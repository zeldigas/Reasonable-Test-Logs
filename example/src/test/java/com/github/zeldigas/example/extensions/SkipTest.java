package com.github.zeldigas.example.extensions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LoggingExtension.class)
public class SkipTest {

    @Test
    @Disabled
    public void disabledTest() {
        System.out.println("I'm disabled");
    }

}
