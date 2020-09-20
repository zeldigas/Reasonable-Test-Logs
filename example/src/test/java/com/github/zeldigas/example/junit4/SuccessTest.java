package com.github.zeldigas.example.junit4;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuccessTest {

    private static Logger log = LoggerFactory.getLogger(SuccessTest.class);

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
    public void successTest() {
        log.info("Log in test");
        try {
            throw new RuntimeException("something went wrong");
        }catch (Exception ex){
            log.error("I'm ignored when reasonable test logs is used :)", ex);
        }
        Assert.assertTrue("This is good", 1 == 1);
    }
}
