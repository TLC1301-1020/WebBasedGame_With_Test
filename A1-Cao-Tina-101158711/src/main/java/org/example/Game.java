package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Cards> adventureDeck;
    private List<Cards> eventDeck;
    private List<Player> players;

    public void setupPlayers() {
    }

    public boolean setupDecks() {

        adventureDeck = new ArrayList<>();
        eventDeck = new ArrayList<>();
        return true;
    }

    public boolean verifyDeckIntegrity() {
        return true;
    }

    public boolean checkPlayerReadiness() {

        return true;
    }
}
