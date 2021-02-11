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

public class SingleSmallDocumentUpload_test {
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
        //test uploading single small document/ smaller than 100MB
    void uploadSingleSmallDocument() throws InterruptedException, AWTException {
        Setup.uploadSingleFile(driver, wait, smallFilePath);
        Setup.fillUpForm(driver, wait);
        driver.findElement(By.id("finish-upload")).click();
        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.id ("upload-more-files")));
        WebElement uploadStatus = driver.findElement(By.cssSelector("div#app div.ml-3"));

        String str = uploadStatus.getText();
        System.out.println(str);
        Assertions.assertEquals("Upload completed", str);
    }
}
