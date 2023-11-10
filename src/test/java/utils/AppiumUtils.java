package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public abstract class AppiumUtils {

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


}
