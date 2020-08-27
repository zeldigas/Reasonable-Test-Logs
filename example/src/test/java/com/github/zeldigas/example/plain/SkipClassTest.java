package com.github.zeldigas.example.plain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class SkipClassTest {

    @Test
    public void disabledTest() {
        System.out.println("I'm disabled by class");
    }

}
