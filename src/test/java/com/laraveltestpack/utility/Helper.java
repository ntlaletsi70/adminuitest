package com.laraveltestpack.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Helper {

    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    protected static RemoteWebDriver remoteDriver;
    public static String remote_url = "http://192.168.1.137:30001/wd/hub";

    public void setupThreadWeb(String browserName) throws MalformedURLException {
        if(browserName.equalsIgnoreCase("chrome"))
        {
            ChromeOptions options = new ChromeOptions();
            driver.set(new RemoteWebDriver(new URL(remote_url), options));
        }
        else if (browserName.equalsIgnoreCase("firefox"))
        {
            FirefoxOptions options = new FirefoxOptions();
            driver.set(new RemoteWebDriver(new URL(remote_url), options));
        }
    }

    public void setupThreadAndroid() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("udid", "4df70d325d884031");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("browserName", "Chrome");

        remoteDriver = new RemoteWebDriver(new URL(remote_url), caps);
    }
    public void setupThreadMAcWeb() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserVersion", "93");
        caps.setCapability("platformName", "MAC");

        remoteDriver = new RemoteWebDriver(new URL(remote_url), caps);
    }
    
    public void setupThreadIOS() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "11.4");
        caps.setCapability("deviceName", "iPhone 8");
        caps.setCapability("browserName", "Safari");

        remoteDriver = new RemoteWebDriver(new URL(remote_url), caps);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void tearDownDriver() {
        getDriver().quit();
    }
}