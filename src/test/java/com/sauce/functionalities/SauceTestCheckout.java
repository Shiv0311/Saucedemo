package com.sauce.functionalities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sauce.common.SauceBase;
import com.sauce.login.SauceLoginLogout;

import io.qameta.allure.Story;

public class SauceTestCheckout extends SauceBase {
	
	SauceLoginLogout loginlogout = new SauceLoginLogout();
	 @Test(dependsOnMethods = "com.sauce.functionalities.SauceTestAddToCart.continueShopping")
	    @Parameters({"firstName", "lastName", "postalCode"})
	    @Story("To check whether checkout functionality is working properly")
	    public void doCheckout(String firstname, String lastname, String zipcode) {
	        WebElement cart = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-test=\"shopping-cart-link\"]")));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cart);
	        getAction().moveToElement(cart).click().perform();
	        List<WebElement> productList = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=\"cart_item\"]/div[2]/a/div")));
	        if (productList.size() > 0) {
	            WebElement checkout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"checkout\"]")));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkout);
	            getAction().moveToElement(checkout).click().perform();
	            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"firstName\"]"))).sendKeys(firstname);
	            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"lastName\"]"))).sendKeys(lastname);
	            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"postalCode\"]"))).sendKeys(zipcode);
	            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-test=\"continue\"]"))).click();
	            String actualCheckoutUrl = driver.getCurrentUrl();
	            String expectedCheckoutUrl = "https://www.saucedemo.com/checkout-step-two.html";
	            System.out.println("actualCheckoutUrl= "+actualCheckoutUrl);
	            System.out.println("expectedCheckoutUrl= "+expectedCheckoutUrl);
	            Assert.assertEquals(actualCheckoutUrl, expectedCheckoutUrl);
	        } else {
	            if (driver.getCurrentUrl().equals("https://www.saucedemo.com/checkout-step-two.html")) {
	                Assert.fail();
	            } else {
	                Assert.assertTrue(true);
	            }
	        }
	    }

	    @Test(dependsOnMethods = "com.sauce.functionalities.SauceTestCheckout.doCheckout")
	    @Parameters({"status"})
	    @Story("To check the checkout functionality post clicking on Finish or cancel button")
	    public void finishOrCancelCheckout(String status) {
	        if (status.equalsIgnoreCase("finish")) {
	            WebElement finishButton = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"finish\"]")));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finishButton);
	            getAction().moveToElement(finishButton).click().perform();
	            String actualFinishUrl = driver.getCurrentUrl();
	            String expectedFinishUrl = "https://www.saucedemo.com/checkout-complete.html";
	            Assert.assertEquals(actualFinishUrl, expectedFinishUrl);
	        } else if (status.equalsIgnoreCase("cancel")) {
	            WebElement cancelButton = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-test=\"cancel\"]")));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
	            getAction().moveToElement(cancelButton).click().perform();
	            String actualCancelUrl = driver.getCurrentUrl();
	            String expectedCancelUrl = "https://www.saucedemo.com/inventory.html";
	            Assert.assertEquals(actualCancelUrl, expectedCancelUrl);
	        } else {
	            Assert.fail();
	        }
	    }
	    
	    @Test(dependsOnMethods = "com.sauce.functionalities.SauceTestCheckout.finishOrCancelCheckout")
	    public void testLogout() {
	        loginlogout.sauceLogout();
	    }

}
