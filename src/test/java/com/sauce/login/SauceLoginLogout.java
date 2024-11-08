package com.sauce.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.sauce.common.SauceBase;

public class SauceLoginLogout extends SauceBase{
	
	public void sauceLogin(String username, String password) {
		
        WebElement userName = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement passWord = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement loginButton = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        userName.clear();
        userName.sendKeys(username);
        passWord.clear();
        passWord.sendKeys(password);
        loginButton.click();
		
	}
	
	public void sauceLogout() {
		
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"react-burger-menu-btn\"]"))).click();
    	getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test=\"logout-sidebar-link\"]"))).click();
		
	}

}
