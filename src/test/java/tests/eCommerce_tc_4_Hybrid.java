package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.android.CartPage;
import pages.android.ProductCatalogue;
import testUtils.AndroidBaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class eCommerce_tc_4_Hybrid extends AndroidBaseTest {

    @Test(dataProvider = "getData")
    public void FillForm(HashMap<String,String> input) throws InterruptedException {

        formPage.setNameField(input.get("name"));
        formPage.setGender(input.get("gender"));
        formPage.setCountrySelection(input.get("country"));
        ProductCatalogue productCatalogue = formPage.submitForm();
        productCatalogue.addItemToCartByIndex(0);
        productCatalogue.addItemToCartByIndex(0);
        CartPage cartPage = productCatalogue.goToCartPage();

        //cartPage.waitForElementToAppear(cartPage.toolbarTitle);
        double totalSum = cartPage.getProductsSum();
        double displayFormattedSum = cartPage.getTotalAmountDisplayed();
        Assert.assertEquals(totalSum, displayFormattedSum);
        cartPage.acceptTermConditions();
        cartPage.submitOrder();

    }

    @BeforeMethod
    public void preSetup(){
        //screen to home page
        formPage.setActivity();
    }


    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "//src//test//java//TestData//eCommerce.json");
        //return new Object[][]{{"Nala","female","Argentina"},{"Leo","male","Argentina"}};
        return new Object[][]{{data.get(0)},{data.get(1)}};

    }


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
