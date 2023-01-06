@pocketfm @prod
Feature: Login scenarios

  @android @login
  Scenario: User logs to app and reach homepage
    Given user logs in to pocketfm
    When user selects 3 shows of his choice
    Then user should see the audio playing