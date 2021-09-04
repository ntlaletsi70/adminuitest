package com.laraveltestpack.pages;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitUntilVisible(int timeoutInSeconds, WebElement element) {
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public Select selectByVisibleText(WebElement element, String visibleText) {
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
        return select;
    }

    public void click(WebElement element){
        try {
            element.click();
        }
        catch (NoSuchElementException ex){
            ex.printStackTrace();
        }
    }

    public void sendKeys(WebElement element,String keys){
        try{
            element.sendKeys(keys);
        }
        catch(Exception ex){
        }
    }

    @Attachment(value = "[Screenshot] {name}", type = "image/png")
    public byte[] takeScreenshot(String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
