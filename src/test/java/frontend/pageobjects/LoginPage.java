package frontend.pageobjects;

import framework.base.BasePageMethods;
import org.openqa.selenium.By;

public class LoginPage extends BasePageMethods {

    //*********Web Elements*********
    By logInButton = By.linkText("Inloggen");
    By email = By.id("iusername");
    By pass = By.id("ipassword");
    By bigLoginButton = By.xpath("//form[@id='icustomerloginform']/input[@value='Inloggen']");



    By userName = By.cssSelector("#username_input");
    By passWord = By.cssSelector("#password_input");
    By loginButton = By.cssSelector("#login_button");

    //*********Page Methods*********

    public void logInWith(String username, String password) throws InterruptedException {

        clickWebElement(logInButton);
        sendKeysToElement(email, username);
        sendKeysToElement(pass, password);
        clickWebElement(bigLoginButton);

    }

}
