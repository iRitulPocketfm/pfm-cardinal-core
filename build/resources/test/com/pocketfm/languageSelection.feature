@pocketfm @prod
Feature: Language Selection scenarios

  @android @languageSelection
  Scenario: User logs to app, reach homepage and select different languages
    Given User logs in and reach homepage
    When user selects language as "Hindi"
    Then user sees header and bottom tabs