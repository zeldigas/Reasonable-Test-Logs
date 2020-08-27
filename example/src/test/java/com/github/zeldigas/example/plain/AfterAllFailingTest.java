package com.github.zeldigas.example.plain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfterAllFailingTest {

    private static final Logger log = LoggerFactory.getLogger(AfterAllFailingTest.class);

    @AfterAll
    public static void afterAll() {
        log.info("Entering into after all that would fail");
        throw new RuntimeException();
    }

    @Test
    void hello() {
        log.info("passing test");
    }

    @Test
    void world() {

    }

    @Test
    void failing() {
        log.warn("I'm failing and should be logged");
        throw new RuntimeException("Test error");
    }
}
