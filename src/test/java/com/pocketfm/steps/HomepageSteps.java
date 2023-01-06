package com.pocketfm.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.pocketfm.businessLayer.HomepageBL;
import com.pocketfm.entities.POCKETFM_TEST_CONTEXT;
import com.znsio.e2e.tools.Drivers;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;

public class HomepageSteps {
    private static final Logger LOGGER = Logger.getLogger(HomepageSteps.class.getName());
    private final TestExecutionContext context;
    private final Drivers allDrivers;

    public HomepageSteps() {
        context = SessionContext.getTestExecutionContext(Thread.currentThread()
                .getId());
        LOGGER.info("context: " + context.getTestName());
        allDrivers = (Drivers) context.getTestState(POCKETFM_TEST_CONTEXT.ALL_DRIVERS);
        LOGGER.info("allDrivers: " + (null == allDrivers));
    }

    @Then("user should see the audio playing")
    public void userShouldSeeTheAudioPlaying() {
        new HomepageBL().checkIfPlayerOpensPlayingAShow();
    }

    @When("user selects language as {string}")
    public void userSelectsLanguageAs(String language) {
        new HomepageBL().selectLanguageFromLanguageList(language);
    }

    @Then("user sees header and bottom tabs")
    public void userSeesHeaderAndBottomTabs() {
        new HomepageBL().validateHeaderAndBottomTabs();
    }
}
