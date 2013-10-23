package com.company.project.selenium;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriver;

public class WebDriverManager {

    private static final ThreadLocal<WebDriver> instances = new ThreadLocal<WebDriver>();

    private static WebDriverProvider driverProvider;

    @BeforeStories
    public synchronized void beforeStories() {
        driverProvider = getDriverProvider();
    }

    public WebDriver getWebDriver() {
        WebDriver webDriver = instances.get();
        if (webDriver == null) {
            webDriver = createWebDriver();
            instances.set(webDriver);
        }
        return webDriver;
    }

    @AfterStory
    public synchronized void kill() {
        driverProvider.end();
        instances.set(null);
    }

    protected WebDriver createWebDriver() {
        driverProvider.initialize();
        return driverProvider.get();
    }

    protected synchronized WebDriverProvider getDriverProvider() {
        if (driverProvider == null) {
            driverProvider = new PropertyWebDriverProvider();
        }
        return driverProvider;
    }

}