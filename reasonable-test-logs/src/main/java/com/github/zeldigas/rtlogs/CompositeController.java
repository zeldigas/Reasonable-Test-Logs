package com.github.zeldigas.rtlogs;

import java.util.Collections;
import java.util.List;

public class CompositeController implements LoggingController {

    private final List<LoggingController> items;

    public CompositeController(List<LoggingController> items) {
        this.items = items;
    }

    @Override
    public void enter(String execution) {
        items.forEach(loggingController -> loggingController.enter(execution));
    }

    @Override
    public void exit(String execution) {
        items.forEach(loggingController -> loggingController.exit(execution));
    }

    @Override
    public void exitAndFlushLogs(String execution) {
        items.forEach(loggingController -> loggingController.exitAndFlushLogs(execution));
    }

    @Override
    public void startCapture() {
        items.forEach(LoggingController::startCapture);
    }

    @Override
    public void stopCapture() {
        items.forEach(LoggingController::stopCapture);
    }

    public List<LoggingController> getItems() {
        return Collections.unmodifiableList(items);
    }
}
