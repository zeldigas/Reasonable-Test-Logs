package com.github.zeldigas.example.plain;

import com.github.zeldigas.example.extensions.AfterAllFailingTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeforeAllFailingTest {

    private static final Logger log = LoggerFactory.getLogger(AfterAllFailingTest.class);

    @BeforeAll
    public static void beforeAll() {
        log.info("Entering beforeAll that fails");
        throw new RuntimeException();
    }

    @Test
    void hello() {

    }

    @Test
    void world() {

    }
}
