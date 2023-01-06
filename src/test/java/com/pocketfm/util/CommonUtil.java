package com.pocketfm.util;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.znsio.e2e.tools.Driver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static com.google.common.collect.Range.atLeast;
import static com.znsio.e2e.tools.Wait.waitFor;

public class CommonUtil {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CommonUtil.class.getName());

    public boolean scrollTillElementPresentWitMaxScrollLimit(By element, Driver driver, int maxLimit) {
        String previousPageSource = driver.getInnerDriver().getPageSource();
        boolean isElementFound = true;
        int maxScrollCount = 0;
        while (!driver.isElementPresent(element) && maxScrollCount < maxLimit) {
            AndroidDriver driver1 = (AndroidDriver) driver.getInnerDriver();
            if (driver1.isKeyboardShown()) {
                driver.hideKeyboard();
            }
            driver.scrollDownByScreenSize();
            if (!previousPageSource.equals(driver.getInnerDriver().getPageSource())) {
                previousPageSource = driver.getInnerDriver().getPageSource();
            } else {
                return false; // Will return false if element is not found till end of page
            }
            LOGGER.info("Number of Scrolls: " + maxScrollCount++);
        }
        return isElementFound;
    }

    public boolean scrollTillElementPresentForInnerScroll(By element, Driver driver, By byScrollerId) {
        String previousPageSource = driver.getInnerDriver().getPageSource();
        boolean isElementFound = true;
        while (!driver.isElementPresent(element)) {
            scrollDownByScreenSize(driver, byScrollerId);
            if (!previousPageSource.equals(driver.getInnerDriver().getPageSource())) {
                previousPageSource = driver.getInnerDriver().getPageSource();
            } else {
                return false; // Will return false if element is not found till end of page
            }
        }
        return isElementFound;
    }

    public boolean swipeLeftTillElementPresent(By element, Driver driver) {
        AppiumDriver appiumDriver = (AppiumDriver) driver.getInnerDriver();
        String previousPageSource = driver.getInnerDriver().getPageSource();
        boolean isElementFound = true;
        Dimension windowSize = appiumDriver.manage().window().getSize();
        int height = windowSize.height / 2;
        int fromWidth = (int) (windowSize.width * 0.9);
        int toWidth = (int) (windowSize.width * 0.2);
        while (!driver.isElementPresent(element)) {
            moveToLocation(appiumDriver, height, height, fromWidth, toWidth);
            if (!previousPageSource.equals(driver.getInnerDriver().getPageSource())) {
                previousPageSource = driver.getInnerDriver().getPageSource();
            } else {
                return false; // Will return false if element is not found till end of page
            }
        }
        return isElementFound;
    }

    private void scrollDownByScreenSize(Driver driver, By byScrollerId) {
        AppiumDriver appiumDriver = (AppiumDriver) driver.getInnerDriver();
        Dimension windowSize = driver.waitTillElementIsPresent(byScrollerId).getSize();
        LOGGER.info("dimension: " + windowSize.toString());
        int width = windowSize.width / 2;
        int fromHeight = (int) (windowSize.height * 0.9);
        int toHeight = (int) (windowSize.height * 0.5);
        LOGGER.info(String.format("width: %s, from height: %s, to height: %s", width, fromHeight, toHeight));

        moveToLocation(appiumDriver, fromHeight, toHeight, width, width);
    }

    public void scrollDown(Driver driver) {
        Dimension dimension = driver.getInnerDriver().manage().window().getSize();
        int scrollStart = (int) (dimension.getHeight() * 0.5);
        int scrollEnd = (int) (dimension.getHeight() * 0.2);

        new TouchAction((PerformsTouchActions) driver.getInnerDriver())
                .press(PointOption.point(0, scrollStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(0, scrollEnd))
                .release().perform();
    }

    public void scrollTillMaxLimitOrElementVisible(By elementId, Driver driver, int maxScroll) {
        int count = 0;
        while (count < maxScroll) {
            if (isVisible(driver, elementId)) {
                waitFor(1); // wait for screen to be stable
                break;
            } else {
                scrollDown(driver);
                LOGGER.info("Number of Scrolls: " + count++);
            }
        }
    }

    public boolean waitTillElementIsDisappeared(WebDriver driver, int numberOfSecondsToWait, By elementId) {
        return (new WebDriverWait(driver, numberOfSecondsToWait)).until(ExpectedConditions.invisibilityOfElementLocated(elementId));
    }

    public boolean waitTillTextOfElementBe(WebDriver driver, int numberOfSecondsToWait, By locator, String text) {
        return new WebDriverWait(driver, numberOfSecondsToWait).until(ExpectedConditions.textToBe(locator, text));
    }

    public String getElementAttributeValue(Driver driver, By elementId, String attribute) {
        return driver.waitTillElementIsPresent(elementId).getAttribute(attribute.toLowerCase());
    }

    public boolean isVisible(Driver driver, By elementId) {
        try {
            return driver.findElement(elementId).isDisplayed();
        } catch (Exception e) {
            LOGGER.info("The element expected is not found and thrown exception therefore it is not displayed and thus returned false. Exceptiom : "+ e);
            return false;
        }
    }

    public void openUrlInNewTabAndCloseCurrentTab(String currentUrl, Driver driver) {
        String link = "window.open('%s','_blank')";
        ((JavascriptExecutor) driver.getInnerDriver()).executeScript(String.format(link, currentUrl));
        waitFor(5);//required for new tab to open with the link
        ArrayList<String> tabs = new ArrayList<String>(driver.getInnerDriver().getWindowHandles());
        driver.getInnerDriver().switchTo().window(tabs.get(0)).close();
        driver.getInnerDriver().switchTo().window(tabs.get(1));
    }

    public String getCurrentUrl(Driver driver) {
        return driver.getInnerDriver().getCurrentUrl();
    }

    public void clickOnBackButton(Driver driver) {
        driver.getInnerDriver().navigate().back();
    }

    public void scrollTillElementIntoMiddle(By elementId, Driver driver) {
        WebElement element = driver.findElement(elementId);
        ((JavascriptExecutor) driver.getInnerDriver()).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'})", element);
    }

    public void scrollUpPage(Driver driver) {
        Actions at = new Actions(driver.getInnerDriver());
        at.sendKeys(Keys.PAGE_UP).build().perform();
        waitFor(2);
    }

    public By getElementXpathByScreenSize(Driver driver, String xpath) {
        int width = driver.getInnerDriver().manage().window().getSize().getWidth();
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(0, 719), "sm:block");
        rangeMap.put(atLeast(720), "md:block");
        return By.xpath(String.format(xpath, rangeMap.get(width)));
    }

    private void moveToLocation(AppiumDriver appiumDriver, int fromHeight, int toHeight, int fromWidth, int toWidth) {
        TouchAction touchAction = new TouchAction(appiumDriver);
        touchAction.press(PointOption.point(new Point(fromWidth, fromHeight)))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(new Point(toWidth, toHeight)))
                .release()
                .perform();
    }
}