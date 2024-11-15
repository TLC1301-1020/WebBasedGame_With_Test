
package org.example;

import io.cucumber.java.en.*;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class A1ScenarioSteps {
    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @Given("first game is created with players hands set to values")
    public void first_game_is_created_with_players_hands_set_to_values() {
        mockDeck = Mockito.mock(Deck.class);
        mockScanner = Mockito.mock(Scanner.class);
        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };


        List<String> hand1 = Arrays.asList("F5", "F5", "F15", "F15", "D5", "S10", "S10", "H10", "H10", "B15", "B15", "L20");
        List<String> hand2 = Arrays.asList("F5", "F5", "F5", "F10", "F10", "F10", "D5", "H10", "B15", "B15", "L20", "E30");
        List<String> hand3 = Arrays.asList("F5", "F5", "F5", "F15", "D5", "S10", "H10", "S10", "H10", "B15", "L20", "S10");
        List<String> hand4 = Arrays.asList("F5", "F15", "F15", "F40", "D5", "D5", "S10", "H10", "H10", "B15", "L20", "E30");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }

        game.getPlayers().get(0).addCards(hand1);
        game.getPlayers().get(1).addCards(hand2);
        game.getPlayers().get(2).addCards(hand3);
        game.getPlayers().get(3).addCards(hand4);



        Mockito.when(mockDeck.drawAdventureCard())
                .thenReturn("F30","S10","B15") // p1s1
                .thenReturn("F10","L20","L20") // p1s2
                .thenReturn("B15","S10") // p3s3
                .thenReturn("F30","L20") // p3s4
                .thenReturn("S10"); //p2 draws


        menu = new Menu(game);
        mockScanner = Mockito.mock(Scanner.class);
        menu.setScanner(mockScanner);
        menu.updateRound();

    }

    @When("player 1 declines to sponsor the quest {string}, player2 sponsors")
    public void first_player_draws_a_quest_player2_sponsors(String quest){
        Mockito.when(mockScanner.nextInt()).thenReturn(2)
                .thenReturn(1);
        menu.findingSponsor(quest);

    }

    @When("three players participate the quest")
    public void three_players_participate_the_quest(){
        Mockito.when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player2 sets the stage for {string}")
    public void sponsor_sets_the_stage(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        Mockito.when(mockScanner.nextLine())
                .thenReturn("F10")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("B15")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("B15")
                .thenReturn("H10")
                .thenReturn("quit")

                .thenReturn("F5")
                .thenReturn("L20")
                .thenReturn("E30")
                .thenReturn("quit");
        menu.buildQuest(quest,sponsor,participants);
    }

    @When("quest {string} completed with sponsor card updated and participant shields updated")
    public void quest_completed_sponsor_card_updated_and_shields_updated(String event) {

        Mockito.when(mockScanner.nextLine())
                .thenReturn("F5")   //dis p1
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")

                .thenReturn("F5")   //dis
                .thenReturn("no")
                .thenReturn("S10")
                .thenReturn("D5")
                .thenReturn("quit")
                .thenReturn("\n")

                .thenReturn("F5")   //dis
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("H10")
                .thenReturn("quit")
                .thenReturn("\n")

                //p1 stage 2
                .thenReturn("no")
                .thenReturn("H10")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")

                //p3 stage 2
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")

                //p4 stage 2
                .thenReturn("no")
                .thenReturn("H10")
                .thenReturn("B15")
                .thenReturn("quit")
                .thenReturn("\n")

                //p3 stage 3
                .thenReturn("no")
                .thenReturn("L20")
                .thenReturn("H10")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")

                //p4 stage 3
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("S10")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")

                //p3 stage 4
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("H10")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")
                //p4 stage 4
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("S10")
                .thenReturn("L20")
                .thenReturn("E30")
                .thenReturn("quit")
                .thenReturn("\n")

                //ended player 2 trim
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("\n");

        menu.quest(event);
    }

    @Then("sponsor player should have exact 12 cards")
    public void sponsor_player_should_have_exact_cards(){
        assertTrue(menu.getSponsorplayer().getHand().containsAll(List.of("F5","F5","D5","S10","S10","S10","S10","S10","S10","S10","S10","S10")));
        assertEquals(menu.getSponsorplayer().getHand().size(),12);
    }


    @Then("shields should be updated correctly")
    public void shields_should_be_updated_correctly(){
        assertEquals(menu.getParticipants().size(),1);                       //only one participant left at the last stage
        assertTrue(menu.getParticipants().contains(game.getPlayers().get(3)));     //only player 4 is in the participant list
        assertEquals(game.getPlayers().get(0).getShields(),0);              //player 1 shield 0
        assertEquals(game.getPlayers().get(1).getShields(),0);              //player 2 shield 0
        assertEquals(game.getPlayers().get(2).getShields(),0);              //player 3 shield 0
        assertEquals(game.getPlayers().get(3).getShields(),4);              //player 4 shield 4 as won the quest

    }

    @Then("cards in each player hand should be correct")
    public void cards_in_each_player_hand_should_be_correct(){
        assertTrue(game.getPlayers().get(0).getHand().containsAll(Arrays.asList("F5", "F10", "F15", "F15", "F30", "H10", "B15", "B15", "L20")));
        assertTrue(game.getPlayers().get(2).getHand().containsAll(Arrays.asList("F5", "F5", "F15", "F30", "S10")));
        assertTrue(game.getPlayers().get(3).getHand().containsAll(Arrays.asList("F15", "F15", "F40", "L20")));
    }


}



