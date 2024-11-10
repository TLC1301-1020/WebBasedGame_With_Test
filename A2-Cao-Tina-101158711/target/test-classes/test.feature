Feature: Game

  Scenario: player 1 declines to sponsor, player2 sponsors, and player 1,3,4 participate in the quest.

    Given the game is created with players hands set to values
    When player 1 declines to sponsor the quest "Q4", second player sponsors
    And three players participate the quest
    And sponsor sets the stage for "Q4"
    And quest "Q4" completed with sponsor card updated and participant shields updated
    Then sponsor player should have exact cards
    And shields should be updated correctly
    And cards in each player hand should be correct



