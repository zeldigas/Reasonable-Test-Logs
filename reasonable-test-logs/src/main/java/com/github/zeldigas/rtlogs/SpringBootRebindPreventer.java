package com.github.zeldigas.rtlogs;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

/**
 * Special class to prevent SpringBoot to fully reinitialize logging system.
 *
 * It sets special marker object to logging context that SB checks before fully resetting logging context.
 * Unfortunately after this action it's hardly impossible to find a good point in wrapping appenders again, thus it's
 * easier just to prevent dealing with logging context modification that should be just fine in tests in most of the times.
 */
class SpringBootRebindPreventer implements LoggingController {
    private static final String BOOT_INIT_KEY = "org.springframework.boot.logging.LoggingSystem";

    @Override
    public void enter(String execution) {
        LoggerContext context = getLoggerContext();
        if (context.getObject(BOOT_INIT_KEY) == null) {
            context.putObject(BOOT_INIT_KEY, new Object());
        }
    }

    private LoggerContext getLoggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Override
    public void exit(String execution) {
    }

    @Override
    public void exitAndFlushLogs(String execution) {
    }

    @Override
    public void startCapture() {
    }

    @Override
    public void stopCapture() {
    }
}
