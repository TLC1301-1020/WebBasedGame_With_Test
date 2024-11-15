package org.example;

import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OneWinnerScenarioSteps {
    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @Given("third game is created with players hands set to values")
    public void third_game_is_created_with_players_hands_set() {
        mockDeck = mock(Deck.class);
        mockScanner = mock(Scanner.class);
        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };

        List<String> hand1 = Arrays.asList("F5", "F5", "F10", "F10", "F20", "F70", "D5", "H10", "S10", "H10", "S10", "L20");
        List<String> hand2 = Arrays.asList("F5", "F5", "F10", "D5", "D5", "S10", "H10", "H10", "S10", "L20", "L20", "E30");
        List<String> hand3 = Arrays.asList("F10", "F20", "F70", "D5", "D5", "S10", "H10", "S10", "H10", "B15", "E30", "E30");
        List<String> hand4 = Arrays.asList("F5", "F10", "F10", "F10", "F70", "D5", "D5", "S10", "L20", "L20", "E30", "E30");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }

        game.getPlayers().get(0).addCards(hand1);
        game.getPlayers().get(1).addCards(hand2);
        game.getPlayers().get(2).addCards(hand3);
        game.getPlayers().get(3).addCards(hand4);

        when(mockDeck.drawAdventureCard())
                //first quest
                .thenReturn("E30","D5","B15") //s1

                .thenReturn("D5","F5","D5") // s2

                .thenReturn("B15","E30","S10") // s3

                .thenReturn("B15","S10","H10") // s4

                .thenReturn("F10", "F10", "F10", "F10", "F10", "F10", "F10", "F10", "F10") //p1 draws

                //prosperity
                .thenReturn("F10", "F15") //P1
                .thenReturn("E30", "S10") //P2
                .thenReturn("D5", "F10") //P3
                .thenReturn("F10", "B15") //P4

                //Queens favor
                .thenReturn("F10", "F20")

                //second quest
                .thenReturn("D5") //P2S1
                .thenReturn("H10")//P3S1
                .thenReturn("E30")//P4S1

                .thenReturn("F20") //P2S2
                .thenReturn("F10") //P3S2

                .thenReturn("D5") //P2S3
                .thenReturn("F5") //P3S3

                .thenReturn("F10","F10","F10","F10","F10","F10","F10","F10"); //p1 draws after second quest

        menu = new Menu(game);
        mockScanner = mock(Scanner.class);
        menu.setScanner(mockScanner);
        menu.updateRound();
    }

    @When("player 1 draws then sponsor {string}")
    public void player1_draws_sponsor_quest_Q4(String quest){
        when(mockScanner.nextInt()).thenReturn(1);
        menu.findingSponsor(quest);
    }

    @When("3 participants found for Q4 quest")
    public void three_participants_found_for_the_quest_Q4(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds game stage {string}")
    public void player1_builds_game_stage(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        when(mockScanner.nextLine())
                .thenReturn("F5","quit")        //stage 1
                .thenReturn("F10","quit")       //stage 2
                .thenReturn("F5","H10","quit")  //stage 3
                .thenReturn("F20","quit");      //stage 4
        menu.buildQuest(quest,sponsor,participants);

    }

    @When("quest {string} completed with three winners")
    public void quest_Q4_completed_with_three_winners(String event){

        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("F5","no","D5","quit","\n")   //dis

                //P3 stage1
                .thenReturn("F70","no","B15","quit","\n")   //dis

                //P4 stage1
                .thenReturn("F10","no","S10","quit","\n")   //dis

                //P2 stage2
                .thenReturn("no","H10","quit","\n")

                //P3 stage2
                .thenReturn("no","D5","H10","quit","\n")

                //P4 stage2
                .thenReturn("no","L20","quit","\n")

                //P2 stage3
                .thenReturn("no","L20","quit","\n")

                //P3 stage3
                .thenReturn("no","E30","quit","\n")

                //P4 stage 3
                .thenReturn("no","E30","quit","\n")

                //P2 stage 4
                .thenReturn("no","L20","D5","quit","\n")

                //P3 stage 4
                .thenReturn("no","S10","H10","D5","quit","\n")

                //P4 stage 4
                .thenReturn("no","E30","D5","quit","\n")

                //ended player 1 trim
                .thenReturn("F10","F10","F10","F10","\n");

        menu.quest(event);
        menu.updateRound();
    }

    @When("player 2 draws plague")
    public void player_2_draws_plague(){
        menu.plagueCard();
        menu.updateRound();
    }

    @When("player 3 draws prosperity")
    public void player_3_draws_prosperity(){
        menu.Prosperity();
        menu.updateRound();
    }

    @When("player 4 draws Queens favor")
    public void player_4_draws_Queens_favor(){
        when(mockScanner.nextLine()).thenReturn("F10","F10");
        menu.QueensFavor();
        menu.updateRound();
    }

    @When("player 1 draws sponsor second quest {string}")
    public void player_draws_sponsor_second_quest(String quest){
        when(mockScanner.nextInt()).thenReturn(1);
        menu.findingSponsor(quest);
    }

    @When("3 participants found for Q3 quest")
    public void three_participants_found_for_quest_Q3(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds the stage {string}")
    public void player1_builds_the_stage(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        when(mockScanner.nextLine())
                .thenReturn("F10","quit")            //stage 1
                .thenReturn("F10","D5","quit")       //stage 2
                .thenReturn("F10","S10","quit");     //stage 3
        menu.buildQuest(quest,sponsor,participants);

    }

    @When("quest {string} completed with two winners")
    public void quest_Q3_completed_with_two_winners(String event){

        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("D5","no","S10","D5","quit","\n")

                //P3 stage1
                .thenReturn("no","S10","D5","quit","\n")

                //P4 stage1
                .thenReturn("F70","no","D5","quit","\n")

                //P2 stage2
                .thenReturn("no","E30","quit","\n")

                // P3 stage2
                .thenReturn("no", "E30", "quit", "\n")

                // P2 stage3
                .thenReturn("no", "E30", "quit", "\n")

                // P3 stage3
                .thenReturn("no", "E30", "quit", "\n")

                // Ended player 1 trim
                .thenReturn("F10", "F10", "F10", "F10", "F10");


        menu.quest(event);
        menu.updateRound();
    }

    @Then("players shields should be updated correctly")
    public void players_shields_should_be_updated_correctly(){
        assertEquals(game.getPlayers().get(0).getShields(),0);
        assertEquals(game.getPlayers().get(1).getShields(),5);
        assertEquals(game.getPlayers().get(2).getShields(),7);
        assertEquals(game.getPlayers().get(3).getShields(),4);

    }

    @Then("player3 should be detected as winner")
    public void player3_should_be_detected_as_winner(){
        menu.printWinner();
        assertEquals(1, game.checkWinners().size());
        assertTrue(game.checkWinners().contains(game.getPlayers().get(2)));
    }

}
