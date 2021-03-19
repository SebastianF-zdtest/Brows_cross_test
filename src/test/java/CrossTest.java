import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

//import org.junit.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import org.testng.TestNGException;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CrossTest {
    WebDriver driver;


    @BeforeTest
    public void setup_drivers(@NotNull @Optional("browser") String browser) throws TestNGException, InterruptedException {


        if (browser.equalsIgnoreCase("firefox")){
            /** Type the path webdriver of Chrome browser
             *  e.g."<D:\\SeleniumRobot\\chromedriver.exe>"*/
            System.setProperty("webdriver.gecko.driver", "<Your\\...\\path\\of\\Firefox\\webdriver>");
            driver = new FirefoxDriver();

            /** Here type correct URL of website under test    */
            String url = "<https://place.to/write/your/url>";
            driver.get(url);
            driver.manage().window().maximize();

        }

        if (browser.equalsIgnoreCase("chrome")){
            /** Type the path webdriver of Chrome browser
             *  e.g."<D:\\SeleniumRobot\\chromedriver.exe>"*/
            System.setProperty("webdriver.chrome.driver", "<Your\\...\\path\\of\\Chrome\\webdriver>");
            driver = new ChromeDriver();

            /** Here type correct URL of website under test    */
            String url = "<https://place.to/write/your/url>";
            driver.get(url);
            driver.manage().window().maximize();
        }

        else if (browser.equalsIgnoreCase("edge")){
            /** Type the path webdriver of Edge browser
             *  e.g."<D:\\SeleniumRobot\\msedgedriver.exe>"*/
            System.setProperty("webdriver.edge.driver", "<Your\\...\\path\\of\\Edge\\webdriver>");
            driver = new EdgeDriver();

            /** Here type correct URL of website under test    */
            String url = "<https://place.to/write/your/url>";
            driver.get(url);
            driver.manage().window().maximize();
        }
        TimeUnit.SECONDS.sleep(15);

    }

    @Test

    public void TestBrowsers() throws Exception {

        /** Here type correct URL of website under test    */
        String url= "<https://place.to/write/your/url>";
        driver.get(url);
        /** Type the path and name of the screenshot which
         *  will taken after click interaction
         *  e.g. "C:\\BrowsersCrossTest\\ScreenshotFromTest.jpg"    */
        takeSnapShot2(driver, "<Your\\...\\path\\ScreenshotAfterAction.png>");

        WebElement canvasFirst = driver.findElement(By.id("#canvas"));

        int width = canvasFirst.getSize().getWidth();
        int height = canvasFirst.getSize().getHeight();

        Actions FirstClick = new Actions(driver);
        FirstClick.moveByOffset(1030, 574).click().build().perform();


        TimeUnit.SECONDS.sleep(15);

        /** Type the path and name of the screenshot which
         *  will taken after click interaction
         *  e.g. "C:\\BrowsersCrossTest\\ScreenshotFromTest.jpg"    */
        takeSnapShot2(driver, "<Your\\...\\path\\ScreenshotAfterAction.png>");
    }

    public static void takeSnapShot2(WebDriver driver, String fileWithPath) throws Exception {

        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }


    @AfterTest

    public void ConsoleError () throws TestNGException, IOException {

        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        logEntries.getAll().stream().map(LogEntry::toString).collect(Collectors.toList());
        java.util.List<String> lines = logEntries.getAll().stream().map(LogEntry::toString).collect(Collectors.toList());

        for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage() + "");
        }
        WebElement canvas = driver.findElement(By.id("#canvas"));

        int width2 = canvas.getSize().getWidth();
        int height2 = canvas.getSize().getHeight();

        String currentUrl = driver.getCurrentUrl();

        /** Type the path and name of the .txt file which will be the generated with DevTool log from Browser
         *  e.g. "C:\\BrowsersCrossTest\\DevTool_Log.txt"   */
        FileWriter textFile = new FileWriter("<Your\\...\\path\\LogFile.txt>");

        textFile.write(width2 + "" + "Width");
        textFile.write("\n");
        textFile.write("\n");
        textFile.write(height2 + " " + "Height");
        textFile.write("\n");
        textFile.write("\n");
        textFile.write(currentUrl);
        textFile.write("\n");
        textFile.write("\n");
        textFile.write(String.valueOf(lines));

        textFile.close();
        System.out.println("Successfully wrote to the file.");

        driver.quit();

    }

}
