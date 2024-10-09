package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    @DisplayName("R1 Test")
    void testSetupDecks() {
        Game game = new Game();
        game.setupPlayers();
        boolean isDeckSetUp = game.setupDecks();
        boolean isDeckIntegrityVerified = game.verifyDeckIntegrity();
        boolean arePlayersReady = game.checkPlayerReadiness();
        Assertions.assertTrue(isDeckSetUp, "Decks not set up.");
        Assertions.assertTrue(isDeckIntegrityVerified, "Deck not verified.");
        Assertions.assertTrue(arePlayersReady, "Players not ready.");
    }

    @Test
    @DisplayName("R2 Test: Distribute 12 adventure cards to each player and update the deck")
    void AdventureCardsToPlayers() {
        Game game = new Game();
        game.setupPlayers();


        boolean isCardsDistributed = game.distributeAdventureCards();
        int playerCardCount = game.getPlayerCardCount();
        int remainingDeckCount = game.getRemainingDeckCount();

        Assertions.assertTrue(isCardsDistributed, "Cards not distributed.");
        Assertions.assertEquals(12, playerCardCount, "Each player do not have 12 cards.");
        Assertions.assertEquals(remainingDeckCount, expectedDeckCount, "Deck not updated correctly.");
    }
}
