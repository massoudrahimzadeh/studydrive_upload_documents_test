package studydrive.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.ArrayList;

public class Download_test {
    static WebDriver driver = new ChromeDriver();
    WebDriverWait waitLong=new WebDriverWait(driver, 600000);
    WebDriverWait wait=new WebDriverWait(driver, 10);
    Actions act = new Actions(driver);

    String smallFilePath = "E:\\studydrive-task\\studyDriveTask_uploadTest\\dummyFiles\\file-txt_PDF_1MB.pdf";

    @BeforeEach
        //Accept cookies / Login / go to the upload page
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver.get("https://www.studydrive.net/");
        driver.manage().window().maximize();
        Setup.clickOnLoginButton(driver);
        Setup.loginVerifiedUser(driver);
        Setup.goToCreateDocument(driver, wait);
    }

    @AfterEach
        //delete all the uploaded files and quite the WebDriver
    void tearDown() {
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"app\"]/div/div[1]/div[3]/div[1]/div/div/div[1]/div[2]/div[6]/div[1]/div"
                ))).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector(
                        "div#app div.popover.max-w-2xs > ul > li:nth-child(3) > span"
                ))).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//*[@id=\"swal2-content\"]/div/div/button[2]/span/span"))).click();
        driver.quit();

    }

    @Test
    void courseMatch() throws InterruptedException, AWTException {

        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/" +
                "div/div/div[1]/div[1]/div[1]/div/div/form/div/div/input")).click();

        Setup.uploadSingleFile(driver, wait, smallFilePath);
        Setup.fillUpForm(driver, wait);
        driver.findElement(By.id("finish-upload")).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText ("Open document"))).click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath (
                        "//*[@id=\"app\"]/div/div[1]/div[3]/div[1]/div/div/div[1]/div[2]/div[3]"))).click();
        String confirmDownloaded = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector (
                "div#app div.flash-message.fixed.z-above-sweetalert.top-0.right-0.p-4.flex.flex-col.w-full.md" +
                        "\\3a w-160 > div"))).getText();

        Assertions.assertTrue(confirmDownloaded.contains("Download successful"));

    }
}
