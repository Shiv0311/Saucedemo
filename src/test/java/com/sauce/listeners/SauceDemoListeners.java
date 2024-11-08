package com.sauce.listeners;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.sauce.utility.ScreenshotUtility;

import io.qameta.allure.Allure;

public class SauceDemoListeners extends ScreenshotUtility implements ITestListener {
	 
    InputStream iS;
	
	@Override
    public void onTestStart(ITestResult result) {
        Reporter.log("Test started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("Test passed: " + result.getName() + " with status: " + result.getStatus());
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	
    	String screenshotPath = null;
		try {
			screenshotPath = ScreenshotUtility.failedEvidence();
		} catch (IOException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		try {
			iS = new FileInputStream(screenshotPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Allure.addAttachment("Screenshot", iS);



    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Reporter.log("Test skipped: " + result.getName() + " with status: " + result.getStatus());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // not implemented
    }

    @Override
    public void onFinish(ITestContext context) {
        // not implemented
    }

}
