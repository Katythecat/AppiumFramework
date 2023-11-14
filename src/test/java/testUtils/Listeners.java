package testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.IOUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AppiumUtils;

import java.io.IOException;
import java.util.Base64;

public class Listeners extends AppiumUtils implements ITestListener {
    ExtentTest test;
    ExtentReports extent=ExtentReporterNG.getReporterObject();
    AppiumDriver driver;

    @Override// this method will get executed before each and every address test method
    public void onTestStart(ITestResult result) {
        test= extent.createTest(result.getMethod().getMethodName());
    }

    @Override // this method will run after, if test successfully pass
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS,"Test Passed");


    }

    @Override // when test is failed it will run this code
    /*public void onTestFailure(ITestResult result) {
        test.fail(result.getThrowable());

        try{
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());

        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            //test.addScreenCaptureFromPath(new File(destinationFile).getAbsolutePath(), result.getMethod().getMethodName());

            test.addScreenCaptureFromPath(getScreenshotPath(result.getMethod().getMethodName(), driver), result.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        }*/

    public void onTestFailure(ITestResult result) {
           /* test.fail(result.getThrowable());

            try {
                driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
                        .get(result.getInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), driver);
                File screenshotFile = new File(screenshotPath);

                if (screenshotFile.exists()) {
                    System.out.println("Screenshot file exists: " + screenshotFile.getAbsolutePath());
                    // Provide the absolute path to the screenshot
                    test.addScreenCaptureFromPath(screenshotFile.getAbsolutePath(), result.getMethod().getMethodName());
                } else {
                    System.out.println("Screenshot file does not exist!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/


        /*test.fail(result.getThrowable());
        try {
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
            String screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), driver);
            test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        test.fail(result.getThrowable());
        try {
            // Assuming 'driver' is a member variable or accessible within this scope
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());

            // Ensure the screenshot is taken and path is correct
            //String screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), driver);

            // Add the screenshot to the Extent Report
            test.addScreenCaptureFromPath(getScreenShotPath(result.getMethod().getMethodName(),driver), result.getMethod().getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
