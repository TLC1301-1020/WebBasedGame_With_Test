package org.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Player {
    private String name;
    private List<String> hand;
    private int shields;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.shields = 0;
    }

    public String getName(){
        return name;
    }

    public void addCards(List<String> cards){
        hand.addAll(cards);
        getSortedHand();
    }

    public List<String> getSortedHand() {
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
        return hand;
    }

    public List<String> getHand() {
        return hand;
    }

    public void updateShields(int diff) {
        shields += diff;
        if(shields < 0){
            shields = 0;
        }
    }

    public int getShields(){
        return shields;
    }

    public Boolean trimHand(String cardToTrim) {
        if (hand.contains(cardToTrim)) {
            hand.remove(cardToTrim);
            return true;
        } else {
            return false;
        }
    }


}