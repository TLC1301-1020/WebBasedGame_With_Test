package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;


public class GameTest {

    private Game game;
    private Deck deck;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = new Deck();
        player = new Player("Test Player");
    }

    @Test
    @DisplayName("R1 - Player Initialization")
    public void testPlayersInitialized() {
        List<Player> players = game.getPlayers();
        Assertions.assertEquals(4, players.size(), "The number of players is incorrect.");

        for (int i = 1; i <= 4; ++i) {
            Assertions.assertEquals("Player" + i, players.get(i - 1).getName(), "Player name is not correct.");
        }

    }

    @Test
    @DisplayName("R1 - Adventure Deck Initialization")
    public void testAdventureDeckInitialized() {
        //50 weapon cards + 50 foe cards = 100 cards in adventure deck
        List<String> adventureDeck = deck.getAdventureDeck();
        Assertions.assertEquals(100, adventureDeck.size(), "Adventure deck amount isn't correct");

        long countF5 = adventureDeck.stream().filter(card -> card.equals("F5")).count();
        Assertions.assertEquals(8, countF5, "There should be 8 F5 cards in the adventure deck.");

        long countF70 = adventureDeck.stream().filter(card -> card.equals("F70")).count();
        Assertions.assertEquals(1, countF70, "There should be 1 F70 card in the adventure deck.");

        long countD5 = adventureDeck.stream().filter(card -> card.equals("D5")).count();
        Assertions.assertEquals(6, countD5, "There should be 6 D5 cards in the adventure deck.");

        long countE30 = adventureDeck.stream().filter(card -> card.equals("E30")).count();
        Assertions.assertEquals(2, countE30, "There should be 2 E30 cards in the adventure deck.");


    }

    @Test
    @DisplayName("R1 - Event Deck Initialization")
    public void testEventDeckInitialized() {
        //12 Q cards and 5 E cards = 17 cards in event deck
        List<String> eventDeck = deck.getEventDeck();
        Assertions.assertEquals(17, eventDeck.size(), "The number of event card is not correct");

        long countPlague = eventDeck.stream().filter(card -> card.equals("Plague")).count();
        Assertions.assertEquals(1, countPlague, "There should be 1 plague card in the event deck");

        long countQueensFavor = eventDeck.stream().filter(card -> card.equals("Queen's Favor")).count();
        Assertions.assertEquals(2, countQueensFavor, "There should be 2 'Queen's Favor' cards in the event deck.");

        long countProsperity = eventDeck.stream().filter(card -> card.equals("Prosperity")).count();
        Assertions.assertEquals(2, countProsperity, "There should be 2 'Prosperity' cards in the event deck.");

    }

    @Test
    @DisplayName("R2 - Distribute out adventure cards to players")
    public void testAdventureCardsDistributed(){
        for(Player player: game.getPlayers()){
            Assertions.assertEquals(12, player.getHand().size(),"Each player should receive 12 adventure cards");
        }
    }

    @Test
    @DisplayName("R2 - Deck size reduced after distribution")
    public void testDeckUpdatedAfterDistribution(){
        Assertions.assertEquals(100 - 48, game.getDeck().getAdventureDeck().size(), "Deck amount incorrect");
    }

    @Test
    @DisplayName("R2 - Player cards sorting")
    public void testSortedHands(){
        player.addCards(List.of("H10", "F5", "S10", "F15", "B15", "H5", "F20", "L20"));
        List<String> sortedHand = player.getSortedHand();
        List<String> expectedOrder = List.of("F5", "F15", "F20", "H5", "H10", "S10", "B15", "L20");
        Assertions.assertEquals(expectedOrder, sortedHand, "The player's hand should be sorted correctly");
    }

    @Test
    @DisplayName("R6 - Identify winner(s)")
    public void testIdentifyWinners() {
        game.getPlayers().get(0).setShields(8);
        game.getPlayers().get(1).setShields(7);
        game.getPlayers().get(2).setShields(10);
        game.getPlayers().get(3).setShields(3);

        List<Player> winners = game.checkWinners();
        Assertions.assertEquals(3, winners.size(), "There should be 3 winners.");
        Assertions.assertFalse(winners.contains(game.getPlayers().get(3)),"Player 4 should not be a winner.");
        Assertions.assertTrue(winners.contains(game.getPlayers().get(0)), "Player 1 should be a winner.");
        Assertions.assertTrue(winners.contains(game.getPlayers().get(1)), "Player 2 should be a winner.");
        Assertions.assertTrue(winners.contains(game.getPlayers().get(2)), "Player 3 should be a winner.");
    }

    @Test
    @DisplayName("R6 - Positive value shield")
    public void testPlayerShield() {
        Player player = game.getPlayers().get(0);
        player.setShields(-5);
        game.checkPlayersShields();
        Assertions.assertEquals(0, player.getShields(), "Player shields should be reset to 0 if negative.");
    }

}