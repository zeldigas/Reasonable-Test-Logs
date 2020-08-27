package com.github.zeldigas.example.extensions;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExtension implements
        BeforeAllCallback, BeforeEachCallback,
        AfterEachCallback, AfterAllCallback,
        ParameterResolver{

    private Logger log = LoggerFactory.getLogger(LoggingExtension.class);

    public void afterAll(ExtensionContext context) throws Exception {
        log.info("After all in extension");
    }

    public void afterEach(ExtensionContext context) throws Exception {
        log.info("After each in extension");
    }

    public void beforeAll(ExtensionContext context) throws Exception {
        log.info("Before all in extension");
    }

    public void beforeEach(ExtensionContext context) throws Exception {
        log.info("Before each in extension");
    }

    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Fixture.class);
    }

    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new Fixture();
    }
}
