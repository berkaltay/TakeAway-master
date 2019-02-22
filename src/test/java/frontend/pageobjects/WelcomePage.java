package frontend.pageobjects;

import com.aventstack.extentreports.MediaEntityBuilder;
import framework.base.BasePageMethods;
import framework.extentFactory.ReportFactory;
import framework.utilities.Constants;
import framework.utilities.ScreenshotTaker;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;

import java.io.IOException;

import static framework.utilities.ScreenshotTaker.takeBase64Screenshot;

public class WelcomePage extends BasePageMethods {

    By addAddressButton = By.xpath("//section[@class='content-myaccount myaccount-listing']//button[.='Adres toevoegen']");
    By streetName = By.id("imyaccount_street");
    By postCode = By.id("imyaccount_postcode");
    By town = By.id("imyaccount_town");
    By successNotification = By.id("inotification");
    By address = By.xpath("//div[@id='userpanel-wrapper']/section[@class='bottom-content']//a[@href='/mijnaccount/mijn-adressen']");
    By addAddressButtonLastStep = By.xpath("//form[@id='imyaccount_addeditaddressform']//input[@value='Adres toevoegen']");




    String username = null;
    By logOutButton = By.xpath("//a[contains(text(),'log out')]");
    By profileSection = By.cssSelector("#profile_link");
    By detailsSection = By.cssSelector("#details_link");
    By loggedInUserInformation = By.xpath("//div[@id='status']");
    By loggedInAsAdminText = By.xpath("//div[@id='status' and ./p[contains(.,'admin')]]");
    By loggedInAsDevText = By.xpath("//div[@id='status' and ./p[contains(.,'dev')]]");
    By loggedInAsTesterText = By.xpath("//div[@id='status' and ./p[contains(.,'test')]]");

    public void returnLoggedInUser() {

        if (isElementPresent(loggedInAsAdminText,10))
            ReportFactory.getChildTest().info("Logged in as Admin");
        else if (isElementPresent(loggedInAsDevText,10))
            ReportFactory.getChildTest().info("Logged in as Dev");
        else if (isElementPresent(loggedInAsTesterText,10))
            ReportFactory.getChildTest().info("Logged in as Tester");
        else {
            ReportFactory.getChildTest().warning("Not ready for a new user!!");
        }
    }

    public void whoIsLoggedIn() {

        if(driver.findElement(loggedInUserInformation).getText().contains("admin"))
            ReportFactory.getChildTest().info("Logged in as Admin");
        else if(driver.findElement(loggedInUserInformation).getText().contains("dev"))
            ReportFactory.getChildTest().info("Logged in as Dev");
        else if(driver.findElement(loggedInUserInformation).getText().contains("test"))
            ReportFactory.getChildTest().info("Logged in as Tester");
        else {
            ReportFactory.getChildTest().warning("Not ready for a new user!!");
        }
    }

    public void logOut() {
        clickWebElement(logOutButton);
        ReportFactory.getChildTest().info("User logged self out");
    }

    public void switchTab(String title) {

            switch (title){
                case "details":
                    clickWebElement(detailsSection);
                    break;
                case "profile":
                    clickWebElement(profileSection);
                    break;
            }

        }

    public void fillAddressForm(){
        clickWebElement(address);
        clickWebElement(addAddressButton);
        sendKeysToElement(streetName,"SusameStreet 5");
        sendKeysToElement(postCode,"1017AB ");
        sendKeysToElement(town,"SomeTown");
        clickWebElement(addAddressButtonLastStep);

    }

/*    public boolean getTextOfNotification() {
        String ntfyTextOnScreen = waitUntilVisibleByLocator(successNotification).getText();
        String givenText = "Het adres is succesvol toegevoegd aan je account!";
        Assert.assertEquals(ntfyTextOnScreen, givenText);
        return true;
    }*/


    public Matcher<WelcomePage> getTextOfNotification() {
        return new BaseMatcher<WelcomePage>() {
            @Override
            public boolean matches(Object item) {
                WelcomePage welcomePage = (WelcomePage) item;

                //boolean returner = true;

                String ntfyTextOnScreen = waitUntilVisibleByLocator(successNotification).getText();
                String givenText = "Het adres is succesvol toegevoegd aan je account!";
                try {
                    ReportFactory.getChildTest().info("Assertion done for the text!", MediaEntityBuilder.createScreenCaptureFromBase64String(takeBase64Screenshot("testThuis", "getTextOfNotification")).build());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Assert.assertEquals(ntfyTextOnScreen, givenText);
                return true;

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("HomePage should be asserted in every inch. ");
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                description.appendText("but not. ");
                ReportFactory.getChildTest().fail("login screen is NOT displayed successfully");
            }
        };
    }


}


