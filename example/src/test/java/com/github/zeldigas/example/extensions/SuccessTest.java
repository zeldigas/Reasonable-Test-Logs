package com.github.zeldigas.example.extensions;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(LoggingExtension.class)
public class SuccessTest {

    private static Logger log = LoggerFactory.getLogger(SuccessTest.class);

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
    void successTest(Fixture fixture) {
        log.info("Log in test");
        Assertions.assertTrue(1==1, "This is good");
    }
}
