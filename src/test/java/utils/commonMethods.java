package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class commonMethods extends PageInitializer {
    public static WebDriver driver;

    /**
     * this method opens a browser, maximizes window and sets implicit wait
     */

    @BeforeMethod(alwaysRun = true)
    public static void openBrowser() {


        configReader.readProperties(Constants.CONFIGURATION_FILEPATH);

        switch (configReader.getPropertyValue("browser")) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Invalid browser name");
        }

        driver.get(configReader.getPropertyValue("url"));
        PageInitializer.initializePageObjects();
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    /**
     * this method closes browser
     */
    @AfterMethod(alwaysRun = true)
    public static void closeBrowser() {
        driver.quit();
    }

    /**
     * this method returns date in String with specific pattern
     *
     * @param pattern - YYYY-MM-DD-HH-MM-SS-MS
     * @return - data in String
     */
    public static String getTimeStamp(String pattern) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static boolean displayed(WebElement element){
//       add some wait for the element to appear
        return  element.isDisplayed();
    }

    /**
     * this method takes screenshot and save file in Screenshot folder
     *
     * @param filename - name for screenshot file
     * @return - screenshot in array of bytes for report
     */
    public static byte[] takeScreenshot(String filename) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        byte[] picBytes = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(sourceFile, new File(
                    Constants.SCREENSHOT_FILEPATH + filename + " " + getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picBytes;
    }

    /**
     * this is method gets time of explicit wait
     *
     * @return WebDriverWait
     */
    public static WebDriverWait getWait() {
        WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        return wait;
    }

    /**
     * this method wait until element will be clickable
     *
     * @param element - webElement which we need to click
     */
    public static void waitForClickability(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * this method makes click on webElement
     *
     * @param element - webElement which we need to click
     */
    public static void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    /**
     * this method makes click on webElement
     *
     * @param element webElement which we need to click using JS click
     */
//    public static void jsClick(WebElement element) {
//        getJSExecutor().executeScript("arguments[0].click();", element);
//    }

    /**
     * Method checks if radio/checkbox is enabled and clicks it
     *
     * @param radioOrcheckbox
     * @param value
     */
    public static void clickRadioOrCheckbox(List<WebElement> radioOrcheckbox, String value) {

        String actualValue;

        for (WebElement el : radioOrcheckbox) {
            actualValue = el.getAttribute("value").trim();
            if (!el.isSelected() && el.isEnabled() && actualValue.equals(value)) {
                el.click();
                break;
            }
        }
    }

    /**
     * this method send text to webElement with text box
     *
     * @param element - webElement
     * @param text    - text which we need to send
     */
    public static void sendText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /**
     * this method send text to webElement with text box and hits enter
     *
     * @param element
     * @param text
     * @param key
     */
    public static void sendText(WebElement element, String text, Keys key) {
        element.clear();
        element.sendKeys(text, key);
    }

    /**
     * Method that checks if text is there and then selects it
     *
     * @param element
     * @param textToSelect
     */
    public static void selectDdValue(WebElement element, String textToSelect) {

        try {
            Select select = new Select(element);
            List<WebElement> options = select.getOptions();

            for (WebElement el : options) {
                if (el.getText().equals(textToSelect)) {
                    select.selectByVisibleText(textToSelect);
                    break;
                }
            }
        } catch (UnexpectedTagNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that selects value by index
     *
     * @param element
     * @param index
     */
    public static void selectDdValue(WebElement element, int index) {

        try {
            Select select = new Select(element);
            int size = select.getOptions().size();

            if (size > index) {
                select.selectByIndex(index);
            }
        } catch (UnexpectedTagNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method checks do we have specific text in DropDownBox
     *
     * @param element - webElement of DropDownBox
     * @param text    - specific Text
     * @return - boolean
     */
    public static boolean isTextEnableInDropDown(WebElement element, String text) {
        boolean isTextEnable = false;
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        for (WebElement op : options) {
            if (op.getText().equals(text)) {
                isTextEnable = true;
            }
        }
        return isTextEnable;
    }

    /**
     * Methods that accept alerts and catches exception if alert is not present
     */
    public static void acceptAlert() {

        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();

        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methods that dismiss alerts and catches exception if alert is not present
     */
    public static void dismissAlert() {

        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();

        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methods that gets text of alert and catches exception if alert is not present
     *
     * @return String alert text
     */
    public static String getAlertText() {

        String alertText = null;

        try {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }

        return alertText;
    }

    /**
     * Methods that sends text to alert and catches exception if alert is not
     * present
     *
     */
    public static void sendAlertText(String text) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    public static void switchToFrame(String nameOrId) {

        try {
            driver.switchTo().frame(nameOrId);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }

    public static void switchToFrame(WebElement element) {

        try {
            driver.switchTo().frame(element);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }

    public static void switchToFrame(int index) {

        try {
            driver.switchTo().frame(index);
        } catch (NoSuchFrameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method switches focus to child window
     */
    public static void switchToChildWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public static JavascriptExecutor getJSExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public static void jsScroll() {
        getJSExecutor().executeScript("window.scrollBy(0,200)");
    }

    public static void moveCursor(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public static String getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1970, 2019);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        String Day   = gc.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + gc.get(Calendar.DAY_OF_MONTH) : "" + gc.get(Calendar.DAY_OF_MONTH);
        String Month = (gc.get(Calendar.MONTH) + 1) < 10 ? "0" + (gc.get(Calendar.MONTH) + 1) : "" + (gc.get(Calendar.MONTH) + 1);
        return gc.get(Calendar.YEAR) + "-" + Month + "-" + Day;
    }

}
