import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.android.FormPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    public AppiumDriverLocalService service;
    public AndroidDriver driver;
    public FormPage formPage;
    @BeforeClass
    public void configureAppium() throws MalformedURLException {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .withAppiumJS(new File("C://Users//Wilasinee Ramirez//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
                .usingDriverExecutable(new File("C://Program Files//nodejs//node.exe"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withLogFile(new File("Appiumlog.txt"))
                .withIPAddress("127.0.0.1");
        //.withArgument(GeneralServerFlag.BASEPATH, "wd/hub") for java client less than 8
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        System.out.println(service.getUrl());
        System.out.println(service.isRunning());
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Cat");
        options.setChromedriverExecutable("C:\\Users\\Wilasinee Ramirez\\Driver\\chromedriver.exe");
        //options.setApp("C://Users//Wilasinee Ramirez//IdeaProjects//AppiumDemo//src//test//java//Resource//ApiDemos-debug.apk");
        options.setApp("C://Users//Wilasinee Ramirez//IdeaProjects//AppiumDemo//src//test//java//Resource//General-Store.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        formPage=new FormPage(driver);
    }

    public void longPressAction(WebElement element){
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
                        "duration",2000));
    }

    public void longPressA(AndroidDriver driver, WebElement element){
        Point location = element.getLocation();
        Dimension size= element.getSize();

        //Point centerOfElement = getCenterOfElement(location,size);
    }

    public void scrollToEndAction(){
        boolean canScrollMore;
        do{
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0
            ));
        }while(canScrollMore);
    }

    public void swipeAction(WebElement element, String direction){
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75));

    }

    public Double getFormattedAmount(String amount){
        Double price=Double.parseDouble(amount.substring(1));
        return price;
    }



    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}
