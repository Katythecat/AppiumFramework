import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.messages.types.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.android.CartPage;
import pages.android.FormPage;
import pages.android.ProductCatalogue;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class eCommerce_tc_4_Hybrid extends BaseTest {

    @Test
    public void FillForm() throws InterruptedException {

        formPage.setNameField("Nala");
        formPage.setGender("female");
        formPage.setCountrySelection("Argentina");
        ProductCatalogue productCatalogue= formPage.submitForm();
        productCatalogue.addItemToCartByIndex(0);
        productCatalogue.addItemToCartByIndex(0);
        CartPage cartPage=productCatalogue.goToCartPage();

        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")),"text","Cart"));

        double totalSum=cartPage.getProductsSum();
        double displayFormattedSum=cartPage.getTotalAmountDisplayed();
        Assert.assertEquals(totalSum,displayFormattedSum);
        cartPage.acceptTermConditions();
        cartPage.submitOrder();


        /*List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count=productPrices.size();
        double totalSum=0;
        for (int i = 0; i <count ; i++) {
            String amountString=productPrices.get(i).getText();
            Double price=getFormattedAmount(amountString);//string will start from first index = 160.97
            totalSum+=price;

        }
        String displaySum=driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double displayFormattedSum=getFormattedAmount(displaySum);

        Assert.assertEquals(totalSum,displayFormattedSum);

        longPressAction(driver.findElement(By.id("com.androidsample.generalstore:id/termsButton")));
        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(6000);
        //we switch to browser now*/
       /* Set<String> contexts = driver.getContextHandles(); // will get you all the context that present
        for (String contextName:contexts) {
            System.out.println(contextName);
        }
        // ask dev to give the correct context name
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("rahul shetty academy");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));// use for click on back button on mobile
        driver.context("NATIVE_APP");// switch back to mobile app*/












    }
}
