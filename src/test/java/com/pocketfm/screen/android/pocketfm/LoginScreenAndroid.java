package com.pocketfm.screen.android.pocketfm;

import com.pocketfm.screen.pocketfm.LoginScreen;
import com.pocketfm.util.CommonUtil;
import com.znsio.e2e.runner.Runner;
import com.znsio.e2e.tools.Driver;
import com.znsio.e2e.tools.Visual;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class LoginScreenAndroid
        extends LoginScreen {
    private final Driver driver;
    private final Visual visually;
    private static final String SCREEN_NAME = LoginScreenAndroid.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);
    private static final String NOT_YET_IMPLEMENTED = " not yet implemented";
    private static By byClickHereXpath = By.id("com.radio.pocketfm:id/textview_not_user_click");
    private static By byContinueWithMobileXpath = By.xpath("//*[@resource-id='com.radio.pocketfm:id/btn_phone_signUp']");
    private static By byMobileNumberInputXpath = By.xpath("//*[@resource-id='com.radio.pocketfm:id/mobile_number_edt']");
    private static By byContinueButtonXpath = By.xpath("//*[@resource-id='com.radio.pocketfm:id/confirm_phone_number']");
    private static By byOtpViewId = By.id("com.radio.pocketfm:id/otp_view");
    private static By bySelectThreeShowId = By.id("com.radio.pocketfm:id/show_like_rv");
    private static By byShowListId = By.id("com.radio.pocketfm:id/show_image");
    private static By byPlayNowButtonId = By.id("com.radio.pocketfm:id/continue_btn");
    private static By byShowPlayerId = By.id("com.radio.pocketfm:id/player_swag");

    public LoginScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
        visually.checkWindow(SCREEN_NAME, "Login page");
    }

    @Override
    public LoginScreen login() {
        if (new CommonUtil().isVisible(driver, byClickHereXpath)) {
            driver.waitForClickabilityOf(byClickHereXpath).click();
        }
        driver.waitForClickabilityOf(byContinueWithMobileXpath).click();
        driver.waitForClickabilityOf(byMobileNumberInputXpath).sendKeys(Runner.getTestData("mobileNumber"));
        driver.waitForClickabilityOf(byContinueButtonXpath).click();
        driver.waitForClickabilityOf(byOtpViewId).sendKeys(Runner.getTestData("otp"));
        return this;
    }

    @Override
    public boolean isSelectThreeShowsVisible() {
        driver.waitForClickabilityOf(bySelectThreeShowId);
        return new CommonUtil().isVisible(driver, bySelectThreeShowId);
    }

    @Override
    public LoginScreen selectShows(int numberOfShows) {
        List<WebElement> showLists = driver.findElements(byShowListId);
        int showsSelected = 0;
        for (WebElement show : showLists) {
            if (showsSelected < numberOfShows) {
                show.click();
                showsSelected++;
            }
        }
        return this;
    }

    @Override
    public LoginScreen clickOnPlayNow() {
        driver.waitForClickabilityOf(byPlayNowButtonId).click();
        return this;
    }

    @Override
    public boolean isShowPlayingScreenVisible() {
        driver.waitForClickabilityOf(byShowPlayerId);
        return new CommonUtil().isVisible(driver, byShowPlayerId);
    }

}
