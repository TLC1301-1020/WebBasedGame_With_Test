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
        distributeAdventureCards();
    }

    private void initializePlayers(){
        players = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            players.add(new Player("Player" + i));
        }
    }

    private void distributeAdventureCards(){
        for(Player player: players){
            List<String> drawnCards = deck.getAdventureDeck().subList(0,12);
            player.addCards(drawnCards);
            deck.getAdventureDeck().subList(0,12).clear();
        }
    }
    public List<Player> getPlayers(){
        return players;
    }

    public Deck getDeck(){
        return deck;
    }

    public List<Player> checkWinners() {

        List<Player> winners = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getShields() >= 7) {
                winners.add(players.get(i));
            }
        }
        return winners;
    }

    public void reuseDeck(){
        if(deck.checkAdventureCard()){
            deck.addDiscardedAdventureCards();
        }
        if(deck.checkEventCard()){
            deck.addDiscardedEventCards();
        }
    }

    public boolean TrimCards(Player currentlyPlayer,String card){
        currentlyPlayer.getSortedHand();
        Boolean trimmed = currentlyPlayer.trimHand(card);
        if(trimmed){
            deck.getAdventureDiscardPile().add(card);
            return true;
        }else{
            return false;
        }
    }

}