Feature: Add Product to Basket

  Scenario Outline: Add Product to Basket in Juice Shop
    Given Customer goes to Login Page
    When User logins as existing customer
    And Customer goes to All Products Page
    And Customer can view <productItem>
    And Customer clicks on <productItem> Add To Basket Button
    Then Customer should receive message "Placed <productItem> into basket."
    And Customer should view <productItem> in Basket
    And Customer removes <productItem> from Basket

    Examples:
      | productItem            |
      | "Apple Pomace"         |
      | "Apple Juice (1000ml)" |
      | "Banana Juice (1000ml)"|




