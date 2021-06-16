Feature: Sign Up

  Scenario Outline: Register To Juice Shop
    Given Customer goes to Registration Page
    When Customer fills in random email
    And Customer fills in random password
    And Customer repeats random password
    And Customer clicks on Security Question drop-down
    And Customer selects any security question
    And Customer enters any Answer
    And Customer clicks on Register Button
    Then Customer should receive "<successMessage>"
    And User goes to Login Page
    And User enters email and password
    And Customer clicks on Account Button
    Then Customer should be logged in to Juice Shop

    Examples:
      |successMessage |
      |Registration completed successfully. You can now log in.|


