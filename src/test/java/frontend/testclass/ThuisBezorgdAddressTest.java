package frontend.testclass;

import framework.base.TestBase;
import framework.utilities.Constants;
import frontend.pageobjects.HomePage;
import frontend.pageobjects.LoginPage;
import frontend.pageobjects.WelcomePage;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class ThuisBezorgdAddressTest extends TestBase {

    @Test(description = "Front End Login and Add Address Scenario")
    public void testThuisBezorgdLogin() throws Exception {

        //*************PAGE INSTANTIATIONS*************

        WelcomePage welcomePage = new WelcomePage();
        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();

        //*************TEST METHODS********************

        homePage.clickLogInSection();
        loginPage.logInWith(Constants.USER, Constants.PASSWORD);
        welcomePage.fillAddressForm();
        assertThat("Should verify success message!", welcomePage, welcomePage.getTextOfNotification());
    }

    //@Test(description = "Front End scenario for Sign Up")
    public void testWaesFrontEndSignIp() throws Exception {

        //*************PAGE INSTANTIATIONS*************

        HomePage homePage = new HomePage();

        //*************TEST METHODS********************

        assertThat("Should verify Elements", homePage, homePage.verifyHomePageObjects());
        homePage.clickSignUpSection();
        //signUpPage.fillFormForNewUser();

    }
}
