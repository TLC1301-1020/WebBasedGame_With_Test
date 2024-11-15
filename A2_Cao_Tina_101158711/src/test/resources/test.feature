Feature: Quest Game

  Scenario: A1_Compulsory_Quest
    Given first game is created with players hands set to values
    When player 1 declines to sponsor the quest "Q4", player2 sponsors
    And three players participate the quest
    And player2 sets the stage for "Q4"
    And quest "Q4" completed with sponsor card updated and participant shields updated
    Then sponsor player should have exact 12 cards
    And shields should be updated correctly
    And cards in each player hand should be correct


  Scenario: 2winner_game_2winner_quest
    Given second game is created with players hands set to values
    When player1 draws then sponsor "Q4"
    And participants found for Q4 quest
    And player1 builds the stage for "Q4"
    And quest "Q4" completed with player1 hand updated
    And player2 draws then player 3 sponsors "Q3"
    And 2 participants found for Q3 quest
    And player3 builds the stage for "Q3"
    And quest "Q3" completed with player3 hand updated
    Then players should have correct shields
    And two winners should be detected


  Scenario: 1winner_game_with_events
    Given third game is created with players hands set to values
    When player 1 draws then sponsor "Q4"
    And 3 participants found for Q4 quest
    And player1 builds game stage "Q4"
    And quest "Q4" completed with three winners
    And player 2 draws plague
    And player 3 draws prosperity
    And player 4 draws Queens favor
    And player 1 draws sponsor second quest "Q3"
    And 3 participants found for Q3 quest
    And player1 builds the stage "Q3"
    And quest "Q3" completed with two winners
    Then players shields should be updated correctly
    And player3 should be detected as winner


  Scenario: 0_winner_quest
    Given fourth game is created with players hand set to values
    When player 1 draws and sponsor "Q2"
    And players chose to participate
    And player 1 builds the stages for quest "Q2"
    And players failed to complete the quest "Q2"
    Then players hands should be correctly updated
    And all participants have not gained any shields

