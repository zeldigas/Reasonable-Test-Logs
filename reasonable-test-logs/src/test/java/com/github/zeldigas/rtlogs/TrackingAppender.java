package com.github.zeldigas.rtlogs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class TrackingAppender extends AppenderBase<ILoggingEvent> {

    private int startCount;
    private int stopCount;
    private List<ILoggingEvent> events = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        events.add(eventObject);
    }

    @Override
    public void start() {
        super.start();
        startCount++;
    }

    @Override
    public void stop() {
        super.stop();
        stopCount++;
    }

    public int getStartCount() {
        return startCount;
    }

    public int getStopCount() {
        return stopCount;
    }

    public List<ILoggingEvent> getEvents() {
        return events;
    }
}
