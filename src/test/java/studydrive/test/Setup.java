package studydrive.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//methods to use before and during the test
public class Setup {

    static String username = "Massoudrmz@gmail.com"; //the email address is verified
    static String password = "@Massoud1234";
    static String unVerifiedUsername = "notverified@notverified.com"; //the email address is not verified


    //accept cookies and click on login button
    static void clickOnLoginButton(WebDriver driver){
        WebElement root1 = driver.findElement(By.cssSelector("#usercentrics-root"));
        WebElement shadowRoot1 = (WebElement) ((JavascriptExecutor) driver).executeScript("return " +
                "arguments[0].shadowRoot",root1);
        WebElement element1 = shadowRoot1.findElement(By.cssSelector(".sc-iIEYCM.iGZzXW"));
        WebElement element2 = element1.findElement(By.cssSelector(".sc-fvhGYg.jPZDSG"));
        WebElement element3 = element2.findElement(By.cssSelector(".sc-xyEjG.fFnpOK"));
        WebElement element4 = element3.findElement(By.cssSelector(".sc-dWdcrH.ldstxH"));
        WebElement element5 = element4.findElement(By.cssSelector(".sc-biBrSq.fUrceT"));
        WebElement element6 = element5.findElement(By.cssSelector(".sc-hzMMCg.kRzfai"));
        WebElement loginBtn = element6.findElement(By.cssSelector("button.sc-bdfBwQ.fCTAhd"));
        loginBtn.click();
    }

    //login as verified user
    static void loginVerifiedUser(WebDriver driver){
        driver.findElement(By.cssSelector("div#app button.mx-5.md\\3a mx-9")).click();
        WebElement usernameField = driver.findElement(By.cssSelector("div#app input.mb-4.border"));
        usernameField.clear();
        usernameField.sendKeys(username);
        WebElement passwordField = driver.findElement(By.cssSelector("div#app input.mb-5.border"));
        passwordField.clear();
        passwordField.sendKeys(password);
        driver.findElement(By.cssSelector("div#app span > span")).click();
    }

    //login as unverified user
    static void loginUnVerifiedUser(WebDriver driver){
        driver.findElement(By.cssSelector("div#app button.mx-5.md\\3a mx-9")).click();
        WebElement usernameField = driver.findElement(By.cssSelector("div#app input.mb-4.border"));
        usernameField.clear();
        usernameField.sendKeys(unVerifiedUsername);
        WebElement passwordField = driver.findElement(By.cssSelector("div#app input.mb-5.border"));
        passwordField.clear();
        passwordField.sendKeys(password);
        driver.findElement(By.cssSelector("div#app span > span")).click();
    }

    //go to document upload page
    static void goToCreateDocument(WebDriver driver, WebDriverWait wait){
        WebElement addBtn;
        addBtn = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector( "button#add-button")));
        addBtn.click();

        WebElement createDocumentBtn = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector( "div#app div:nth-child(5) > div > ul > li:nth-child(1) > a")));
        createDocumentBtn.click();
    }

    //handle uploading a single file by use of Robot class
    static void uploadSingleFile(WebDriver driver, WebDriverWait wait, String filePath) throws AWTException, InterruptedException {
        driver.findElement(By.cssSelector(
                "div#app div.bg-blue-200.shadow.md\\3a rounded-lg.flex.flex-col.absolute.md\\" +
                        "3a relative.inset-0.mt-20.md\\3a mt-0.md\\3a px-12\\2c > " +
                        "div > div > form > div > div > input[name=\"phrase\"]")).click();

        WebElement courseList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector( "div#app div.bg-blue-200.shadow.md\\" +
                        "3a rounded-lg.flex.flex-col.absolute.md\\3a relative.inset-0.mt-20.md\\3a mt-0.md\\3a px-12\\2c " +
                        "> div > div > form > div > div > div > div > div > ul > li > button[type=\"button\"]" +
                        " > span > span.list-item.text-black-500 > span")));
        courseList.click();
        WebElement uploadFileBtn = driver.findElement(By.cssSelector("div#dropzone button"));
        // using linkText, to click on uploadFileBtn element
        uploadFileBtn.click(); // Click on uploadFileBtn option on the webpage
        Thread.sleep(2000); // suspending e
        // xecution for specified time period

        // creating object of Robot class
        Robot rb = new Robot();

        // copying File path to Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        // press Contol+V for pasting
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
    }

    //handle uploading multiple files at once by use of Robot class
    static void uploadMultipleFiles(WebDriver driver, WebDriverWait wait, String folderPath, String filesName) throws AWTException, InterruptedException {
        driver.findElement(By.cssSelector(
                "div#app div.bg-blue-200.shadow.md\\3a rounded-lg.flex.flex-col.absolute.md\\" +
                        "3a relative.inset-0.mt-20.md\\3a mt-0.md\\3a px-12\\2c > " +
                        "div > div > form > div > div > input[name=\"phrase\"]")).click();

        WebElement courseList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector( "div#app div.bg-blue-200.shadow.md\\" +
                        "3a rounded-lg.flex.flex-col.absolute.md\\3a relative.inset-0.mt-20.md\\3a mt-0.md\\3a px-12\\2c " +
                        "> div > div > form > div > div > div > div > div > ul > li > button[type=\"button\"]" +
                        " > span > span.list-item.text-black-500 > span")));
        courseList.click();
        WebElement uploadFileBtn = driver.findElement(By.cssSelector("div#dropzone button"));
        // using linkText, to click on uploadFileBtn element
        uploadFileBtn.click(); // Click on uploadFileBtn option on the webpage
        Thread.sleep(2000);
        // xecution for specified time period

        // creating object of Robot class
        Robot rb = new Robot();

        // copying File path to Clipboard
        StringSelection folder = new StringSelection(folderPath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(folder, null);

        // press Contol+V for pasting
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(4000);

        StringSelection files = new StringSelection(filesName);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(files, null);

        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }

    //fill the form for single file upload
    static void fillUpForm(WebDriver driver, WebDriverWait wait){
        WebElement field1 = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector
                        ( "div#app div.mb-4.w-full.fieldBoxLeft.md\\3a pr-4 > input:nth-child(2)")));
        field1.sendKeys("mrb-test1");
        WebElement field2 = driver.findElement(By.id("vs1__combobox"));
        field2.click();
        driver.findElement(By.cssSelector("li#vs1__option-3 div")).click();

        WebElement field3 = driver.findElement(By.id("vs2__combobox"));
        field3.click();
        driver.findElement(By.cssSelector("li#vs2__option-2 div")).click();
    }

    //fill the forms for multiple files upload
    static void fillUpForms(WebDriver driver, WebDriverWait wait){
        WebElement fieldDescription1 = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath
                        ( "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]" +
                                "/div/div/div[1]/div[2]/div[1]/div[2]/" +
                                "div[3]/div[2]/div[1]/input[2]")));
        fieldDescription1.sendKeys("mrbtest1");

        WebElement fieldSemester = driver.findElement(By.xpath("//*[@id=\"vs1__combobox\"]/div[1]/input"));
        fieldSemester.click();
        driver.findElement(By.cssSelector("li#vs1__option-3 div")).click();
        driver.findElement(By.xpath(
                "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/div/div/div[1]/div[2]/div[1]/div[2]/" +
                        "div[3]/div[2]/div[2]/div[2]/div[2]/button")).click();

        WebElement fieldDocument = driver.findElement(By.id("vs2__combobox"));
        fieldDocument.click();
        driver.findElement(By.cssSelector("li#vs2__option-2 div")).click();
        driver.findElement(By.xpath(
                "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]/div/div/div[1]/div[2]/div[1]/div[2]/" +
                        "div[3]/div[3]/div[2]/div/div[2]/button")).click();

        WebElement fieldDescription2 = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath
                        ( "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]" +
                        "/div/div/div[1]/div[2]/div[1]/div[2]/" +
                        "div[4]/div[2]/div[1]/input[2]")));
        fieldDescription2.sendKeys("mrbtest2");

        WebElement fieldDescription3 = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath
                        ( "//*[@id=\"app\"]/div/div[1]/div[3]/div[2]" +
                                "/div/div/div[1]/div[2]/div[1]/div[2]/" +
                                "div[5]/div[2]/div[1]/input[2]")));
        fieldDescription3.sendKeys("mrbtest3");
    }

    static String catchTheFirstLine(String filePath){
        List<String> fileLines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                fileLines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileLines.get(0);
    }

    static String getFirstLinePdf(String filepath){
        List<String> fileLines = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(filepath))) {

            document.getClass();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);

                // split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                    fileLines.add(line);
                }
            }

        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileLines.get(0));
        return fileLines.get(0);
    }
    

}
