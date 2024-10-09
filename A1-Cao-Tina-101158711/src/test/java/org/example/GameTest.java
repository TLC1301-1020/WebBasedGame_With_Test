package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {
    GameTest() {
    }

    @Test
    @DisplayName("R1 Test")
    void testSetupAdventureAndEventDeck() {
        Game game = new Game();
        game.setupPlayers();
        boolean isDeckSetUp = game.setupDecks();
        boolean isDeckIntegrityVerified = game.verifyDeckIntegrity();
        boolean arePlayersReady = game.checkPlayerReadiness();
        Assertions.assertTrue(isDeckSetUp, "Decks not set up.");
        Assertions.assertTrue(isDeckIntegrityVerified, "Deck not verified.");
        Assertions.assertTrue(arePlayersReady, "Players not ready.");
    }
}
