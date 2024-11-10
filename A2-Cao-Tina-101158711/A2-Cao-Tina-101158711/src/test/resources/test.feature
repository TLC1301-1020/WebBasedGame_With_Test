Feature: Quest Game

  Scenario: player 1 declines to sponsor, player2 sponsors, and player 1,3,4 participate in the quest.

    Given first game is created with players hands set to values
    When player 1 declines to sponsor the quest "Q4", player2 sponsors
    And three players participate the quest
    And player2 sets the stage for "Q4"
    And quest "Q4" completed with sponsor card updated and participant shields updated
    Then sponsor player should have exact 12 cards
    And shields should be updated correctly
    And cards in each player hand should be correct


 Scenario: Two winners from the quests
    Given second game is created with players hands set to values
    When player 1 draws then sponsor "Q4"
    And participants found for Q4 quest
    And sponsor builds the stage for "Q4"
    And quest "Q4" completed with player1 hand updated
    And player2 draws then player 3 sponsors "Q3"
    And participants found for Q3 quest
    And player3 builds the stage for "Q3"
    And quest "Q3" completed with player3 hand updated
    Then players should have correct shields
    And two winners should be detected
