package com.github.zeldigas.rtlogs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

class LoggingControllerTest {

    private List<String> varsToPreserve = Arrays.asList("rtlogs.mode", "rtlogs.debug", "sun.java.command");
    private Map<String, String> preservedProperties;

    @BeforeEach
    void setUp() {
        preservedProperties = varsToPreserve.stream()
                .map(name -> new AbstractMap.SimpleEntry<String,String>(name, System.getProperty(name)) {})
                .filter(i -> i.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @AfterEach
    void tearDown() {
        preservedProperties.forEach(System::setProperty);
    }

    @Test
    void nopControllerWhenLogbackIsMissing() {
        System.setProperty("rtlogs.mode", "reasonable"); //forcing real controller to be used
        try (MockedStatic<ExecutionEnvironment> mocked = mockStatic(ExecutionEnvironment.class)) {
            mocked.when(ExecutionEnvironment::supportedLoggerAvailable).thenReturn(false);
            assertTrue(LoggingController.getInstance() instanceof NopLoggingController);
        }
    }

    @Test
    void nopController() {
        System.setProperty("rtlogs.mode", "nop");

        assertTrue(LoggingController.getInstance() instanceof NopLoggingController);
    }

    @Test
    void reasonableController() {
        System.setProperty("rtlogs.mode", "reasonable");

        LoggingController instance = LoggingController.getInstance();
        thenControllerIsReasonableWithSpringBootPreventer(instance);
    }

    @Test
    void reasonableWithoutSpringPreventer() {
        System.setProperty("rtlogs.mode", "reasonable");
        System.setProperty("rtlogs.disable-spring-boot", "false");

        LoggingController instance = LoggingController.getInstance();

        List<LoggingController> items = ((CompositeController) instance).getItems();
        assertEquals(1, items.size());
        assertTrue(items.get(0) instanceof LoggingControllerImpl);
    }

    private void thenControllerIsReasonableWithSpringBootPreventer(LoggingController instance) {
        assertTrue(instance instanceof CompositeController);

        List<LoggingController> items = ((CompositeController) instance).getItems();
        assertEquals(2, items.size());
        assertTrue(items.get(0) instanceof SpringBootRebindPreventer);
        assertTrue(items.get(1) instanceof LoggingControllerImpl);
    }

    @Test
    void autoResultingToNop() {
        System.setProperty("rtlogs.mode", "auto");
        System.setProperty("sun.java.command", "com.intellij.rt.junit");

        assertTrue(LoggingController.getInstance() instanceof NopLoggingController);
    }

    @Test
    void autoResultingToReasonable() {
        System.setProperty("rtlogs.mode", "auto");
        System.setProperty("sun.java.command", "something else");

        LoggingController instance = LoggingController.getInstance();

        thenControllerIsReasonableWithSpringBootPreventer(instance);
    }
}