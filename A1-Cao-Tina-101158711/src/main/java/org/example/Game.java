package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game{
    private List<Player> players;
    private Deck deck;


    public Game(){
        initializeGame();
    }

    private void initializeGame(){
        initializePlayers();
        deck = new Deck();
        deck.shuffleAdventureDeck();
        deck.shuffleEventDeck();
    }


    private  void initializePlayers(){
        players = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            players.add(new Player("Player" + i));
        }
    }

    public List<Player> getPlayers(){
        return players;
    }
}