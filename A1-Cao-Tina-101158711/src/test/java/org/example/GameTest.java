package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;


public class GameTest {

    private Game game;
    private Deck deck;

    @BeforeEach
    public void setUp() {
        game = new Game();
        deck = new Deck(); // For directly testing the deck state
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
        List<String> eventDeck = deck.getEventDeck();
        Assertions.assertEquals(5, eventDeck.size(), "The number of event card is not correct");

        long countPlague = eventDeck.stream().filter(card -> card.equals("Plague")).count();
        Assertions.assertEquals(1, countPlague, "There should be 1 plague card in the event deck");

        long countQueensFavor = eventDeck.stream().filter(card -> card.equals("Queen's Favor")).count();
        Assertions.assertEquals(2, countQueensFavor, "There should be 2 'Queen's Favor' cards in the event deck.");

        long countProsperity = eventDeck.stream().filter(card -> card.equals("Prosperity")).count();
        Assertions.assertEquals(2, countProsperity, "There should be 2 'Prosperity' cards in the event deck.");


    }
}
