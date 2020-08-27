package com.github.zeldigas.example.extensions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(LoggingExtension.class)
public class BeforeAllFailingTest {

    private static final Logger log = LoggerFactory.getLogger(AfterAllFailingTest.class);

    @BeforeAll
    public static void beforeAll(){
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
