package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GameTest {
    private Game game;


    @BeforeEach
    void setUp() {
        game = new Game();
        game.startGame();
    }

    @Test
    @DisplayName("R1 Test")
    //Game set up adventure and event deck, verifies the players and deck are initialized correctly
    void testDecksInitialized() {
        Assertions.assertNotNull(game.getDeck().getAdventureDeck(), "Adventure not initialized");
        Assertions.assertNotNull(game.getDeck().getEventDeck(), "Event deck not initialized");

        Assertions.assertEquals(100, game.getDeck().getAdventureDeckSize(), "Initial adventure deck not 100 cards.");
        Assertions.assertEquals(4, game.getPlayers().size(), "Game does not have exactly 4 players");


    }
}