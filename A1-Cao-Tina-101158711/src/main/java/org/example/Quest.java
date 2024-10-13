package org.example;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int level;
    private Player sponsor;
    private List<Stage> stages;
    private List<Player> participants;
    private String event;

    public Quest(String event,Player sponsor,List<Player> participants){
        this.level = Integer.parseInt(event.substring(1));
        this.sponsor = sponsor;
        stages = new ArrayList<>();
        this.participants = participants;
        this.event = event;
    }

    public boolean initializeStages(int currentLevel, String foeCard, List<String> weaponCards){
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
    public int getStages(){
        return stages.size();
    }

    public Stage getStageIndex(int level){
        return stages.get(level-1);
    }

    public void removeParticipant(Player participant){
        participants.remove(participant);
    }
}