package tests.us0006;


import org.openqa.selenium.Keys;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Us0006;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.io.IOException;


public class US_0006 {
    Us0006 roomsPage=new Us0006();
    Select select;
    @Test
    public void TC_0001(){
        roomsPage.girisYap();
        roomsPage.hotelManagementButonu.click();
        roomsPage.hotelRoomsButonu.click();
    }

    @Test(dependsOnMethods = "TC_0001")
    public void TC_0002(){
        String expectedResult="LIST OF HOTELROOMS";
        String actualResult=roomsPage.listOfHotelRoomsElementi.getText();
        Assert.assertTrue(actualResult.contains(expectedResult));
        roomsPage.addHotelRoomButonu.click();
    }
    @Test(dependsOnMethods = "TC_0002")
    public void TC_0003() throws IOException {
        String expectedResult="Create Hotelroom";
        String actualResult=roomsPage.createHotelRoomElementi.getText();
        Assert.assertEquals(actualResult,expectedResult);
        select=new Select(roomsPage.idHotelElementi);
        select.selectByVisibleText("Anemon");
        roomsPage.otelEklemeCode.sendKeys(ConfigReader.getProperty("otelRoomCode"));
        roomsPage.otelEklemeName.sendKeys("İzmir");
        roomsPage.locationBox.sendKeys("İzmir" + Keys.PAGE_DOWN);
        roomsPage.textBox.sendKeys("Das Hotel ist schön.");
        roomsPage.priceBox.sendKeys("300"+Keys.PAGE_DOWN);
        Select select1=new Select(roomsPage.roomTypeElementi);
        System.out.println(roomsPage.roomTypeElementi.getText());
        select1.selectByVisibleText("Triple");
        roomsPage.maxAdultCountElementi.sendKeys("2");
        roomsPage.maxChildElementi.sendKeys("2");
        roomsPage.approvedButonu.click();
        roomsPage.saveButonu.click();
        ReusableMethods.waitFor(7);
        String actualText=roomsPage.successfullyElementi.getText();
        String expectedText=ConfigReader.getProperty("basariliKayit");
        Assert.assertEquals(actualText, expectedText);
        ReusableMethods.getScreenshot("Basarili Kayit");
        roomsPage.kabul.click();
    }

    @Test(dependsOnMethods = "TC_0003")
    public void TC_0004()  {
        Actions actions=new Actions(Driver.getDriver());
        actions.sendKeys(Keys.PAGE_UP).sendKeys(Keys.PAGE_UP).perform();
        ReusableMethods.waitFor(3);
        roomsPage.hotelRoomsButonu.click();
        select=new Select(roomsPage.roomListIdHotelElementi);
        select.selectByVisibleText("Anemon");

    }

    @Test(dependsOnMethods = "TC_0004")
    public void TC_0005() throws IOException {
        Actions actions=new Actions(Driver.getDriver());
        roomsPage.searchButton.click();
        ReusableMethods.waitFor(3);
        roomsPage.detailsButonu.click();
        Assert.assertTrue(roomsPage.editHotelRoomElementi.isDisplayed());
        System.out.println(roomsPage.editHotelRoomElementi);
        ReusableMethods.waitFor(4);
        actions.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).perform();
        ReusableMethods.waitFor(2);
        roomsPage.roomDeleteButonu.click();
        ReusableMethods.waitFor(3);
        ReusableMethods.getScreenshot("Room Delete");
        String actualroomDelete=roomsPage.roomClearElement.getText();
        String expectedroomDelete=ConfigReader.getProperty("clearElement");
        Assert.assertEquals(actualroomDelete,expectedroomDelete);
        System.out.println(roomsPage.roomClearElement.getText());
        roomsPage.silindiElementi.click();
        ReusableMethods.waitFor(5);
        roomsPage.tekrarLoginUsernameBox.sendKeys(ConfigReader.getProperty("HMCValidUsername"));
        roomsPage.tekrarLoginPasswordBox.sendKeys(ConfigReader.getProperty("HMCValidPassword"));
        roomsPage.tekrarLoginButonu.click();
        ReusableMethods.waitFor(7);
        Assert.assertTrue(roomsPage.listOfHotelRoomsElementi.isDisplayed());
        Driver.getDriver().close();

    }
}
