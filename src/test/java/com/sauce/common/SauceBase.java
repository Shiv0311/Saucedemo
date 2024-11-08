package com.sauce.common;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class SauceBase {
    
    protected static WebDriver driver;
    protected Actions action;
    protected FluentWait<WebDriver> wait;
    protected JavascriptExecutor js;  // Declare JavascriptExecutor here
    
    // Flag to ensure setup runs only once per suite
    private static boolean isInitialized = false;

    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true)
    public void setUp(String browser) {
        if (!isInitialized) {  // Check if setup has already run
            if (browser.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.get("https://www.saucedemo.com");
            } else if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                driver.get("https://www.saucedemo.com");
            } else if (browser.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
                driver.manage().window().maximize();
                driver.get("https://www.saucedemo.com");
            } else {
                throw new IllegalArgumentException("Invalid browser value: " + browser);
            }
            
            // Initialize JavascriptExecutor after the driver is set
            js = (JavascriptExecutor) driver;

            // Wait for the page to load completely
            getWait().until(driver -> js.executeScript("return document.readyState").equals("complete"));
            
            isInitialized = true;  // Set flag to true after initialization
        }
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            isInitialized = false;  // Reset flag for future test suites
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    protected FluentWait<WebDriver> getWait() {
        if (wait == null) {
            wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))           // Maximum wait time
                    .pollingEvery(Duration.ofSeconds(2))           // Frequency of checks
                    .ignoring(NoSuchElementException.class);
        }
        return wait;
    }

    protected Actions getAction() {
        if (action == null) {
            action = new Actions(driver);
        }
        return action;
    }
}
