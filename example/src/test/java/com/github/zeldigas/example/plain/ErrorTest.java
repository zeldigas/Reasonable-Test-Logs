package com.github.zeldigas.example.plain;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorTest {

    private static Logger log = LoggerFactory.getLogger(ErrorTest.class);

    @BeforeAll
    static void beforeAll() {
        log.info("Before class");
    }

    @AfterAll
    static void afterAll() {
        log.info("After class");
    }

    @BeforeEach
    void setUp() {
        log.info("Before test");
    }

    @AfterEach
    void tearDown() {
        log.info("After test");
    }

    @Test
    public void testFailure() {
        log.info("Log in test that fails");
        Assertions.assertTrue(1 == 2, "assertion failure");
    }

    @Test
    void testError() {
        log.info("Log in test that errors");
        System.out.println("Result is: " + (1 / 0));
    }

    @Test
    void passingTest() {
        log.error("I'm passing so this message will not logged");
        Assertions.assertTrue(1 == 1, "assertion failure");
    }
}
