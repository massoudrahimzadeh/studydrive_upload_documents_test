package studydrive.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UnverifiedUserTest {

    WebDriver driver = new ChromeDriver();
    WebDriverWait wait=new WebDriverWait(driver, 20);

    @BeforeEach  //accept cookies / login/ go to create document page
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver.get("https://www.studydrive.net/");
        Setup.clickOnLoginButton(driver);
        Setup.loginUnVerifiedUser(driver);
        Setup.goToCreateDocument(driver, wait);
    }

    @AfterEach //quite the WebDriver
    void tearDown() {
        driver.quit();
    }

    @Test //test if unverified users has access to the upload function
    void unverifiedUserUploadTest() {
        WebElement verifyYourEmail = driver.findElement(By.cssSelector("div#swal2-content h3"));
        String verification = verifyYourEmail.getText();
        Assertions.assertTrue(verification.contains("Verify your email address to use all features"));
    }
}
