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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class SauceBase {

    public static WebDriver driver; // Instance driver, not static
    private static ThreadLocal<WebDriver> dr = new ThreadLocal<>();
    protected Actions action;
    protected FluentWait<WebDriver> wait;
    protected JavascriptExecutor js;
    
    public static WebDriver getDriver() {
    	
    	return dr.get();
    	
    }
    
    public static void setDriver(WebDriver driverref) {
    	
    	dr.set(driverref);
    	
    }
    
    public static void unload() {
    	dr.remove();
    }

    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true) // Using BeforeMethod instead of BeforeTest for parallelism
    public void setUp(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
        	driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser value: " + browser);
        }
        
        setDriver(driver);
        getDriver().manage().window().maximize();
        getDriver().get("https://www.saucedemo.com");

        js = (JavascriptExecutor) getDriver();
        action = new Actions(getDriver());
        
        // Initialize FluentWait with driver instance
        

        // Ensure the page is fully loaded
        getWait().until(driver -> js.executeScript("return document.readyState").equals("complete"));
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
        	getDriver().quit();
            unload();
        }
    }

//    public WebDriver getDriver() {
//        return driver;
//    }

    public FluentWait<WebDriver> getWait() {
    	if(wait==null) {
    		wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class);
    	}
		return wait;
    }

    public Actions getAction() {
    	if(action == null) {
    		action = new Actions(getDriver());
    	}
        return action;
    }
}
