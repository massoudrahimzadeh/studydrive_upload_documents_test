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
import studydrive.test.AfterTest;

import java.awt.*;
import java.util.List;

public class AsynchronousDocumentsUpload_Test {
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

    @Test
        //test the upload process is asynchronous
    void asynchronousUpload() throws InterruptedException, AWTException {
        Setup.uploadMultipleFiles(driver, wait, folderPath, files);
        Setup.fillUpForms(driver, wait);
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.id("finish-upload"))).click();
        Thread.sleep(1500);
        List<WebElement> uploadStatuses = driver.findElements(By.className("ml-3"));
        int numberOfDocumentsUploadingInTheSameTime = 0;
        for(WebElement element: uploadStatuses){
            if(element.getText().contains("Uploading"))
                numberOfDocumentsUploadingInTheSameTime++;
        }
        Thread.sleep(25000);

        Assertions.assertEquals(1, numberOfDocumentsUploadingInTheSameTime);
    }
}
