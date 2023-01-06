package com.pocketfm.businessLayer;

import com.context.TestExecutionContext;
import com.znsio.e2e.entities.Platform;
import com.znsio.e2e.runner.Runner;
import com.pocketfm.entities.POCKETFM_TEST_CONTEXT;
import com.pocketfm.screen.pocketfm.LoginScreen;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

public class LoginBL {
    private static final Logger LOGGER = Logger.getLogger(LoginBL.class.getName());
    private final TestExecutionContext context;
    private final SoftAssertions softly;
    private final String currentUserPersona;
    private final Platform currentPlatform;

    public LoginBL(String userPersona, Platform forPlatform) {
        long threadId = Thread.currentThread()
                .getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = userPersona;
        this.currentPlatform = forPlatform;
        Runner.setCurrentDriverForUser(userPersona, forPlatform, context);
    }

    public LoginBL() {
        long threadId = Thread.currentThread()
                .getId();
        this.context = Runner.getTestExecutionContext(threadId);
        softly = Runner.getSoftAssertion(threadId);
        this.currentUserPersona = POCKETFM_TEST_CONTEXT.ME;
        this.currentPlatform = Runner.platform;
    }

    public LoginBL login() {
        LoginScreen loginScreen = LoginScreen.get();
        loginScreen.login();
        softly.assertThat(loginScreen.isSelectThreeShowsVisible()).as("Select Three Shows screen is not visible").isTrue();
        return this;
    }

    public LoginBL selectShowsOnFeed(int numberOfShows) {
        LoginScreen loginScreen = LoginScreen.get();
        loginScreen.selectShows(numberOfShows);
        loginScreen.clickOnPlayNow();
        softly.assertThat(loginScreen.isShowPlayingScreenVisible()).as("Show player is not visible after login.").isTrue();
        return this;
    }
}