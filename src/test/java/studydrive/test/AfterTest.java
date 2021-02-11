package studydrive.test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AfterTest {

    //handle deleting all the uploaded files
    static void deleteDocuments(WebDriver driver, WebDriverWait wait){

        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector( "div#app a > img"))).click();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        try {
            boolean leaveBtnIsDisplayed = driver.findElement(By.id ("btn-leave-upload-page-confirm")).isDisplayed();

            if(leaveBtnIsDisplayed){
                WebElement leaveBtn= driver.findElement(By.id ( "btn-leave-upload-page-confirm"));
                leaveBtn.click();
            }
        } catch (NoSuchElementException e) {
        }

        wait.until(ExpectedConditions.
                visibilityOfElementLocated(
                        By.xpath("//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/a[1]"))).click();

        wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.partialLinkText( "txt")));

        //all the test files for multiple uploading must include "txt" substring
        List<WebElement> list  = driver.findElements(By.partialLinkText("txt"));

        for(int i=0; i<list.size(); i++){
            wait.until(ExpectedConditions.
                    visibilityOfElementLocated(By.partialLinkText( "txt"))).click();
            wait.until(ExpectedConditions.
                    visibilityOfElementLocated(By.xpath(
                            "//*[@id=\"app\"]/div/div[1]/div[3]/div[1]/div/div/div[1]/div[2]/div[6]/div[1]/div"))).click();

            wait.until(ExpectedConditions.
                    visibilityOfElementLocated(By.cssSelector("div#app div.popover.max-w-2xs > ul > li:nth-child(3) > span"))).click();
            wait.until(ExpectedConditions.
                    visibilityOfElementLocated(By.xpath("//*[@id=\"swal2-content\"]/div/div/button[2]"))).click();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }

    }
}
