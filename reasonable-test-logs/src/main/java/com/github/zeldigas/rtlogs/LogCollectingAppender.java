package com.github.zeldigas.rtlogs;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogCollectingAppender extends AppenderBase<ILoggingEvent> {

    private List<ILoggingEvent> events = new ArrayList<ILoggingEvent>();

    public LogCollectingAppender() {
        started = true;
    }

    protected void append(ILoggingEvent eventObject) {
        events.add(eventObject);
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }

    public int getEventsRef() {
        return events.size();
    }

    public void resetTo(int ref) {
        if (noNewEvents(ref)) return;

        if (ref <= 0) {
            events = new ArrayList<>();
        } else {
            events = events.stream().limit(ref).collect(Collectors.toList());
        }
    }

    private boolean noNewEvents(int ref) {
        return getEventsRef() == ref;
    }

    public void flushEventsToLogger(Logger logger) {
        getEvents().forEach(logger::callAppenders);
        events.clear();
    }
}
