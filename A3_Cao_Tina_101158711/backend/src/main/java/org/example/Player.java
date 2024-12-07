package org.example;

import java.util.*;

public class Player {
    private String name;
    private List<String> hand;
    private int shields;

    public Player(String name) {
        this.name = name;
        this.hand = new LinkedList<>();
        this.shields = 0;
    }

    public void addCard(String card){
        hand.add(card);
        getSortedHand();
    }

    public void addCards(List<String> cards){
        hand.addAll(cards);
        getSortedHand();
    }


    public void getSortedHand() {
        Collections.sort(hand, new Comparator<String>(){
            @Override
            public int compare(String card1, String card2) {
                boolean isFoe1 = card1.startsWith("F");
                boolean isFoe2 = card2.startsWith("F");

                if (isFoe1 && !isFoe2) {
                    return -1;
                } else if (!isFoe1 && isFoe2) {
                    return 1;
                } else {
                    int value1 = Integer.parseInt(card1.substring(1));
                    int value2 = Integer.parseInt(card2.substring(1));
                    return Integer.compare(value1, value2);
                }
            }
        });
    }

    public void updateShields(int diff) {
        shields += diff;
        if(shields < 0){
            shields = 0;
        }
    }
    public Boolean trimHand(String cardToTrim) {
        if (hand.contains(cardToTrim)) {
            hand.remove(cardToTrim);
            return true;
        } else {
            return false;
        }
    }
    public int countFoeCards(){
        int count = 0;
        for(int i = 0; i < hand.size(); i++){
            if (hand.get(i).contains("F")){
                count++;
            }
        }
        return count;
    }


    //return amount of foe card in player hand
    public int foeCardAmount(){
        int count = 0;
        for (String card : hand) {
            if (card.contains("F")) {
                count++;
            }
        }
        return count;
    }
    public int getShields(){
        return shields;
    }

    public List<String> getHand() {
        return hand;
    }

    public String getName(){
        return name;
    }

}