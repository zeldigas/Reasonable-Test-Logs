package com.github.zeldigas.rtlogs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionEnvironmentTest {

    private String preservedPropery;

    @BeforeEach
    void setUp() {
        preservedPropery = System.getProperty("sun.java.command");
    }

    @AfterEach
    void tearDown() {
        System.setProperty("sun.java.command", preservedPropery);
    }

    @Test
    void noIdeMode() {
        System.setProperty("sun.java.command", "something else");

        assertFalse(ExecutionEnvironment.ideMode());
    }

    @Test
    void ideMode() {
        System.setProperty("sun.java.command", "com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5");
        assertTrue(ExecutionEnvironment.ideMode());
    }
}