package framework.base;

import framework.extentFactory.ReportFactory;
import framework.listener.Listener;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import static framework.extentFactory.ReportFactory.createReportFile;

@Listeners({Listener.class})
public class WSTestBase extends TestBase {

    public String testNameFromXml = null;

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        createReportFile();
    }

    @BeforeClass
    public void beforeClass() {
        testNameFromXml = this.getClass().getName();
        ReportFactory.createTest(this.getClass().getName());
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        ReportFactory.createChildTest(testNameFromXml, method.getName());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {

    }
}
