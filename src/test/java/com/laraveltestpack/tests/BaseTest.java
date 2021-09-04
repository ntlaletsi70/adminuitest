package com.laraveltestpack.tests;

import com.laraveltestpack.utility.Helper;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;


public class BaseTest{
    public Helper helperForBase;
    public String environment;
    public String browser;

    @Test
    public void launchOnWeb() throws Exception {
        helperForBase = new Helper();
        environment = System.getProperty("env");
        browser = System.getProperty("browser");
        helperForBase.setupThreadWeb(browser);
        helperForBase.getDriver().get("https://crecheadmin-dev.herokuapp.com/");
    }

//    @Test
//    public void launchOnWebMac() throws Exception {
//        helperForBase = new Helper();
//        environment = System.getProperty("env");
//        browser = System.getProperty("browser");
//        helperForBase.setupThreadMAcWeb();
//        helperForBase.getDriver().get("https://crecheadmin-dev.herokuapp.com/");
//    }

//    @Test
//    public void launchOnWebIOS() throws Exception {
//        helperForBase = new Helper();
//        environment = System.getProperty("env");
//        browser = System.getProperty("browser");
//        helperForBase.setupThreadIOS();
//        helperForBase.getDriver().get("https://crecheadmin-dev.herokuapp.com/");
//    }


//
//    @Test
//    public void launchOnWebAndroid() throws Exception {
//        helperForBase = new Helper();
//        environment = System.getProperty("env");
//        browser = System.getProperty("browser");
//        helperForBase.setupThreadAndroid();
//        helperForBase.getDriver().get("https://crecheadmin-dev.herokuapp.com/");
//    }

    @AfterTest
    public void afterTest() {
       helperForBase.tearDownDriver();
    }
}
