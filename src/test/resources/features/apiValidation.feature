@application_apiTests
Feature: validate api response

@Validate_api_responseCode_getService
Scenario: User get the success response code for available service
  When I send GET request to the service
  Then I should get success status code for the service
  
#@Validate_api_responseCode_postService
#Scenario: User get the success response code for post service
#  When I send POST request to the service+
#  Then I should get success status code for the post service
  
#@Validate_totalBToffers
#Scenario Outline: User want to validate total BT offers available
 # When I call getFTOffer API with valid "<cardHolder_id>" and "<accountnumber_id>" to validate "<BT_Offers>"
  #And I get response "<Response_File_name>" to compare
  #Then I should be able to validate the response with BT offers available
   # Examples:
    #|cardHolder_id|accountnumber_id|BT_Offers|Response_File_name|
    #|123456789    |  4929651064624 |   2     |AccountResponse   |