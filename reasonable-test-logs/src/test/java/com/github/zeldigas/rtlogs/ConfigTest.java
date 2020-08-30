package com.github.zeldigas.rtlogs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @AfterEach
    void tearDown() {
        System.clearProperty("rtlogs.mode");
        System.clearProperty("rtlogs.debug");
    }

    @Test
    void defaultConfig() {
        Config c = Config.load();//test resources has file with default name
        assertEquals(Config.ControllerType.NOP, c.getControllerType());
        assertFalse(c.isDebug());
    }

    @Test
    void configIsTakenFromFile() {
        Config c = Config.load("/rt-reasonable.properties");

        assertEquals(Config.ControllerType.REASONABLE, c.getControllerType());
        assertTrue(c.isDebug());
    }

    @Test
    void invalidConfigIsIgnored() {
        Config c = Config.load("/bad_file.txt");

        assertEquals(Config.ControllerType.NOP, c.getControllerType());
        assertFalse(c.isDebug());
    }

    @Test
    void systemPropertiesTakesPrecedenceOverConfigurationFile() {
        System.setProperty("rtlogs.mode", "auto");
        System.setProperty("rtlogs.debug", "false");

        Config c = Config.load("/rt-reasonable.properties");

        assertEquals(Config.ControllerType.AUTO, c.getControllerType());
        assertFalse(c.isDebug());
    }
}