package framework.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static framework.base.TestBase.BROWSER;

public class Browsers {

    public static WebDriver prepareDriver() {
        WebDriver driver;

        loadBrowsers();
        driver = getLocalDriver();
        driver.manage().window().maximize();

        return driver;
    }

    private static WebDriver getLocalDriver() {
        switch (BROWSER) {
            default:
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
        }
    }

    private static void loadBrowsers() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe");
        System.setProperty("webdriver.firefox.driver", System.getProperty("user.dir") + "//src//main//resources//geckodriver.exe");
    }
}
