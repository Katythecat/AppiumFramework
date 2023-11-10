package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testUtils.AndroidBaseTest;

public class eCommerce_tc_1 extends AndroidBaseTest {

    @BeforeMethod
    public void preSetup(){
        //screen to home page
        formPage.setActivity();
    }

    @Test
    public void FillFormErrorValidation() {
        //driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Nala");
        driver.hideKeyboard();
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator
                ("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"))"));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();

        driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        //capture toast msg
        String toast=driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("text");
        Assert.assertEquals(toast,"Please enter your name");

    }

    @Test
    public void FillFormPositiveFlow() {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Nala");
        driver.hideKeyboard();
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        driver.findElement(AppiumBy.androidUIAutomator
                ("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"))"));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();

        driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        //capture toast msg
        Assert.assertTrue(driver.findElements(By.xpath("(//android.widget.Toast)[1]")).size()<1);


    }
}
