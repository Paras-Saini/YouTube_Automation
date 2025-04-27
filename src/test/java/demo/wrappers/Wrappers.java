package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    // Method to navigate to the given URL
    public static boolean gotoURL(WebDriver driver, String url) {
        boolean status = false;
        try {
            driver.get(url);
            status = true;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return status;
    }

    // Method to check if the URL contains the given string
    public static boolean checkURL(WebDriver driver, String s) {
        boolean status = false;
        try {
            String uRl = driver.getCurrentUrl();
            if (uRl.contains(s.toLowerCase())) {
                System.out.println("URL contains: " + s);
                status = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception: " + e);
            System.out.println("URL not contains: " + s);
        }
        return status;
    }

    // Method to check side bar is visible or not
    // if not visible then click on the menu button to make it visible
    public static boolean menuVisible(WebDriver driver) {
        WebElement menu = driver.findElement(By.xpath("//button[@aria-label='Guide']"));
        String menuStatus = menu.getAttribute("aria-pressed");
        boolean status = false;
        if (menuStatus.equals("false")) {
            menu.click();
            status = true;
        } else if (menuStatus.equals("true")) {
            status = true;
        }
        return status;
    }

    // Method to click on the option
    public static boolean optionSelect(WebDriver driver, By locator) {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            menuVisible(driver);
            WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            option.click();
            status = true;
        } catch (Exception e) {
            System.out.println("Element not found: " + e);
        }
        return status;
    }

    // Method to get message on the screen
    public static boolean getMessage(WebDriver driver, By locator) {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            List<WebElement> lis = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            for (WebElement li : lis) {
                System.out.println("Message: " + li.getText());
            }
            status = true;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Element not found: " + e);
        }
        return status;
    }

    // Method to click on the side bar option
    public static boolean sideBarOptn(WebDriver driver, String s) {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            menuVisible(driver);
            WebElement option = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title='" + s + "']")));
            option.click();
            status = true;
        } catch (Exception e) {
            System.out.println("Element not found: " + e);
        }
        return status;
    }

    // Method to scroll the section to the extreme right
    public static boolean scrollOpt(WebDriver driver, WebElement ele) {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String s = ".//div[@id='right-arrow']/ytd-button-renderer";
        try {
            while (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(s))).isDisplayed()) {
                ele.findElement(By.xpath(s)).click();
            }
            status = true;
        } catch (Exception e) {
            System.out.println("Element not found: " + e);
        }
        return status;
    }

    // Method to get text
    public static String elementText(WebElement last, By locator) {
        String text = last.findElement(locator).getText();
        return text;
    }

    // lastEle method to find the extreme right element in the given section
    public static WebElement lastEle(WebDriver driver, WebElement ele, By locator) {

        List<WebElement> elements = ele.findElements(locator);
        WebElement lastElem = elements.get(elements.size() - 1);
        return lastElem;
    }

    // checkMovieCategory method to check if the movie belongs to the given category
    public static boolean checkMovieCategory(String movieName, String[] validCategories) {
        boolean isValidCat = false;
        try {
            for (String category : validCategories) {
                if (movieName.contains(category)) {
                    isValidCat = true;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return isValidCat;
    }

    // Find the required section in the page
    public static WebElement reqSection(WebDriver driver, String str) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement elements = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//span[contains(text(),'" + str + "')]/ancestor::div[@id='dismissible']")));
        return elements;
    }

    // Get the list of news elements in reqSection
    public static List<WebElement> listNews(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<WebElement> elements = new ArrayList<>();
        try {
            List<WebElement> ele = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            for (int i = 0; i < 3; i++) {
                elements.add(ele.get(i));
            }
        } catch (Exception e) {
            System.out.println("Element not found: " + e);
        }
        return elements;
    }

    // Method to get number from text of the element
    public static int numberElement(WebElement element, By locator) {
        int number = 0;
        try {
            String text = element.findElement(locator).getText().trim();

            if (text.isEmpty()) {
                return 0;
            }

            text = text.toUpperCase();

            if (text.endsWith("K")) {
                number = (int) (Double.parseDouble(text.replace("K", "")) * 1000);
            } else if (text.endsWith("M")) {
                number = (int) (Double.parseDouble(text.replace("M", "")) * 1000000);
            } else if (text.endsWith("B")) {
                number = (int) (Double.parseDouble(text.replace("B", "")) * 1000000000);
            } else {
                // No suffix like K/M/B → directly parse
                number = Integer.parseInt(text.replaceAll("[^0-9]", ""));
            }
        } catch (Exception e) {
            System.out.println("Element not found: " + e);
        }
        return number;
    }

    // Method to count no of likes in 1st 3 news posts
    public static int sumLikeElements(List<WebElement> elements, By locator) {
        int sum = 0;
        for (WebElement el : elements) {
            sum += numberElement(el, locator);
        }
        return sum;
    }

    // Method to get the title and body of the news posts
    public static void titleBody(WebDriver driver, List<WebElement> element) {
        String lTitle = ".//a[@id='author-text']/span";
        String lBody = ".//yt-formatted-string[@id='home-content-text']";
        try {
            int count = 1;
            for (WebElement el : element) {
                String title = el.findElement(By.xpath(lTitle)).getText();
                String body;
                // try {
                body = el.findElement(By.xpath(lBody)).getText();

                // } catch (NoSuchElementException e) {
                // body = el.findElement(By.xpath(lBody + "/..")).getText();
                // }
                System.out.println(count + " Title: " + title);
                System.out.println("Body: " + body);
                count++;
            }

        } catch (Exception e) {
            System.out.println("Element not found: " + e);
        }

    }

}
