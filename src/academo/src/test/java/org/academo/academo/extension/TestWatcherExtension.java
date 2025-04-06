package org.academo.academo.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestWatcherExtension implements TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(TestWatcherExtension.class);
    private final List<TestResultStatus> testResultsStatus = new ArrayList<>();

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("❌ Test Failed: {}", context.getDisplayName(), cause);
        testResultsStatus.add(TestResultStatus.FAILED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info(" Test Passed: {}", context.getDisplayName());
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("🛑 Test Aborted: {} - {}", context.getDisplayName(), cause.getMessage());
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
        logger.warn("⚠️ Test Disabled: {} - {}", context.getDisplayName(), reason.orElse("No reason provided"));
        testResultsStatus.add(TestResultStatus.DISABLED);
    }
}
