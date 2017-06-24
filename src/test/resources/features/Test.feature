@DemoFeatureFile
Feature: Test all the stepDefs

@Test
Scenario: Perfrom all the steps defined in stepdefinition file.
Given I launch app 
When I click "signInBtn" on "netflixHome" screen
#And I select "abc" element on "xyz" screen
#And I enter "abc" details in "xyz" on "klm" screen
#And I validate "abc" in "xyz" database
#And I verify "abc" element on "XYZ" screen