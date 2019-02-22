package frontend.pageobjects;

import framework.base.BasePageMethods;
import framework.extentFactory.ReportFactory;
import framework.utilities.Constants;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HomePage extends BasePageMethods {

    //*********Web Elements*********
    By myAccount = By.xpath("//div[@class='myaccount']/button");



    By waesImage = By.xpath("//img[@title='We Are WAES']");
    By header = By.xpath("//h1[contains(text(),'WAES Tester Assignment')]");
    By login = By.id("login_link");
    By signUp = By.id("signup_link");
    By homeLink =  By.cssSelector("#home_link");

    //*********Page Methods*********
    public String uniqueDatePicker() throws Exception{

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime localDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");
        String uid = localDateAndTime.format(formatter);
        return uid;
    }


    public static  Matcher<HomePage> verifyHomePageObjects() {
        return new BaseMatcher<HomePage>() {
            @Override
            public boolean matches(Object item) {
                HomePage homePage = (HomePage) item;

                boolean returner = true;

                if(homePage.isElementPresent(homePage.header, Constants.WAIT_SHORT))
                ReportFactory.getChildTest().info("Header is verified");
                if(homePage.isElementPresent(homePage.homeLink, Constants.WAIT_SHORT))
                ReportFactory.getChildTest().info("HomeLink is verified");
                if(homePage.isElementPresent(homePage.waesImage, Constants.WAIT_SHORT))
                ReportFactory.getChildTest().info("Image is verified");
                ReportFactory.getChildTest().pass("HomePage is verified for every bit");

                return returner;

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("HomePage should be asserted in every inch. ");
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendText("but not. ");
                ReportFactory.getChildTest().fail("OneNT login screen is NOT displayed successfully");
            }
        };
    }

    public void clickLogInSection() {
            clickWebElement(myAccount);
    }

    public void clickSignUpSection() {
        clickWebElement(signUp);
    }

}
