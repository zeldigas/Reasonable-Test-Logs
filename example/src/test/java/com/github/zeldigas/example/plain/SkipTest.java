package com.github.zeldigas.example.plain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SkipTest {

    @Test
    @Disabled
    public void disabledTest() {
        System.out.println("I'm disabled");
    }

}
