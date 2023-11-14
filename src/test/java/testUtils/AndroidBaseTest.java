package testUtils;

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
import utils.AppiumUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class AndroidBaseTest extends AppiumUtils {
    public AppiumDriverLocalService service;
    public AndroidDriver driver;
    public FormPage formPage;

    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws IOException {

        Properties prop = new Properties();
        FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//data.properties");
        String ipAddress=System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
        // explain - we use ternary to set ip via mvn if we don't specific ipaddress it will go to .property file
        prop.load(fis);
        //String ipAddress=prop.getProperty("ipAddress");
        String port=prop.getProperty("port");
        service = startAppiumServer(ipAddress, Integer.parseInt(port));
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(prop.getProperty("AndroidDeviceNames"));
        options.setChromedriverExecutable("C://Users//Wilasinee Ramirez//Driver//chromedriver.exe");
        //options.setApp("C://Users//Wilasinee Ramirez//IdeaProjects//AppiumDemo//src//test//java//Resource//ApiDemos-debug.apk");
        options.setApp(System.getProperty("user.dir")+"//src//test//resources//General-Store.apk");
        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        formPage=new FormPage(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}
