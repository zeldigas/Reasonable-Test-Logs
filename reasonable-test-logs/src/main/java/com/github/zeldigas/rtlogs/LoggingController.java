package com.github.zeldigas.rtlogs;

public interface LoggingController {
    void enter(String execution);

    void exit(String execution);

    void exitAndFlushLogs(String execution);

    void startCapture();

    void stopCapture();
}
