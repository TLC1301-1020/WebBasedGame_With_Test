package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Quest {
    private Player sponsor;
    private List<Stage> stages;
    private List<Player> participants;
    private String event;
    private Player challenger;
    private int count = 0;
    private int totalCardUsed;

    public Quest(String event,Player sponsor,List<Player> participants){
        this.sponsor = sponsor;
        stages = new ArrayList<>();
        this.participants = participants;
        this.event = event;
        participants.sort(Comparator.comparing(Player::getName));
        challenger = participants.get(count);
    }

    public boolean initializeStages(int currentLevel, String foeCard, List<String> weaponCards){
        totalCardUsed += weaponCards.size()+1;
        Stage stage = new Stage(currentLevel,foeCard,weaponCards);
        if(stages.isEmpty()||currentLevel == 0){
            stages.add(stage);
        }else{
            if(stages.get(currentLevel-1).getTotalValue() >= stage.getTotalValue()){
                return false;
            }else{
                stages.add(stage);

            }
        }
        return true;
    }


    public void removeParticipant(){
        participants.remove(challenger);
        count--;
    }


    public int getStageTotalValue(int index){
        return stages.get(index).getTotalValue();
    }


    public List<Player> getParticipants(){
        return participants;
    }

    public boolean questNoParticipants(){
        return participants.isEmpty();
    }

    public boolean currentStagePassed(int playerHandTotal, int index){
        return playerHandTotal >= getStageTotalValue(index);
    }

    public Player getSponsor(){
        return sponsor;
    }

    public Player getChallenger(){
        return challenger;
    }

    public void updateChallenger(){

        if(count < participants.size()-1){
            count ++;
            challenger = participants.get(count);
        //all players went once
        }else if(count == participants.size()-1){
            count = 0;
            challenger = participants.get(count);
        }else {
            count++;
            challenger = participants.get(count);
        }
    }

    //see how many cards sponsor used to build the stage
    public int getTotalCardUsed(){
        return totalCardUsed;
    }


    public void clearQuest(){
        //TODO: CHECK CORRECTNESS
        //clear each stage
        for (Stage stage : stages) {
            stage.clearStage();
        }

        stages = new ArrayList<>();
        participants = new ArrayList<>();
        event = null;
        challenger = null;
        count = 0;
        totalCardUsed = 0;

    }
    public void clearSponsor(){
        sponsor = null;
    }




}