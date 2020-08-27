package com.github.zeldigas.example.plain;

import com.github.zeldigas.example.extensions.Fixture;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    void successTest() {
        log.info("Log in test");
        Assertions.assertTrue(1 == 1, "This is good");
    }
}
