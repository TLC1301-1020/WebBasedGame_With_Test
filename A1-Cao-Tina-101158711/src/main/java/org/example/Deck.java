package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck{
    private List<String> adventureDeck;
    private List<String> eventDeck;
    private List<String> eventDiscardPile;
    private List<String> adventureDiscardPile;

    public Deck(){
        adventureDeck = new ArrayList<>();
        eventDeck = new ArrayList<>();
        initializeAdventureDeck();
        initializeEventDeck();
        eventDiscardPile = new ArrayList<>();
        adventureDiscardPile = new ArrayList<>();
    }

    private void initializeAdventureDeck(){
        int[] foeValues = {5,10,15,20,25,30,35,40,50,70};
        int[] foeCounts = {8,7,8,7,7,4,4,2,2,1};
        addCards("F",foeValues,foeCounts);

        int[] weaponValues = {5,10,10,15,20,30};
        int[] weaponCounts = {6,12,16,8,6,2};

        String[] weaponNames = {"D","H","S","B","L","E"};
        addCards(weaponNames,weaponValues,weaponCounts);
    }

    private void initializeEventDeck(){
        int[] questCounts = {3,4,3,2};
        for(int i = 0; i < questCounts.length; i++){
            for(int j = 0; j < questCounts[i]; j++) {
                eventDeck.add("Q" + (i+2));

            }
        }
        eventDeck.add("Plague");
        eventDeck.add("Queen's Favor");
        eventDeck.add("Queen's Favor");
        eventDeck.add("Prosperity");
        eventDeck.add("Prosperity");
    }

    private void addCards(String prefixes, int[] values, int[] counts){
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < counts[i]; j++){
                adventureDeck.add(prefixes + values[i]);
            }
        }
    }

    private void addCards(String[] prefixes, int[] values, int[] counts){
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < counts[i]; j++){
                adventureDeck.add(prefixes[i] + values[i]);

            }
        }
    }

    public void discardAdventureCard(String card){
        adventureDiscardPile.add(card);
    }

    public void discardEventCard(String card){
        eventDiscardPile.add(card);
    }

    public void shuffleAdventureDeck(){
        Collections.shuffle(adventureDeck);
    }

    public void shuffleEventDeck(){
        Collections.shuffle(eventDeck);
    }

    //reuse cards added and shuffled
    public void addDiscardedAdventureCards(){
        if(!adventureDiscardPile.isEmpty()){
            adventureDeck.addAll(adventureDiscardPile);
            shuffleAdventureDeck();
            adventureDiscardPile.clear();
        }
    }
    //reuse cards added and shuffled
    public void addDiscardedEventCards(){
        if(!eventDiscardPile.isEmpty()){
            eventDeck.addAll(eventDiscardPile);
            shuffleEventDeck();
            eventDiscardPile.clear();
        }
    }

    public List<String> getEventDiscardPile(){
        return eventDiscardPile;
    }

    public List<String> getAdventureDiscardPile(){
        return adventureDiscardPile;
    }

    public List<String> getAdventureDeck(){
        return adventureDeck;
    }

    public List<String> getEventDeck(){
        return eventDeck;
    }

    //true if empty
    public boolean checkEventCard(){
        return eventDeck.isEmpty();
    }

    //true if empty
    public boolean checkAdventureCard(){
        return adventureDeck.isEmpty();
    }

}