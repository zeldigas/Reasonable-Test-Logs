package com.github.zeldigas.rtlogs;

public class NopLoggingController implements LoggingController {
    @Override
    public void enter(String execution) {
        report("Execution started: " + execution);
    }

    @Override
    public void exit(String execution) {
        report("Execution completed, dropping logs: " + execution);
    }

    @Override
    public void exitAndFlushLogs(String execution) {
        report("Execution completed, flushing : " + execution);
    }

    @Override
    public void startCapture() {
        report("Start capturing");
    }

    @Override
    public void stopCapture() {
        report("Stop capturing");
    }

    private void report(String msg) {
        System.out.println("[" + Thread.currentThread().getName() + "] NopLoggingController: " + msg);
    }
}
