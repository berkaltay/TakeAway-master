package framework.base;

import framework.extentFactory.ReportFactory;
import framework.listener.Listener;
import framework.utilities.DriverManager;
import framework.utilities.ReTryTestCase;
import framework.webservice.common.Config;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import static framework.base.Browsers.prepareDriver;
import static framework.extentFactory.ReportFactory.createReportFile;

@Listeners({Listener.class})
public class TestBase {

    public static String BROWSER = null;
    public static String BASEURL = null;
    public static int RETRY;
    public String testNameFromXml = null;

    public void initializeConfig(String reTry, String browser) throws Throwable {

        BROWSER = browser;
        RETRY = Integer.parseInt(reTry);
        BASEURL = Config.getInstance().getBaseUrl();
    }

    @Parameters(value = {"reTry", "browser"})
    @BeforeSuite
    public void beforeSuite(ITestContext context, String reTry, String browser) throws Throwable {

        initializeConfig(reTry, browser);
        createReportFile();

        for (ITestNGMethod method : context.getSuite().getAllMethods()) {
            method.setRetryAnalyzer(new ReTryTestCase());
        }
    }

    @BeforeClass
    public void beforeClass() {
        testNameFromXml = this.getClass().getName();
        ReportFactory.createTest(this.getClass().getName());
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws UnsupportedEncodingException {
        DriverManager.setDriver(prepareDriver());

        getDriver().navigate().to(BASEURL);

        ReportFactory.createChildTest(testNameFromXml, method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        getDriver().quit();
        IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer();
        if (retry == null) {
            return;
        }
        result.getTestContext().getSkippedTests().removeResult(result.getMethod());
    }

    public synchronized static WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}