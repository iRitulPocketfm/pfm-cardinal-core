package com.pocketfm.screen.pocketfm;

import com.pocketfm.screen.android.pocketfm.HomepageScreenAndroid;
import com.znsio.e2e.entities.Platform;
import com.znsio.e2e.runner.Runner;
import com.znsio.e2e.tools.Driver;
import com.znsio.e2e.tools.Visual;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;

import static com.znsio.e2e.runner.Runner.fetchDriver;
import static com.znsio.e2e.runner.Runner.fetchEyes;

public abstract class HomepageScreen {
    private static final String SCREEN_NAME = HomepageScreen.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);

    public static HomepageScreen get() {
        Driver driver = fetchDriver(Thread.currentThread()
                .getId());
        Platform platform = Runner.fetchPlatform(Thread.currentThread()
                .getId());
        LOGGER.info(SCREEN_NAME + ": Driver type: " + driver.getType() + ": Platform: " + platform);
        Visual visually = fetchEyes(Thread.currentThread()
                .getId());

        switch (platform) {
            case android:
                return new HomepageScreenAndroid(driver, visually);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented in " + Runner.platform);
    }

    public abstract boolean isShowGettingPlayed();

    public abstract HomepageScreen clickOnDownArrow();

    public abstract HomepageScreen clickOnLanguageSelectionButton();

    public abstract HomepageScreen selectHindiLanguage();

    public abstract HomepageScreen clickOnSaveButton();

    public abstract boolean isLanguagePopVisible();

    public abstract boolean validateHeaderTabs(List<String> headerTabsList);

    public abstract String getMyLibraryTitleText();

    public abstract boolean isHomeOptionVisibleInBottomTabs();

    public abstract HomepageScreen clickOnAudiobooks();

    public abstract boolean isAudiobookOptionVisibleInBottomTabs();

    public abstract HomepageScreen clickOnNovels();

    public abstract boolean isNovelsOptionVisibleInBottomTabs();

    public abstract HomepageScreen clickOnMyLibrary();

    public abstract HomepageScreen clickOnMyStore();

    public abstract String getMyStoreTitleText();
}
