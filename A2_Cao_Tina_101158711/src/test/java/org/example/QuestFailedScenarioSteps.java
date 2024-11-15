package org.example;

import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import io.cucumber.java.Before;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class QuestFailedScenarioSteps {

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

    @Given("fourth game is created with players hand set to values")
    public void fourth_game_is_created_with_players_hand_set_to_values(){

        List<String> hand1 = Arrays.asList("F5","F5","F10","F10","F15","F15","F20","F20","D5","D5","B15","B15");
        List<String> hand2 = Arrays.asList("F5","F5","F20","F70","D5","D5","S10","S10","H10","H10","B15","E30");
        List<String> hand3 = Arrays.asList("F5","F5","F10","F20","D5","D5","S10","S10","H10","H10","L20","E30");
        List<String> hand4 = Arrays.asList("F5","F5","F10","D5","D5","S10","S10","S10","H10","H10","L20","E30");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }

        game.getPlayers().get(0).addCards(hand1);
        game.getPlayers().get(1).addCards(hand2);
        game.getPlayers().get(2).addCards(hand3);
        game.getPlayers().get(3).addCards(hand4);

        when(mockDeck.drawAdventureCard())
                //first quest draw
                .thenReturn("F5") //p2s1
                .thenReturn("F5") // p3s1
                .thenReturn("F5") // p4s1
                .thenReturn("S10"); //P1 draw after the quest

        menu = new Menu(game);
        mockScanner = mock(Scanner.class);
        menu.setScanner(mockScanner);
        menu.updateRound();
    }

    @When("player 1 draws and sponsor {string}")
    public void player1_draws_then_sponsor_quest(String quest){
        when(mockScanner.nextInt()).thenReturn(1);
        menu.findingSponsor(quest);
    }

    @When("players chose to participate")
    public void players_chose_to_participate(){
        when(mockScanner.nextInt()).thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        menu.findParticipants();
    }

    @When("player 1 builds the stages for quest {string}")
    public void player_1_builds_the_stages_for_quest_Q2(String quest){
        Player sponsor = menu.getSponsorplayer();
        List<Player> participants = menu.getParticipants();
        when(mockScanner.nextLine())
                .thenReturn("F20","quit")                    //stage 1
                .thenReturn("F15","D5","B15","quit","\n");        //stage 2

        menu.buildQuest(quest,sponsor,participants);

    }

    @When("players failed to complete the quest {string}")
    public void players_failed_to_complete_the_quest(String event){
        when(mockScanner.nextLine())
                //P2 stage1
                .thenReturn("F5","no","D5","quit","\n")   //discard, no opt out, cards to play
                //P3 stage1
                .thenReturn("F5","no","D5", "quit","\n")   //discard, no opt out, cards to play
                //P4 stage 1
                .thenReturn("F5","no","D5","quit","\n")   //discard, no opt out, cards to play
                //P1 trim extra cards with S10
                .thenReturn("S10");

        menu.quest(event);
    }

    @Then("players hands should be correctly updated")
    public void players_hands_should_be_correctly_updated(){
        //player draws S10 after the quest, # of cards draw = Q2(2) + usedcards(4) = 6 -> add 6 S10
        //original hand remaining = 8, as 4 played in the quest
        //6+8 need to trim 2 out, remaining S10 should be 4
        int count = 0;
        for(int i = 0; i < menu.getSponsorplayer().getHand().size(); i++){
            if(menu.getSponsorplayer().getHand().get(i).equalsIgnoreCase("S10")){
                count++;
            }
        }
        assertEquals(4,count);
        assertEquals(game.getPlayers().get(1).getHand().size(),11);
        assertEquals(game.getPlayers().get(2).getHand().size(),11);
        assertEquals(game.getPlayers().get(3).getHand().size(),11);

        assertTrue(game.getPlayers().get(0).getHand().containsAll(Arrays.asList("F5","F5","F10","F10","F15","F20","D5","S10","S10","S10","S10","B15")));
        assertTrue(game.getPlayers().get(1).getHand().containsAll(Arrays.asList("F5", "F5", "F20", "F70", "D5", "S10", "S10", "H10", "H10", "B15", "E30")));
        assertTrue(game.getPlayers().get(2).getHand().containsAll(Arrays.asList("F5", "F5", "F10", "F20", "D5", "S10", "S10", "H10", "H10", "L20", "E30")));
        assertTrue(game.getPlayers().get(3).getHand().containsAll(Arrays.asList("F5", "F5", "F10", "D5", "S10", "S10", "S10", "H10", "H10", "L20", "E30")));

    }
    @Then("all participants have not gained any shields")
    public void all_participants_have_not_gained_any_shields(){
        assertEquals(0,game.getPlayers().get(0).getShields());
        assertEquals(0,game.getPlayers().get(1).getShields());
        assertEquals(0,game.getPlayers().get(2).getShields());
        assertEquals(0,game.getPlayers().get(3).getShields());
        assertTrue(game.checkWinners().isEmpty());
    }
}
