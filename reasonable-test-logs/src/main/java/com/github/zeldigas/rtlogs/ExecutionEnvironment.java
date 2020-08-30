package com.github.zeldigas.rtlogs;

public class ExecutionEnvironment {

    public static boolean ideMode() {
        String val = System.getProperties().getProperty("sun.java.command");
        return isRunningInIntellij(val);
    }

    private static boolean isRunningInIntellij(String val) {
        return val.startsWith("com.intellij.rt.");
    }

}
