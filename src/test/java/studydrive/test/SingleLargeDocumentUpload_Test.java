package studydrive.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class SingleLargeDocumentUpload_Test {
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
        AfterTest.deleteDocuments(driver, wait);
        driver.quit();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test //test uploading large documents/over 100MB
    void uploadSingleLargeDocument() throws InterruptedException, AWTException {
        Setup.uploadSingleFile(driver, wait, largeFilePath);
        Setup.fillUpForm(driver, wait);
        driver.findElement(By.id("finish-upload")).click();

        waitLong.until(ExpectedConditions.
                visibilityOfElementLocated(By.id ("upload-more-files")));
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement uploadStatus = driver.findElement(By.xpath (
                "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/div" +
                        "/div/div[1]/div[2]/div[1]/div[2]/div[3]/div[4]/div/div[2]"));
        boolean fileIsTooBig = uploadStatus.getText().contains("Sorry! The file you're trying to upload is too big");

        Assertions.assertTrue(fileIsTooBig);
    }
}
