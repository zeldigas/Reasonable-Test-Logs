package com.github.zeldigas.rtlogs;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class LoggingControllerImpl implements LoggingController {

    private final Deque<TestFrame> executionStack = new LinkedList<>();
    private List<AppenderSwitcher> switchers = new ArrayList<>();

    @Override
    public void startCapture() {
        LoggerContext loggerContext = getLoggerContext();
        switchers = loggerContext.getLoggerList().stream()
                .map(logger -> {
                    List<Appender<ILoggingEvent>> appenders = new ArrayList<>();
                    logger.iteratorForAppenders().forEachRemaining(appenders::add);
                    if (!appenders.isEmpty()) {
                        return new AppenderSwitcher(logger, appenders, new LogCollectingAppender());
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        switchers.forEach(AppenderSwitcher::enableCapturing);
    }

    private LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Override
    public void stopCapture() {
        switchers.forEach(AppenderSwitcher::restoreOriginalAppenders);
    }

    @Override
    public void enter(String execution) {
        executionStack.push(new TestFrame(
                execution, switchers.stream().map(AppenderSwitcher::getCollectingAppender).collect(Collectors.toList())
        ));
    }

    @Override
    public void exit(String execution) {
        if (executionStack.isEmpty()) return;

        if (!execution.equals(executionStack.peek().execution)) {
            System.err.println("Execution stack order is violated. Expected " + executionStack.peek().execution + ", but was " + execution);
            exitAndFlushLogs(execution);
        } else {
            TestFrame frame = executionStack.pop();
            frame.startPositions.forEach(StartPosition::reset);
        }
    }

    @Override
    public void exitAndFlushLogs(String execution) {
        switchers.forEach(AppenderSwitcher::flushLogs);
        executionStack.clear();
    }

    private static class TestFrame {
        private final String execution;
        private final List<StartPosition> startPositions;


        public TestFrame(String execution, List<LogCollectingAppender> eventPositions) {
            this.execution = execution;
            this.startPositions = eventPositions.stream().map(lca -> new StartPosition(
                    lca, lca.getEventsRef()
            )).collect(Collectors.toList());
        }
    }

    private static class StartPosition {
        private final LogCollectingAppender appender;
        private final int startRef;

        public StartPosition(LogCollectingAppender appender, int startRef) {
            this.appender = appender;
            this.startRef = startRef;
        }

        public void reset() {
            appender.resetTo(startRef);
        }
    }

    private static class AppenderSwitcher {
        private final Logger logger;
        private final List<Appender<ILoggingEvent>> originalAppenders;
        private final LogCollectingAppender collectingAppender;

        public AppenderSwitcher(Logger logger, List<Appender<ILoggingEvent>> originalAppenders, LogCollectingAppender collectingAppender) {
            this.logger = logger;
            this.originalAppenders = originalAppenders;
            this.collectingAppender = collectingAppender;
        }

        public void enableCapturing() {
            originalAppenders.forEach(logger::detachAppender);
            logger.addAppender(collectingAppender);
        }

        public void restoreOriginalAppenders() {
            logger.detachAppender(collectingAppender);
            originalAppenders.forEach(logger::addAppender);
        }

        public void flushLogs() {
            restoreOriginalAppenders();
            collectingAppender.flushEventsToLogger(logger);
            enableCapturing();
        }

        public LogCollectingAppender getCollectingAppender() {
            return collectingAppender;
        }
    }

}
