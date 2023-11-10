package testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReporterNG;

public class Listeners implements ITestListener {
    ExtentTest test;
    ExtentReports extent=ExtentReporterNG.getReporterObject();

    @Override// this method will get executed before each and every address test method
    public void onTestStart(ITestResult result) {
        test= extent.createTest(result.getMethod().getMethodName());
    }

    @Override // this method will run after, if test successfully pass
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS,"Test Passed");

    }

    @Override // when test is failed it will run this code
    public void onTestFailure(ITestResult result) {
        //normally we will write take screenshot code here
        test.fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
