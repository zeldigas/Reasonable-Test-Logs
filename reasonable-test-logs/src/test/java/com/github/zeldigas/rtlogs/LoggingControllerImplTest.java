package com.github.zeldigas.rtlogs;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LoggingControllerImplTest {

    private TrackingAppender appender;
    private LoggingControllerImpl controller = new LoggingControllerImpl();

    @BeforeEach
    void before() throws Exception {
        LoggerContext loggerContext = getLoggerContext();
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        try(InputStream is = LoggingControllerImplTest.class.getResourceAsStream("/sample-config.xml")) {
            configurator.setContext(loggerContext);
            configurator.doConfigure(is);
        }
        appender = (TrackingAppender) getLoggerContext().getLogger("ROOT").getAppender("TEST");
    }

    private LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Test
    void capturingDoesNotStartStopAppenders() {
        controller.startCapture();

        assertNull(getLoggerContext().getLogger("ROOT").getAppender("TEST"));

        LoggerFactory.getLogger("test").warn("hello");

        controller.stopCapture();

        assertSame(appender, getLoggerContext().getLogger("ROOT").getAppender("TEST"));

        assertEquals(1, appender.getStartCount());
        assertEquals(0, appender.getStopCount());
    }

    @Test
    void noEventsForCleanExits() {
        controller.startCapture();

        Logger logger = LoggerFactory.getLogger("test");

        controller.enter("first");

        logger.warn("message");

        controller.enter("second");

        logger.warn("message1");

        controller.exit("second");
        controller.exit("first");
        controller.stopCapture();

        assertEquals(0, appender.getEvents().size());
    }

    @Test
    void eventsFlushOnExit() {
        controller.startCapture();

        Logger logger = LoggerFactory.getLogger("test");

        controller.enter("first");

        logger.warn("message");

        controller.enter("second");

        logger.warn("ignored");

        controller.exit("second");

        logger.error("error1");

        controller.exitAndFlushLogs("first");

        controller.stopCapture();

        assertEquals(2, appender.getEvents().size());
        assertEquals(Arrays.asList("WARN:message", "ERROR:error1"), appender.getEvents().stream().map(e ->
                e.getLevel().toString() + ":"+e.getMessage()).collect(Collectors.toList()));
    }

    @Test
    void eventsFlushOnInvalidExitOrder() {
        controller.startCapture();

        Logger logger = LoggerFactory.getLogger("test");

        controller.enter("first");

        logger.warn("message");

        controller.enter("second");

        logger.warn("message1");

        controller.exit("first");


        logger.error("not flushed first as stack was cleared");
        controller.exit("second");

        assertEquals(2, appender.getEvents().size());
        assertEquals(Arrays.asList("WARN:message", "WARN:message1"), appender.getEvents().stream().map(e ->
                e.getLevel().toString() + ":"+e.getMessage()).collect(Collectors.toList()));

        controller.exitAndFlushLogs("anything");

        assertEquals(3, appender.getEvents().size());
        assertEquals(Arrays.asList("WARN:message", "WARN:message1", "ERROR:not flushed first as stack was cleared"), appender.getEvents().stream().map(e ->
                e.getLevel().toString() + ":"+e.getMessage()).collect(Collectors.toList()));

        controller.stopCapture();

    }
}