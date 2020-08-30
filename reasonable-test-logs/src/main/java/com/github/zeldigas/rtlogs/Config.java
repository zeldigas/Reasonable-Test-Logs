package com.github.zeldigas.rtlogs;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class Config {

    public static final String CONFIG_FILE = "/reasonable-test-logs.properties";
    private static final String PROPS_PREFIX = "rtlogs.";
    private static final String PROP_CONTROLLER_TYPE = PROPS_PREFIX + "mode";
    private static final String PROP_DEBUG = PROPS_PREFIX + "debug";

    enum ControllerType {
        AUTO, NOP, REASONABLE
    }

    private final ControllerType controllerType;
    private final boolean debug;

    public Config(ControllerType controllerType, boolean debug) {
        this.controllerType = controllerType;
        this.debug = debug;
    }

    public ControllerType getControllerType() {
        return controllerType;
    }

    public boolean isDebug() {
        return debug;
    }

    public static Config load() {
        return load(CONFIG_FILE);
    }

    static Config load(String file) {
        Properties properties = defaultProperties();
        mergeTo(loadConfigFile(file), properties);
        mergeTo(System.getProperties(), properties);
        mergeTo(propertiesFromEnvVars(), properties);

        return new Config(
                ControllerType.valueOf(properties.getProperty(PROP_CONTROLLER_TYPE).toUpperCase()),
                toBool(properties.getProperty(PROP_DEBUG))
        );
    }

    private static Properties loadConfigFile(String file) {
        Properties properties = new Properties();
        InputStream config = Config.class.getResourceAsStream(file);
        if (config != null) {
            try {
                properties.load(config);
            } catch (Exception e) {
                System.err.println("Failed to load properties from " + file + " file");
                e.printStackTrace();
            }
        }
        return properties;
    }

    private static void mergeTo(Properties source, Properties dest) {
        source.forEach((k, v) -> {
            if (k.toString().startsWith(PROPS_PREFIX)) {
                dest.put(k, v);
            }
        });
    }

    private static boolean toBool(String value) {
        return Boolean.parseBoolean(value);
    }

    private static Properties defaultProperties() {
        Properties properties = new Properties();
        properties.setProperty(PROP_CONTROLLER_TYPE, "auto");
        properties.setProperty(PROP_DEBUG, "false");
        return properties;
    }

    private static Properties propertiesFromEnvVars() {
        Properties properties = new Properties();
        Map<String, String> envVars = System.getenv();
        if (envVars.containsKey("RTLOGS_MODE")) {
            properties.setProperty(PROP_CONTROLLER_TYPE, envVars.get("RTLOGS_MODE"));
        }
        if (envVars.containsKey("RTLOGS_DEBUG")) {
            properties.setProperty(PROP_DEBUG, envVars.get("RTLOGS_DEBUG"));
        }
        return properties;
    }


}
