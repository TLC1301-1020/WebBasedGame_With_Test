package org.example;

import java.util.List;

public class Stage {

    private String foeCard;
    private List<String> weaponCards;
    private int totalValue;
    private int index;    //stage index in the array
    private int stageLevel;
    public Stage(int index,String foeCard, List<String> weaponCards) {
        this.index = index;
        this.foeCard = foeCard;
        this.weaponCards = weaponCards;
        this.totalValue = addSponsorTotal(foeCard,weaponCards);
        this.stageLevel = index + 1;
    }

    public int addSponsorTotal(String foeCard, List<String> weaponCards) {
        //sponsor built no weapon cards, return foe card value
        if (weaponCards.isEmpty()) {
            return getCardValue(foeCard);
        } else {
            //sum up foe and weapon card(s) value
            totalValue += getCardValue(foeCard);
            for (int i = 0; i < weaponCards.size(); i++) {
                totalValue += getCardValue(weaponCards.get(i));
            }
        }
        return totalValue;
    }

    //getters
    //index of the stage in the stages
    public int getIndex(){
        return index;
    }

    //stage level in the quest
    public int getLevel(){
        return stageLevel;
    }

    public int getCardValue(String card) {
        return Integer.parseInt(card.substring(1));
    }

    public int getTotalValue(){
        return totalValue;
    }

    public List<String> getWeaponCards(){
        return weaponCards;
    }

    public String getFoeCard(){
        return foeCard;
    }
}




