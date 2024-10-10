package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck{
    private List<String> adventureDeck;
    private List<String> eventDeck;

    public Deck(){
        adventureDeck = new ArrayList<>();
        eventDeck = new ArrayList<>();
        initializeAdventureDeck();
        initializeEventDeck();
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

    private void addCards(String pre, int[] values, int[] counts){
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < counts[i]; j++){
                adventureDeck.add(pre + values[i]);
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


    public void shuffleAdventureDeck(){
        Collections.shuffle(adventureDeck);
    }

    public void shuffleEventDeck(){
        Collections.shuffle(eventDeck);
    }

    public List<String> getAdventureDeck(){
        return adventureDeck;
    }

    public List<String> getEventDeck(){
        return eventDeck;
    }
}