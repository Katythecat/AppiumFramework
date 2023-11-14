package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public abstract class AppiumUtils {
    ExtentTest test;

    public AppiumDriverLocalService service;
     //this class is used for methods that we use in android and IOS

    // or we can use AppiumDriver can use for both IOS and Android
    public Double getFormattedAmount(String amount){
        Double price=Double.parseDouble(amount.substring(1));
        return price;
    }

    public List<HashMap<String,String>> getJsonData(String jsonFilePath) throws IOException {
        //conver json file content to json string
        //System.getProperty("user.dir") + "//src//test//java//TestData//eCommerce.json"
        String jsonContent = FileUtils.readFileToString(new File(jsonFilePath),StandardCharsets.UTF_8);

        ObjectMapper mapper=new ObjectMapper();
        List<HashMap<String,String>> data = mapper.readValue(jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {
    });
        return data;

    }

    public AppiumDriverLocalService startAppiumServer(String ipAddress,int port ){

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .withAppiumJS(new File("C://Users//Wilasinee Ramirez//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .usingDriverExecutable(new File("C://Program Files//nodejs//node.exe"))
                .usingPort(port)
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withLogFile(new File("Appiumlog.txt"))
                .withIPAddress(ipAddress);
        //.withArgument(GeneralServerFlag.BASEPATH, "wd/hub") for java client less than 8
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        System.out.println(service.getUrl());
        System.out.println(service.isRunning());
        return service;

    }

    public void waitForElementToAppear(WebElement ele, AppiumDriver driver){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains((ele),"text","Cart"));

    }

    /*public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {
        File source= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String destinationFile = System.getProperty("user.dir")+File.separator+"//screenshots"+File.separator+testCaseName+"_"+timestamp+".png";
        FileUtils.copyFile(source, new File(destinationFile));
        //File screenshotFile = new File(destinationFile);
        System.out.println("Screenshot captured.");
        return destinationFile;
        //1. capture screenshot place in folder
        //2. extent report pick file and attach to report

    }*/

    /*public static String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {

        File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String destinationFile = System.getProperty("user.dir")+"//screenshots//"+testCaseName+"_"+timestamp+".png";
        FileUtils.copyFile(source, new File(destinationFile));

        return destinationFile;
    }*/

    public String getScreenShotPath(String testCaseName, AppiumDriver driver) throws IOException {

        File srcFile= ((TakesScreenshot)driver ).getScreenshotAs((OutputType.FILE));
        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String destination = System.getProperty("user.dir")+"//screenshots//" +testCaseName+"_"+timestamp+".png";

        FileUtils.copyFile(srcFile, new File(destination));
        InputStream is = new FileInputStream(destination);
        byte[] ssBytes = IOUtils.toByteArray(is);
        String base64 = Base64.getEncoder().encodeToString(ssBytes);
        //test.log(Status.FAIL, (Markup) test.addScreenCaptureFromPath(new File(destination).getAbsolutePath()));
        test.log(Status.FAIL, (Markup) test.addScreenCaptureFromBase64String("data:image/png:base64,"+base64));
        return destination;
    }



}
