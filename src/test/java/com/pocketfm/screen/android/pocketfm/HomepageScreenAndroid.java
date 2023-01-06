package com.pocketfm.screen.android.pocketfm;

import com.pocketfm.screen.pocketfm.HomepageScreen;
import com.pocketfm.util.CommonUtil;
import com.znsio.e2e.tools.Driver;
import com.znsio.e2e.tools.Visual;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.znsio.e2e.tools.Wait.waitFor;

public class HomepageScreenAndroid extends HomepageScreen {
    private final Driver driver;
    private final Visual visually;
    private static final String SCREEN_NAME = LoginScreenAndroid.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);
    private static final String NOT_YET_IMPLEMENTED = " not yet implemented";
    private static By byShowPlayingBarId = By.id("com.radio.pocketfm:id/audio_progress_control");
    private static By byLanguageSelectionBtnId = By.id("com.radio.pocketfm:id/language_selection_button");
    private static By byEnglishLanguageXpath = By.xpath("//*[@text='" + "English" + "']");
    private static By byHindiLanguageXpath = By.xpath("//*[@text='हिंदी']");
    private static By bySaveLanguageBtnId = By.id("com.radio.pocketfm:id/save");
    private static By byPlayerMinimizeBtnId = By.id("com.radio.pocketfm:id/close_expanded_panel");
    private static By byLanguageSelectionPopUpXpath = By.xpath("//*[@text='Languages']");
    private static By byMyLibraryTabId = By.id("com.radio.pocketfm:id/navigation_listening");
    private static By byMyStoreTabId = By.id("com.radio.pocketfm:id/navigation_store");
    private static By byHomeTabId = By.id("com.radio.pocketfm:id/navigation_home");
    private static By byAudioBookTabId = By.id("com.radio.pocketfm:id/navigation_learn");
    private static By byNovelsTabId = By.id("com.radio.pocketfm:id/navigation_novels");
    private static By byHeaderTabsXpath = By.xpath("//*[@resource-id='com.radio.pocketfm:id/tablayout']//androidx.appcompat.app.ActionBar.Tab");
    private static By byMyLibraryTitleId = By.id("com.radio.pocketfm:id/my_library_title_text");
    private static By byMyStoreTitleId = By.id("com.radio.pocketfm:id/tv_header");


    public HomepageScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
        visually.checkWindow(SCREEN_NAME, "Login page");
    }

    @Override
    public boolean isShowGettingPlayed() {
        driver.waitForClickabilityOf(byShowPlayingBarId);
        return new CommonUtil().isVisible(driver, byShowPlayingBarId);
    }

    @Override
    public HomepageScreen clickOnDownArrow() {
        waitFor(2); // hard wait helps in DOM refresh over here for faster execution of code
        driver.waitForClickabilityOf(byPlayerMinimizeBtnId).click();
        return this;
    }

    @Override
    public HomepageScreen clickOnLanguageSelectionButton() {
        driver.waitForClickabilityOf(byLanguageSelectionBtnId).click();
        return this;
    }

    @Override
    public HomepageScreen selectHindiLanguage() {
        driver.waitForClickabilityOf(byEnglishLanguageXpath).click();
        driver.waitForClickabilityOf(byHindiLanguageXpath).click();
        return this;
    }

    @Override
    public HomepageScreen clickOnSaveButton() {
        driver.waitForClickabilityOf(bySaveLanguageBtnId).click();
        return this;
    }

    @Override
    public boolean isLanguagePopVisible() {
        return new CommonUtil().isVisible(driver, byLanguageSelectionPopUpXpath);
    }

    @Override
    public boolean validateHeaderTabs(List<String> headerTabsList) {
        boolean fl = true;
        driver.waitForClickabilityOf(byHeaderTabsXpath, 20);
        List<WebElement> headerTabListWE = driver.findElements(byHeaderTabsXpath);
        for (WebElement headerTab : headerTabListWE) {
            if (!headerTabsList.contains(headerTab.getAttribute("content-desc").trim())) {
                fl = false;
                LOGGER.info("header tab which doesn't exists: " + headerTab.getAttribute("content-desc").trim());
            }
            LOGGER.info("header tab which exists: " + headerTab.getAttribute("content-desc").trim());
        }
        return fl;
    }

    @Override
    public String getMyLibraryTitleText() {
        return driver.waitForClickabilityOf(byMyLibraryTitleId).getText().trim();
    }

    @Override
    public boolean isHomeOptionVisibleInBottomTabs() {
        return new CommonUtil().isVisible(driver, byHomeTabId);
    }

    @Override
    public HomepageScreen clickOnAudiobooks() {
        driver.waitForClickabilityOf(byAudioBookTabId).click();
        return this;
    }

    @Override
    public boolean isAudiobookOptionVisibleInBottomTabs() {
        return new CommonUtil().isVisible(driver, byAudioBookTabId);
    }

    @Override
    public HomepageScreen clickOnNovels() {
        driver.waitForClickabilityOf(byNovelsTabId).click();
        return this;
    }

    @Override
    public boolean isNovelsOptionVisibleInBottomTabs() {
        return new CommonUtil().isVisible(driver, byNovelsTabId);
    }

    @Override
    public HomepageScreen clickOnMyLibrary() {
        driver.waitForClickabilityOf(byMyLibraryTabId).click();
        return this;
    }

    @Override
    public HomepageScreen clickOnMyStore() {
        driver.waitForClickabilityOf(byMyStoreTabId).click();
        return this;
    }

    @Override
    public String getMyStoreTitleText() {
        return driver.waitForClickabilityOf(byMyStoreTitleId).getText().trim();
    }

}
