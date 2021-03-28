package com.github.zeldigas.rtlogs;

import java.io.PrintStream;

public class StdoutLogger implements LibraryLogger {
    private final boolean debugEnabled;
    private final String className;

    public StdoutLogger(boolean debugEnabled, String className) {
        this.debugEnabled = debugEnabled;
        this.className = className;
    }

    @Override
    public void debug(String msg) {
        if (debugEnabled) {
            doLog(System.out, msg);
        }
    }

    @Override
    public void error(String msg) {
        doLog(System.err, msg);
    }

    private void doLog(PrintStream stream, String msg) {
        stream.println("RTLOGS [" + Thread.currentThread().getName() + "] " + className +": " + msg);
    }
}
