package com.github.zeldigas.rtlogs;

import java.util.ArrayList;
import java.util.List;

public interface LoggingController {
    static LoggingController getInstance() {
        Config config = Config.load();

        LibraryLoggerFactory libraryLogger = LibraryLoggerFactory.create(config.isDebug());

        if (!ExecutionEnvironment.supportedLoggerAvailable()) {
            libraryLogger.get(LoggingController.class).debug("Logback is not in classpath");
            return new NopLoggingController(libraryLogger);
        }

        Config.ControllerType controllerType = config.getControllerType();
        if (controllerType == Config.ControllerType.AUTO) {
            controllerType = ExecutionEnvironment.ideMode() ? Config.ControllerType.NOP : Config.ControllerType.REASONABLE;
        }
        switch (controllerType) {
            case NOP:
                return new NopLoggingController(libraryLogger);
            case REASONABLE:
                return createReasonableController(libraryLogger, config);
            default:
                throw new IllegalStateException("Invalid controller type");
        }
    }

    static LoggingController createReasonableController(LibraryLoggerFactory libraryLogger, Config config) {
        List<LoggingController> controllers = new ArrayList<>();
        if (config.isSuppressSpring()) {
            controllers.add(new SpringBootRebindPreventer());
        }
        controllers.add(new LoggingControllerImpl(libraryLogger));
        return new CompositeController(controllers);
    }

    void enter(String execution);

    void exit(String execution);

    void exitAndFlushLogs(String execution);

    void startCapture();

    void stopCapture();
}
