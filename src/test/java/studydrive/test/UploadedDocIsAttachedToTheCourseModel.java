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
import java.util.List;

public class UploadedDocIsAttachedToTheCourseModel {
    static WebDriver driver = new ChromeDriver();
    WebDriverWait waitLong=new WebDriverWait(driver, 600000);
    WebDriverWait wait=new WebDriverWait(driver, 10);
    Actions act = new Actions(driver);

    String smallFilePath = "E:\\studydrive-task\\studyDriveTask_uploadTest\\dummyFiles\\file-txt_PDF_1MB.pdf";
    String largeFilePath = "E:\\studydrive-task\\studyDriveTask_uploadTest\\dummyFiles\\mrbtest.txt";
    String folderPath = "E:\\studydrive-task\\studyDriveTask_uploadTest\\dummyFiles";
    String files = "\"mrbtest2.txt\" \"mrbtest3.txt\" \"mrbtest4.txt\" ";
    int numberOfTestFiles = 3;

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
        driver.findElement(By.cssSelector("div#app div:nth-child(6) > div.relative.cursor-pointer.btn-menu-tooltip-wrapper > div")).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector("div#app div.popover.max-w-2xs > ul > li:nth-child(3) > span"))).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//*[@id=\"swal2-content\"]/div/div/button[2]/span/span"))).click();
        driver.quit();

    }

    @Test
    void courseMatch() throws InterruptedException, AWTException {

        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/" +
                "div/div/div[1]/div[1]/div[1]/div/div/form/div/div/input")).click();
        String course = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(
                        "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/div/div/div[1]" +
                                "/div[1]/div[1]/div/div/form/div/div/div/div/div" +
                                "/ul/li/button/span/span[1]/span"))).getText();
        System.out.println(course);
        String module = driver.findElement(By.xpath(
                        "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/div/div/div[1]/" +
                                "div[1]/div[1]/div/div/form/div/div/div/div/div/ul" +
                                "/li/button/span/span[2]")).getText();


        System.out.println(module);

        Setup.uploadSingleFile(driver, wait, smallFilePath);
        Setup.fillUpForm(driver, wait);
        driver.findElement(By.id("finish-upload")).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText ("Open document"))).click();
        List<String> tabs = new ArrayList(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(1));

        Thread.sleep(2000);

        String moduleName = driver.findElement(By.xpath(
                "//*[@id=\"app\"]/div/div[1]/div[3]/div[1]/div/div/div[3]/a[1]")).getText();

        System.out.println(moduleName);
        String courseName = driver.findElement(By.xpath(
                "//*[@id=\"app\"]/div/div[1]/div[3]/div[1]/div/div/div[3]/a[2]")).getText();
        System.out.println(courseName);
        courseName = courseName.strip(); moduleName = moduleName.strip();
        course = course.strip(); module = module.strip();
        Assertions.assertTrue(course.contains(courseName));
        Assertions.assertEquals(moduleName, module);
    }
}
