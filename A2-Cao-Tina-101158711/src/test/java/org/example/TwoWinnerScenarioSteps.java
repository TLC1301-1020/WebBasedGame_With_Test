package org.example;

import io.cucumber.java.en.*;
import io.cucumber.java.Before;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TwoWinnerScenarioSteps {

    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @Before
    public void setUp() {
        mockDeck = mock(Deck.class);
        mockScanner = mock(Scanner.class);

        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };

        menu = new Menu(game);
        menu.setScanner(mockScanner);

    }
    @Given("second game is created with players hands set to values")
    public void second_game_is_created_with_players_hands_set() {

        List<String> hand1 = Arrays.asList("F5", "F10", "F10", "F10", "F15", "D5", "S10", "S10", "H10", "H10", "B15", "L20");
        List<String> hand2 = Arrays.asList("F5", "F10", "F20", "D5", "D5", "S10", "S10", "H10", "H10", "B15", "L20", "E30");
        List<String> hand3 = Arrays.asList("F5", "F5", "F10", "F15", "F20", "F20", "D5", "S10", "S10", "H10", "B15", "E30");
        List<String> hand4 = Arrays.asList("F5", "F10", "D5", "S10", "S10", "H10", "B15", "B15", "L20", "L20", "E30", "E30");

        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }

        game.getPlayers().get(0).addCards(hand1);
        game.getPlayers().get(1).addCards(hand2);
        game.getPlayers().get(2).addCards(hand3);
        game.getPlayers().get(3).addCards(hand4);



        when(mockDeck.drawAdventureCard())
                //first quest
                .thenReturn("L20","D5","F20") // stage 1
                .thenReturn("B15", "F20") // stage 2
                .thenReturn("F5", "D5") // stage 3
                .thenReturn("H10","E30") // stage 4

                .thenReturn("S10","S10","S10","S10","S10","S10","S10","S10","S10","S10","S10") //p1 draws

                //second quest
                .thenReturn("S10", "F5") //stage 1
                .thenReturn("L20", "S10") //stage 2
                .thenReturn("F10", "F5") //stage 3

                .thenReturn("F10");
        menu = new Menu(game);
        mockScanner = mock(Scanner.class);
        menu.setScanner(mockScanner);
        menu.updateRound();
    }

    @When("player1 draws then sponsor {string}")
    public void player1_draws_then_sponsor_quest(String quest){
        when(mockScanner.nextInt()).thenReturn(1);
        menu.findingSponsor(quest);
    }

    @When("participants found for Q4 quest")
    public void participants_found_for_the_quest_Q4(){
        when(mockScanner.nextInt()).thenReturn(1)
                    .thenReturn(1)
                    .thenReturn(1);
        menu.findParticipants();
    }

    @When("player1 builds the stage for {string}")
    public void player1_builds_stage_for_Q4(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        when(mockScanner.nextLine())
                .thenReturn("F10","quit")
                .thenReturn("F15","quit")
                .thenReturn("F10","S10","quit")

                .thenReturn("F10","S10","D5","quit");
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} completed with player1 hand updated")
    public void quest_Q4_completed_with_player1_hand_updated(String event){

        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("F20","no","B15","quit","\n")   //discard, no opt out, cards to play
                //P3 stage1
                .thenReturn("S10","no","D5", "quit","\n")   //discard, no opt out, cards to play
                //P4 stage 1
                .thenReturn("F20","no","B15","quit","\n")   //discard, no opt out, cards to play

                //P2 stage2
                .thenReturn("no","L20","quit","\n")
                //P4 stage2
                .thenReturn("no","D5","S10","quit","\n")

                //P2 stage3
                .thenReturn("no","S10","H10","quit","\n")
                //P4 stage3
                .thenReturn("no","E30","quit","\n")

                //p2 stage4
                .thenReturn("no","L20","D5","quit","\n")
                //p4 stage 4
                .thenReturn("no","S10","H10","B15","quit","\n")

                //ended player 1 trim
                .thenReturn("S10","S10","S10","S10","\n");

        menu.quest(event);
        menu.updateRound();
    }

    @When("player2 draws then player 3 sponsors {string}")
    public void player_2_draws_quest_Q3(String quest){
        when(mockScanner.nextInt())
                .thenReturn(2)
                .thenReturn(1);
        menu.findingSponsor(quest);

    }

    @When("2 participants found for Q3 quest")
    public void participants_found_for_Q3_quest(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(2)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player3 builds the stage for {string}")
    public void player3_builds_stage_for_Q3(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        when(mockScanner.nextLine())
                .thenReturn("F5","quit")

                .thenReturn("F10","quit")

                .thenReturn("F15","quit");

        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} completed with player3 hand updated")
    public void quest_Q3_completed_player3_hand_updated(String event){

        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("no","S10","quit","\n")

                //P4 stage1
                .thenReturn("no","L20","quit","\n")

                //P2 stage2
                .thenReturn("no","B15","quit","\n")

                //P4 stage2
                .thenReturn("no","E30","quit","\n")

                //P2 stage3
                .thenReturn("no","E30","quit","\n")

                //P4 stage3
                .thenReturn("no","L20","quit","\n")

                //ended player 3 trim
                .thenReturn("F10","F10","F10","F10","F10","F10","\n");

        menu.quest(event);
        menu.updateRound();
    }

    @Then("players should have correct shields")
    public void players_should_have_correct_shields(){
        assertEquals(0,game.getPlayers().get(0).getShields());
        assertEquals(7,game.getPlayers().get(1).getShields());
        assertEquals(0,game.getPlayers().get(2).getShields());
        assertEquals(7,game.getPlayers().get(3).getShields());
    }

    @Then("two winners should be detected")
    public void two_winners_should_be_detected(){
        assertEquals(2,game.checkWinners().size());
        assertTrue(game.checkWinners().contains(game.getPlayers().get(1)));
        assertTrue(game.checkWinners().contains(game.getPlayers().get(3)));
        menu.printWinner();

    }


}