package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        @Test
        public void testCase01() throws InterruptedException {
                boolean status = false;
                System.out.println("Test case 01 started");
                status = Wrappers.gotoURL(driver, "https://www.youtube.com");
                status = Wrappers.checkURL(driver, "youtube");
                Assert.assertTrue(status, "Testcase 01 Failed");

                // Click on "About" at the bottom of the sidebar.
                status = Wrappers.optionSelect(driver,
                                By.xpath("//*[@id='footer']/div/a[text()='About']"));
                Assert.assertTrue(status, "Test case 01 failed");

                // Print the message on the About screen
                status = Wrappers.getMessage(driver,
                                By.xpath("//section[@class='ytabout__content']"));
                Thread.sleep(2000);
                Assert.assertTrue(status, "Test case 01 failed");
                System.out.println("Test case 01 ended");
        }

        @Test
        public void testCase02() throws InterruptedException {
                SoftAssert softAssert = new SoftAssert();
                boolean status = false;
                System.out.println("Test case 02 started");
                driver.navigate().back();
                // Navigate to "Movies" tab in sidebar and in the “Top Selling” section, scroll
                // to the extreme right.
                status = Wrappers.sideBarOptn(driver, "Movies");
                Assert.assertTrue(status, "Test case 02 failed");
                Thread.sleep(2000);
                WebElement sectionReq = Wrappers.reqSection(driver, "Top selling");
                status = Wrappers.scrollOpt(driver, sectionReq);
                Thread.sleep(2000);
                Assert.assertTrue(status, "Test case 02 failed");

                // Checking using Soft Assert on whether the movie is marked “A” for Mature
                // or not and if the movie is from the given category list.

                WebElement lastElement = Wrappers.lastEle(driver, sectionReq,
                                By.xpath("//ytd-grid-movie-renderer"));
                Thread.sleep(2000);
                String s = Wrappers.elementText(lastElement, By.xpath(".//div[2]/p"));
                softAssert.assertEquals(s, "A", "Movie is not marked A for Mature");

                s = Wrappers.elementText(lastElement,
                                By.xpath(".//a/span[contains(@class,'ytd-grid-movie-renderer')]"));
                String[] category = { "Comedy", "Animation", "Drama" };

                boolean check = Wrappers.checkMovieCategory(s, category);
                Thread.sleep(2000);

                softAssert.assertTrue(check, "Movie category is not from the given list");
                Assert.assertTrue(status, "Test case 02 failed");
                System.out.println("Test case 02 ended");
        }

        @Test
        public void testCase03() throws InterruptedException {
                boolean status = false;
                SoftAssert softAssert = new SoftAssert();
                System.out.println("Test case 03 started");

                // Navigate to the "Music" tab in sidebar and in the 1st "India's Biggest Hits"
                // section.
                // Print the name of the playlist on the most right.

                status = Wrappers.sideBarOptn(driver, "Music");

                WebElement firstPlayList = Wrappers.reqSection(driver, "s Biggest Hits");

                WebElement lastOne = Wrappers.lastEle(driver, firstPlayList,
                                By.xpath(".//ytd-rich-item-renderer[not(@hidden)]"));

                String s = Wrappers.elementText(lastOne, By.xpath(".//a/span"));
                System.out.println("Most right playList: " + s);

                // Soft Assert on whether the number of tracks listed is less than or equal to
                // 50 or not.

                int i = Wrappers.numberElement(lastOne, By.xpath(".//badge-shape/div[2]"));
                System.out.println("Number of tracks listed: " + i);
                softAssert.assertEquals(i <= 50, "True",
                                "Number of traks listed is greater than 50");
                Assert.assertTrue(status, "Test case 03 failed");
                System.out.println("Test case 03 ended");
        }

        @Test
        public void testCase04() throws InterruptedException {
                boolean status = false;
                SoftAssert softAssert = new SoftAssert();
                System.out.println("Test case 04 started");

                // Navigate to the "News" tab in sidebar and in the "Latest news posts"
                // section. Print the title and body of the 3 latest news posts.
                Thread.sleep(2000);
                status = Wrappers.sideBarOptn(driver, "News");

                Thread.sleep(5000);
                WebElement latestNewsList = Wrappers.reqSection(driver, "Latest news posts");
                Thread.sleep(5000);
                List<WebElement> latestNews = Wrappers.listNews(driver,
                                By.xpath(".//ytd-post-renderer"));
                Thread.sleep(2000);
                Wrappers.titleBody(driver, latestNews);

                // Print the number of likes on all 3 of them.

                int sumLike = Wrappers.sumLikeElements(latestNews,
                                By.xpath(".//span[@id='vote-count-middle']"));

                System.out.println("Sum of likes on all 3 of them: " + sumLike);

                Thread.sleep(2000);
                Assert.assertTrue(status, "Test case 04 failed");
                System.out.println("Test case 04 ended");
        }

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}