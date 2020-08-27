package com.github.zeldigas.example.junit4;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorTest {

    private static Logger log = LoggerFactory.getLogger(ErrorTest.class);

    @BeforeClass
    public static void beforeAll() {
        log.info("Before class");
    }

    @AfterClass
    public static void afterAll() {
        log.info("After class");
    }

    @Before
    public void setUp() {
        log.info("Before test");
    }

    @After
    public void tearDown() {
        log.info("After test");
    }

    @Test
    public void testFailure() {
        log.info("Log in test that fails");
        Assert.assertTrue("assertion failure", 1 == 2);
    }

    @Test
    public void testError() {
        log.info("Log in test that errors");
        System.out.println("Result is: " + (1 / 0));
    }

    @Test
    public void passingTest() {
        log.error("I'm passing so this message will not logged");
        Assert.assertTrue("assertion failure", 1 == 1);
    }
}
