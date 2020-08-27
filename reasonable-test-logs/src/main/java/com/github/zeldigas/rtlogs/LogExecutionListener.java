package com.github.zeldigas.rtlogs;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

public class LogExecutionListener implements TestExecutionListener {

    private LoggingController loggingController = new LoggingControllerImpl();

    public void testPlanExecutionStarted(TestPlan testPlan) {
        loggingController.startCapture();
    }

    public void testPlanExecutionFinished(TestPlan testPlan) {
        loggingController.stopCapture();
    }

    public void executionStarted(TestIdentifier testIdentifier) {
        loggingController.enter(testIdentifier.getUniqueId());
    }

    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL) {
            loggingController.exit(testIdentifier.getUniqueId());
        } else {
            loggingController.exitAndFlushLogs(testIdentifier.getUniqueId());
        }
    }

}
