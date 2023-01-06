package com.pocketfm.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.pocketfm.businessLayer.LoginBL;
import com.pocketfm.entities.POCKETFM_TEST_CONTEXT;
import com.znsio.e2e.runner.Runner;
import com.znsio.e2e.tools.Drivers;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;

public class LoginSteps {
    private static final Logger LOGGER = Logger.getLogger(LoginSteps.class.getName());
    private final TestExecutionContext context;
    private final Drivers allDrivers;

    public LoginSteps() {
        context = SessionContext.getTestExecutionContext(Thread.currentThread()
                .getId());
        LOGGER.info("context: " + context.getTestName());
        allDrivers = (Drivers) context.getTestState(POCKETFM_TEST_CONTEXT.ALL_DRIVERS);
        LOGGER.info("allDrivers: " + (null == allDrivers));
    }

    @Given("user logs in to pocketfm")
    public void userLogsInToPocketfmAndLandsHomepage() {
        LOGGER.info(System.out.printf("iAGuestUserSearchForProducts - Persona:'%s', Platform: '%s'", POCKETFM_TEST_CONTEXT.ME, Runner.platform));
        allDrivers.createDriverFor(POCKETFM_TEST_CONTEXT.REGISTERED_USER, Runner.platform, context);
        new LoginBL(POCKETFM_TEST_CONTEXT.REGISTERED_USER, Runner.platform).login();
    }

    @When("user selects {int} shows of his choice")
    public void userSelectsShowsOfHisChoice(int numberOfShows) {
        new LoginBL().selectShowsOnFeed(numberOfShows);
    }

    @Given("User logs in and reach homepage")
    public void userLogsInAndReachHomepage() {
        LOGGER.info(System.out.printf("iAGuestUserSearchForProducts - Persona:'%s', Platform: '%s'", POCKETFM_TEST_CONTEXT.ME, Runner.platform));
        allDrivers.createDriverFor(POCKETFM_TEST_CONTEXT.REGISTERED_USER, Runner.platform, context);
        new LoginBL(POCKETFM_TEST_CONTEXT.REGISTERED_USER, Runner.platform).login();
        new LoginBL().selectShowsOnFeed(3);
    }
}
