package com.github.zeldigas.example.extensions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LoggingExtension.class)
@Disabled
public class SkipClassTest {

    @Test
    public void disabledTest() {
        System.out.println("I'm disabled by class");
    }

}
