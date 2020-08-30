package com.github.zeldigas.rtlogs;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.util.Optional;

import static org.mockito.Mockito.*;

class LogExecutionListenerTest {

    private LoggingController controller = mock(LoggingController.class);
    private LogExecutionListener listener = new LogExecutionListener(controller);

    @Test
    void planStartInitiatesCapture() {
        listener.testPlanExecutionStarted(mock(TestPlan.class));

        verify(controller).startCapture();
    }

    @Test
    void planEndStopsCapture() {
        listener.testPlanExecutionFinished(mock(TestPlan.class));

        verify(controller).stopCapture();
    }

    @Test
    void startExecutionEntersFrame() {
        TestIdentifier identifier = testIdentifier();

        listener.executionStarted(identifier);

        verify(controller).enter("id");
    }

    @Test
    void endExecutionExitsFrameForSuccess() {
        TestIdentifier identifier = testIdentifier();

        TestExecutionResult result = mock(TestExecutionResult.class);
        when(result.getStatus()).thenReturn(TestExecutionResult.Status.SUCCESSFUL);

        listener.executionFinished(identifier, result);

        verify(controller).exit("id");
    }

    @Test
    void endExecutionExitsFrameForFailure() {
        TestIdentifier identifier = testIdentifier();

        TestExecutionResult result = mock(TestExecutionResult.class);
        when(result.getStatus()).thenReturn(TestExecutionResult.Status.FAILED);

        listener.executionFinished(identifier, result);

        verify(controller).exitAndFlushLogs("id");
    }

    private TestIdentifier testIdentifier() {
        TestDescriptor descriptor = mock(TestDescriptor.class);
        UniqueId id = mock(UniqueId.class);
        when(id.toString()).thenReturn("id");
        when(descriptor.getUniqueId()).thenReturn(id);

        when(descriptor.getParent()).thenReturn(Optional.empty());
        when(descriptor.getType()).thenReturn(TestDescriptor.Type.CONTAINER);

        return TestIdentifier.from(descriptor);
    }
}