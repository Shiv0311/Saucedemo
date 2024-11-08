package com.sauce.functionalities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sauce.common.SauceBase;

import io.qameta.allure.Story;

public class SauceTestAddToCart extends SauceBase{
	
	
	@Test(dependsOnMethods = "com.sauce.functionalities.SauceTestLoginLogout.testLogin")
    @Story("To check whether products are added in cart")
    public void addCartFunctionality() {
		WebElement elementBackpack = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test=\"add-to-cart-sauce-labs-backpack\"]")));
		getAction().moveToElement(elementBackpack).click().perform();
		System.out.println("elementbackpack is clicked");
		WebElement elementBikeLight = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test=\"add-to-cart-sauce-labs-bike-light\"]")));
		getAction().moveToElement(elementBikeLight).click().perform();
		System.out.println("elementBikeLight is clicked");
        WebElement OneSie = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test=\"add-to-cart-sauce-labs-onesie\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", OneSie);
//        getAction().scrollToElement(OneSie).click().perform();
        getAction().moveToElement(OneSie).click().perform();
        System.out.println("Onsie is clicked");
        WebElement cart = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-test=\"shopping-cart-link\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cart);
        getAction().moveToElement(cart).click().perform();
        System.out.println("cart is clicked");
        List<WebElement> productList = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class=\"cart_item\"]/div[2]/a/div")));
        System.out.println(productList.size());
        String[] expectedProducts = {"Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Onesie"};
        if (productList.size() == expectedProducts.length) {
            for (int i = 0; i < productList.size(); i++) {
                String actualProduct = productList.get(i).getText();
                String expectedProduct = expectedProducts[i];
                Assert.assertEquals(actualProduct, expectedProduct);
            }
        }
    }

    @Test(dependsOnMethods = "com.sauce.functionalities.SauceTestAddToCart.addCartFunctionality")
    @Story("To check whether user can continue to add more items and navigated to Products Page without completing checkout process")
    public void continueShopping() {
        WebElement continueShopping = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id=\"continue-shopping\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueShopping);
        getAction().moveToElement(continueShopping).click().perform();
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(actualUrl, expectedUrl);
    }
}
