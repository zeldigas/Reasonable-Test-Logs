package com.github.zeldigas.rtlogs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

class LogCollectingAppenderTest {

    private LogCollectingAppender appender = new LogCollectingAppender();

    @Test
    void appenderCollectMessagesToList() {
        ILoggingEvent event = mock(ILoggingEvent.class);

        appender.doAppend(event);

        assertEquals(1, appender.getEvents().size());
        assertSame(event, appender.getEvents().get(0));
    }

    @Test
    void resettingCollectingEventsToBookmark() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        ILoggingEvent event1 = mock(ILoggingEvent.class);

        assertEquals(0, appender.getEventsRef());

        appender.doAppend(event);

        assertEquals(1, appender.getEventsRef());

        appender.doAppend(event1);
        appender.doAppend(event1);

        assertEquals(3, appender.getEventsRef());
        assertEquals(Arrays.asList(event, event1, event1), appender.getEvents());

        appender.resetTo(2);
        assertEquals(2, appender.getEvents().size());
        assertEquals(Arrays.asList(event, event1), appender.getEvents());

        appender.resetTo(0);
        assertEquals(0, appender.getEvents().size());

    }
}