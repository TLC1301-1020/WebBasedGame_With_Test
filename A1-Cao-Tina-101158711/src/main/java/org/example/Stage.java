package org.example;

import java.util.List;

public class Stage {

    private String foeCard;
    private List<String> weaponCards;
    private int totalValue;
    private int stageLevel;

    public Stage(int stageLevel,String foeCard, List<String> weaponCards) {
        this.stageLevel = stageLevel;
        this.foeCard = foeCard;
        this.weaponCards = weaponCards;
        this.totalValue = addSponsorTotal(foeCard,weaponCards);
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
    public int getStageLevel(){
        return stageLevel+1;
    }
}




