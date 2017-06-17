@DemoFeatureFile
Feature: Test all the stepDefs

Scenario: Perfrom all the steps defined in stepdefinition file.
Given I launch app 
When I click "abc" on "xyz" screen
And I scroll "abc" element on "xyz" screen
And I enter "abc" details in "xyz" on "klm" screen
Then I verify elements on "xyz" screen