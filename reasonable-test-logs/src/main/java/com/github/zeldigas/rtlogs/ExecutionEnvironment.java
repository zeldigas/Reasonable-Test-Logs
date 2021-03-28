package com.github.zeldigas.rtlogs;

public class ExecutionEnvironment {

    private static final String LOGBACK_CONTEXT = "ch.qos.logback.classic.LoggerContext";
    private static final String SLF4J = "org.slf4j.LoggerFactory";

    public static boolean ideMode() {
        String val = System.getProperties().getProperty("sun.java.command");
        return isRunningInIntellij(val);
    }

    private static boolean isRunningInIntellij(String val) {
        return val.startsWith("com.intellij.rt.");
    }

    public static boolean logbackIsAvailable() {
        return classExistsInClassPath(SLF4J) && classExistsInClassPath(LOGBACK_CONTEXT);
    }

    static boolean classExistsInClassPath(String className){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

}
