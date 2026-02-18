Feature: Purchase the order from Ecommerce Website
  I want to use this template for my website

  Background:
    Given I landed on Ecommerce Page

  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username "<username>" and "<password>"
    When I add product "<productName>" to cart
    And Checkout "<productName>" and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage


    Examples:
      | username                | password   | productName     |
      | johnlucca@mailinator.com | !Senha1234 | ADIDAS ORIGINAL |

