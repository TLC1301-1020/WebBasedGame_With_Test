package org.example;

import io.cucumber.java.en.*;

import java.util.ArrayList;
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

    @Given("second game is created with players hands set to values")
    public void second_game_is_created_with_players_hands_set() {
        mockDeck = mock(Deck.class);
        mockScanner = mock(Scanner.class);
        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };

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
                .thenReturn("L20") // p2s1
                .thenReturn("D5") // p3s1
                .thenReturn("F20") // p4s1

                .thenReturn("B15") // p2s2
                .thenReturn("F20") // p4s2

                .thenReturn("F5") // p2s3
                .thenReturn("D5") // p4s3

                .thenReturn("H10") //p2s4
                .thenReturn("E30") // p4s4

                .thenReturn("S10","S10","S10","S10","S10","S10","S10","S10","S10","S10","S10") //p1 draws

                //second quest
                .thenReturn("S10") //P2S1
                .thenReturn("F5")  //P4S1

                .thenReturn("L20") //P2S2
                .thenReturn("S10") //P4S2

                .thenReturn("F10") //P2S3
                .thenReturn("F5") //p4S3

                .thenReturn("F10"); //p3 draws

        menu = new Menu(game);
        mockScanner = mock(Scanner.class);
        menu.setScanner(mockScanner);
        menu.updateRound();
    }

    @When("player 1 draws then sponsor {string}")
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

    @When("sponsor builds the stage for {string}")
    public void sponsor_builds_stage_for_Q4(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        when(mockScanner.nextLine())
                .thenReturn("F10")
                .thenReturn("quit")

                .thenReturn("F15")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("S10")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("S10")
                .thenReturn("D5")
                .thenReturn("quit");
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} completed with player1 hand updated")
    public void quest_Q4_completed_with_player1_hand_updated(String event){

        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("F20")   //dis
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("quit")
                .thenReturn("\n")
                //P3 stage1
                .thenReturn("S10")   //dis
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("quit")
                .thenReturn("\n")
                //P4 stage1
                .thenReturn("F20")   //dis
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("quit")
                .thenReturn("\n")

                //P2 stage2
                .thenReturn("no")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")
                //P4 stage2
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")

                //P2 stage3
                .thenReturn("no")
                .thenReturn("S10")
                .thenReturn("H10")
                .thenReturn("quit")
                .thenReturn("\n")
                //P4 stage3
                .thenReturn("no")
                .thenReturn("E30")
                .thenReturn("quit")
                .thenReturn("\n")

                //p2 stage4
                .thenReturn("no")
                .thenReturn("L20")
                .thenReturn("D5")
                .thenReturn("quit")
                .thenReturn("\n")

                //p4 stage 4
                .thenReturn("no")
                .thenReturn("S10")
                .thenReturn("H10")
                .thenReturn("B15")
                .thenReturn("quit")
                .thenReturn("\n")

                //ended player 1 trim
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("\n");

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

    @When("participants found for Q3 quest")
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
                .thenReturn("F5")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("quit")

                .thenReturn("F15")
                .thenReturn("quit");

        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} completed with player3 hand updated")
    public void quest_Q3_completed_player3_hand_updated(String event){

        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("no")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")
                //P4 stage1
                .thenReturn("no")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")
                //P2 stage2
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("quit")
                .thenReturn("\n")

                //P4 stage2
                .thenReturn("no")
                .thenReturn("E30")
                .thenReturn("quit")
                .thenReturn("\n")
                //P2 stage3
                .thenReturn("no")
                .thenReturn("E30")
                .thenReturn("quit")
                .thenReturn("\n")

                //P4 stage3
                .thenReturn("no")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")

                //ended player 3 trim
                .thenReturn("F10")
                .thenReturn("F10")
                .thenReturn("F10")
                .thenReturn("F10")
                .thenReturn("F10")
                .thenReturn("F10")
                .thenReturn("\n");

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