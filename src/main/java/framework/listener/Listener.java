package framework.listener;

import com.aventstack.extentreports.MediaEntityBuilder;
import framework.base.TestBase;
import framework.extentFactory.ReportFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

import static framework.extentFactory.ReportFactory.getChildTest;
import static framework.utilities.ScreenshotTaker.takeBase64Screenshot;

public class Listener extends TestBase implements ITestListener {

    public static String testMethodNameOnTestStart;

    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
        testMethodNameOnTestStart = getTestMethodName(iTestResult);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        getChildTest().pass("Testing finished successfully!");
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
    }

    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                getChildTest().fail("Screenshot: ", MediaEntityBuilder.createScreenCaptureFromBase64String(takeBase64Screenshot(iTestResult.getTestClass().getName(), iTestResult.getMethod().getMethodName())).build());
            } catch (IOException e) {

            } finally {
                driver.quit();
            }

        }else{
            getChildTest().fail(iTestResult.getThrowable().getCause());
        }
    }

    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        iTestResult.setStatus(ITestResult.SKIP);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in onStart method " + iTestContext.getName());
    }

    public void onFinish(ITestContext iTestContext) {
        System.out.println("I am in onFinish method " + iTestContext.getName());
        ReportFactory.saveReport();
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
}