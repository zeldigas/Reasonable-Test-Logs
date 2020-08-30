package com.github.zeldigas.rtlogs;

public interface LoggingController {
    static LoggingController getInstance() {
        Config config = Config.load();

        Config.ControllerType controllerType = config.getControllerType();
        if (controllerType == Config.ControllerType.AUTO) {
            controllerType = ExecutionEnvironment.ideMode() ? Config.ControllerType.NOP : Config.ControllerType.REASONABLE;
        }
        switch (controllerType) {
            case NOP: return new NopLoggingController(config.isDebug());
            case REASONABLE: return new LoggingControllerImpl();
            default: throw new IllegalStateException("Invalid controller type");
        }
    }

    void enter(String execution);

    void exit(String execution);

    void exitAndFlushLogs(String execution);

    void startCapture();

    void stopCapture();
}
