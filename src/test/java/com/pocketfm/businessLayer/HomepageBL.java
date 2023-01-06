package com.pocketfm.businessLayer;

import com.context.TestExecutionContext;
import com.pocketfm.entities.POCKETFM_TEST_CONTEXT;
import com.pocketfm.screen.pocketfm.HomepageScreen;
import com.znsio.e2e.entities.Platform;
import com.znsio.e2e.runner.Runner;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

import java.util.ArrayList;
import java.util.List;

public class HomepageBL {
    private static final Logger LOGGER = Logger.getLogger(HomepageBL.class.getName());
    private final TestExecutionContext context;
    private final SoftAssertions softly;
    private final String currentUserPersona;
    private final Platform currentPlatform;

    public HomepageBL(String userPersona, Platform forPlatform) {
        long threadId = Thread.currentThread()
                .getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = userPersona;
        this.currentPlatform = forPlatform;
        Runner.setCurrentDriverForUser(userPersona, forPlatform, context);
    }

    public HomepageBL() {
        long threadId = Thread.currentThread()
                .getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = POCKETFM_TEST_CONTEXT.ME;
        this.currentPlatform = Runner.platform;
    }

    public HomepageBL checkIfPlayerOpensPlayingAShow() {
        softly.assertThat(HomepageScreen.get().isShowGettingPlayed()).as("Show is not playing in player").isTrue();
        return this;
    }

    public HomepageBL selectLanguageFromLanguageList(String language) {
        HomepageScreen homepageScreen = HomepageScreen.get();
        homepageScreen.clickOnDownArrow().clickOnLanguageSelectionButton().selectHindiLanguage().clickOnSaveButton();
        softly.assertThat(homepageScreen.isLanguagePopVisible()).as("Language selection pop up is not closed").isFalse();
        return this;
    }

    public HomepageBL validateHeaderAndBottomTabs() {
        HomepageScreen homepageScreen = HomepageScreen.get();
        List<String> headerTabsList = new ArrayList<String>() {{
            add("For You");
            add("Premium");
            add("Stories");
            add("Podcasts");
            add("VIP");
        }};
        softly.assertThat(homepageScreen.validateHeaderTabs(headerTabsList)).as("Header tab does not exists in expected list").isTrue();
        softly.assertThat(homepageScreen.isHomeOptionVisibleInBottomTabs()).as("Home option is not visible in bottom tabs").isTrue();
        homepageScreen.clickOnAudiobooks();
        softly.assertThat(homepageScreen.isAudiobookOptionVisibleInBottomTabs()).as("Audiobooks option is not visible in bottom tabs").isTrue();
        homepageScreen.clickOnNovels();
        softly.assertThat(homepageScreen.isNovelsOptionVisibleInBottomTabs()).as("Novels option is not visible in bottom tabs").isTrue();
        homepageScreen.clickOnMyLibrary();
        softly.assertThat(homepageScreen.getMyLibraryTitleText()).as("My Library title is not same as expected").isEqualToIgnoringCase("My Library");
        homepageScreen.clickOnMyStore();
        softly.assertThat(homepageScreen.getMyStoreTitleText()).as("My Store title is not same as expected").isEqualToIgnoringCase("My Store");
        return this;
    }
}
