package org.example;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Game {
    private Quest quest;
    private List<Player> players;
    private Deck deck;
    private Player currentPlayer;
    private int currentRound;
    private Player respondingPlayer;
    private List<Player> questPlayers;
    private String eventCard;
    private List<String> sponsorPlay;
    private int totalHandPlayed;

    private boolean sponsorFound;
    private boolean participantFound;
    private int sponsorCount;
    private int participateCount;

    private int currentStage;       //current building or playing stage
    private int totalStage;         //quest #-1, starting from 0
    private int challengerSize;
    private boolean eventHappened;
    private boolean sponsorTrim = false;
    private int challengerCompleted;

    public Game() {
    }

    public void initializeGame() {
        setCounts();
        eventHappened = false;
        currentRound = -1;
        deck = new Deck();
        deck.initializeAdventureDeck();
        deck.initializeEventDeck();
        deck.shuffleAdventureDeck();
        deck.shuffleEventDeck();
        distributeAdventureCards();
        questPlayers = new ArrayList<>();
        sponsorPlay = new ArrayList<>();
        totalHandPlayed = 0;
        currentStage = 0;
        totalStage = 0;
    }


    public void initializePlayers() {
        players = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            players.add(new Player("Player" + i));
        }
        currentPlayer = players.get(0);
        respondingPlayer = currentPlayer;
    }

    private void distributeAdventureCards() {
        for (Player player : players) {
            List<String> drawnCards = deck.getAdventureDeck().subList(0, 12);
            player.addCards(drawnCards);
            deck.getAdventureDeck().subList(0, 12).clear();
        }
    }

    //update player round
    public void nextRound() {
        if (currentRound == -1) {
            currentRound = 0;
        } else {
            currentRound = (currentRound + 1) % getPlayers().size();
        }
        currentPlayer = getPlayers().get(currentRound);
        respondingPlayer = currentPlayer;
    }

    public void drawEventCard() {
        eventCard = getDeck().drawEventCard();
        discardEventCard();
    }

    public void discardEventCard() {
        getDeck().discardEventCard(eventCard);
    }

    //'player' discard 'card'
    public void TrimCards(Player player, String card) {
        Boolean trimmed = player.trimHand(card);
        if (trimmed) {
            deck.getAdventureDiscardPile().add(card);
        }
    }

    public boolean trimNeeded(Player p) {
        return p.getHand().size() > 12;
    }

    //'player' draws adventure cards by 'amount' times
    public void playerDrawAdventureCard(Player player, int amount) {
        for (int i = 1; i <= amount; i++) {
            player.addCard(getDeck().drawAdventureCard());
        }
    }

    /****event cards*****/
    //plague card is drawn - current player loses two shields
    public void Plague() {
        currentPlayer.updateShields(-2);

    }

    //queens favor card is drawn - current player draws two cards, trim if needed
    public void QueensFavor() {
        playerDrawAdventureCard(currentPlayer, 2);
    }

    //prosperity card is drawn - every player draws two cards, trim during play round
    public void Prosperity() {
        for (int i = 0; i < getPlayers().size(); i++) {
            playerDrawAdventureCard(getPlayers().get(i), 2);
        }
    }

    //add the card value to player total value and remove card from hand
    public void participantStageCards(String card) {
        //remove the card from challenger hand
        getChallenger().getHand().remove(card);
        int value = Integer.parseInt(card.substring(1));
        totalHandPlayed += value;
    }

    //true if the participant passed the stage at [index]
    public boolean participantPassed(int index) {
        if (quest.currentStagePassed(totalHandPlayed, index)) {
            quest.updateChallenger();
            totalHandPlayed = 0;
            return true;
        }
        //remove the failed participant
        quest.removeParticipant();
        if (!quest.questNoParticipants()) {
            quest.updateChallenger();

            totalHandPlayed = 0;
        }
        return false;
    }

    //store the 'input' in sponsorPlay
    //first will be foe card
    //assume first is the foe card
    public void addBuildingCards(String input) {
        quest.getSponsor().getHand().remove(input);
        sponsorPlay.add(input);
    }


    //create the stage in the quest (int stageLevel starts from 0)
    public void createStage(int stageIndex) {
        //get the foe card and remove
        String foe = sponsorPlay.remove(0);
        //create the stage
        quest.initializeStages(stageIndex, foe, sponsorPlay);
        sponsorPlay.clear();
    }

    public void cleanQuestPlayers(){
        questPlayers = new ArrayList<>();
    }
    public void addQuestPlayers() {
        questPlayers.add(respondingPlayer);
    }

    //create the quest
    public void createQuest() {
        //get the sponsor
        Player sponsor = questPlayers.remove(0);
        //create the quest
        quest = new Quest(eventCard, sponsor, questPlayers);
        totalStage = Integer.parseInt(eventCard.substring(1))-1;
        currentStage = 0;
    }


    public void sponsorGetCards(){
        Player p = quest.getSponsor();
        int totalCards = Integer.parseInt(eventCard.substring(1));
        totalCards += quest.getTotalCardUsed();

        //sponsor draws cards
        playerDrawAdventureCard(p, totalCards);
        System.out.println("drawing:" + totalCards);
        //boolean check
        if(p.getHand().size() > 12){
            sponsorTrim = true;
        }

    }
    //update the shields for quest winner(s) - shield # = quest #
    public boolean endQuestUpdate() {
        //reset the trackers
        setCounts();
        sponsorGetCards();
        for (int i = 0; i < quest.getParticipants().size(); i++) {
            quest.getParticipants().get(i).updateShields(Integer.parseInt(eventCard.substring(1)));
        }

        clearQuest();
        return true;
    }
    public void clearSponsor(){
        quest.clearSponsor();
    }

    //Create a list of winners who has at least 7 shields
    public List<Player> checkWinners() {

        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }
        return winners;
    }

    //each participant at new stage draws one card
    public void newStageDrawCards() {
        for (int i = 0; i < getParticipantSize(); i++) {
            Player p = quest.getParticipants().get(i);
            playerDrawAdventureCard(p, 1);
        }

    }

    //update the player that's being asked
    public void nextRespondingPlayer(Player player) {
        int index = getPlayers().indexOf(player);
        if (index == 3) {
            respondingPlayer = getPlayers().get(0);
        } else {
            setRespondingPlayer(getPlayers().get(index + 1));
        }
    }

    public void switchEventHappened(){
        eventHappened = !eventHappened;
    }

    public boolean getEventHappened(){
        return eventHappened;
    }
    public int trimAmount(Player p) {
        return p.getHand().size() - 12;
    }

    /***messages***/
    public void displayWinners() {
        for (int i = 0; i < checkWinners().size(); i++) {
            System.out.println(checkWinners().get(i).getName());
        }
    }
    public void printPlayerHand(Player p) {
        System.out.println("Current Player Hand: " + p.getHand());
    }
    public void currentPlayerInfo() {
        System.out.println("Current Player: " + currentPlayer.getName());
        printPlayerHand(currentPlayer);
        System.out.println("Current Player Shield: " + currentPlayer.getShields());
    }

    //getters and setters and sets that work together
    public void setRespondingPlayer(Player player) {
        respondingPlayer = player;

    }

    public boolean getSponsorFound() {
        return sponsorFound;
    }
    public boolean getParticipantFound() {
        return participantFound;
    }
    public void updateParticipantCountCheck() {
        participateCount--;
    }
    public void updateSponsorCountCheck() {
        sponsorCount--;

    }
    public void sponsorFound() {
        sponsorFound = true;
    }
    public void participantFound() {
        participantFound = true;
    }
    public void setCounts() {
        participateCount = 3;
        sponsorCount = 3;
        sponsorFound = false;
        participantFound = false;

        challengerCompleted = 0;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public Deck getDeck() {
        return deck;
    }
    public String getEventCard() {
        return eventCard;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Player getRespondingPlayer() {
        return respondingPlayer;
    }
    public Player getChallenger() {
        return quest.getChallenger();
    }
    public int getTotalHandPlayed() {
        return totalHandPlayed;
    }
    //return the value of participants
    public int getParticipantSize() {
        return quest.getParticipants().size();
    }
    public Player getSponsorPlayer() {
        return quest.getSponsor();
    }
    public int getParticipateCount() {
        return participateCount;
    }
    public int getSponsorCount() {
        return sponsorCount;
    }
    public int getTotalStage(){
        return totalStage;
    }
    public boolean getSponsorTrim(){
        return sponsorTrim;
    }
    public String getWinnerNames(){
        String names = "";
        for(int i = 0; i < checkWinners().size(); i++){
            names += checkWinners().get(i).getName();
            names += " ";
        }
        return names;
    }
    public void clearQuest(){
        quest.clearQuest();
    }
    public void resetChallengerTracker(){
        challengerCompleted = 0;
        currentStage = 0;
        challengerSize = 0;
    }
    public void updateChallengerCompleted(){
        challengerCompleted ++;
    }
    public int getCurrentStage(){
        return currentStage;
    }
    public void updateCurrentStage(){
        currentStage++;
    }
    public void clearCurrentStage(){
        currentStage = 0;
    }


    public int getChallengerSize(){
        return challengerSize;
    }
    public int getChallengerCompleted(){
        return challengerCompleted;
    }
    public void resetChallengerCompleted(){
        challengerCompleted = 0;
    }
    public void updateChallengerSize(){
        challengerSize = getParticipantSize();
    }

    //no participants left in the quest
    public boolean noChallenger() {
        return quest.questNoParticipants();
    }


    public void initializeTestOne(){
        setCounts();
        eventHappened = false;
        currentRound = -1;
        questPlayers = new ArrayList<>();
        sponsorPlay = new ArrayList<>();
        totalHandPlayed = 0;
        currentStage = 0;
        totalStage = 0;
        deck = new Deck();


        deck.getAdventureDeck().add("F30");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("B15");
        deck.getAdventureDeck().add("B15");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("F30");
        deck.getAdventureDeck().add("L20");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");

        deck.getEventDeck().add("Q4");

        players.get(0).getHand().clear();
        players.get(1).getHand().clear();
        players.get(2).getHand().clear();
        players.get(3).getHand().clear();

        List<String> hand1 = List.of("F5", "F5", "F15", "F15", "D5", "S10", "S10", "H10", "H10", "B15", "B15", "L20");
        List<String> hand2 = List.of("F5", "F5", "F5", "F10", "F10", "F10", "D5", "H10", "B15", "B15", "L20", "E30");
        List<String> hand3 = List.of("F5", "F5", "F5", "F15", "D5", "S10", "H10", "S10", "H10", "B15", "L20", "S10");
        List<String> hand4 = List.of("F5", "F15", "F15", "F40", "D5", "D5", "S10", "H10", "H10", "B15", "L20", "E30");

        players.get(0).addCards(hand1);
        players.get(1).addCards(hand2);
        players.get(2).addCards(hand3);
        players.get(3).addCards(hand4);

    }
    //Two winner game two winner quest test
    public void initializeTestTwo() {
        setCounts();
        eventHappened = false;
        currentRound = -1;
        questPlayers = new ArrayList<>();
        sponsorPlay = new ArrayList<>();
        totalHandPlayed = 0;
        currentStage = 0;
        totalStage = 0;
        deck = new Deck();

        //adventureDeck() - draw cards
        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F40");
        deck.getAdventureDeck().add("F10");

        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F30");

        deck.getAdventureDeck().add("F30");
        deck.getAdventureDeck().add("F15");

        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F20");

        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F20");
        deck.getAdventureDeck().add("F20");
        deck.getAdventureDeck().add("F20");
        deck.getAdventureDeck().add("F20");
        deck.getAdventureDeck().add("F25");
        deck.getAdventureDeck().add("F25");
        deck.getAdventureDeck().add("F30");

        deck.getAdventureDeck().add("D5");
        deck.getAdventureDeck().add("D5");

        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F15");

        deck.getAdventureDeck().add("F25");
        deck.getAdventureDeck().add("F25");

        deck.getAdventureDeck().add("F20");
        deck.getAdventureDeck().add("F20");
        deck.getAdventureDeck().add("F25");
        deck.getAdventureDeck().add("F30");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("B15");
        deck.getAdventureDeck().add("B15");
        deck.getAdventureDeck().add("L20");

        //eventDeck() "Q4","Q3"
        deck.getEventDeck().add("Q4");
        deck.getEventDeck().add("Q3");

        players.get(0).getHand().clear();
        players.get(1).getHand().clear();
        players.get(2).getHand().clear();
        players.get(3).getHand().clear();

        List<String> hand1 = List.of("F5", "F5", "F10", "F10", "F15", "F15", "D5", "H10", "H10", "B15", "B15", "L20");
        players.get(0).addCards(hand1);
        List<String> hand2 = List.of("F40", "F50", "H10", "H10", "S10", "S10", "S10", "B15", "B15", "L20", "L20", "E30");
        players.get(1).addCards(hand2);
        List<String> hand3 = List.of("F5", "F5", "F5", "F5", "D5", "D5", "D5", "H10", "H10", "H10", "H10", "H10");
        players.get(2).addCards(hand3);
        List<String> hand4 = List.of("F50", "F70", "H10", "H10", "S10", "S10", "S10", "B15", "B15", "L20", "L20", "E30");
        players.get(3).addCards(hand4);

    }

    //1winner_game_with_events
    public void initializeTestThree(){
        setCounts();
        eventHappened = false;
        currentRound = -1;
        questPlayers = new ArrayList<>();
        sponsorPlay = new ArrayList<>();
        totalHandPlayed = 0;
        currentStage = 0;
        totalStage = 0;
        deck = new Deck();

        //adventureDeck() - draw cards
        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F20");

        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F25");

        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F20");

        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F20");

        //quest1
        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F15");

        //prosperity
        deck.getAdventureDeck().add("F25");
        deck.getAdventureDeck().add("F25");

        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("S10");

        deck.getAdventureDeck().add("B15");
        deck.getAdventureDeck().add("F40");

        deck.getAdventureDeck().add("D5");
        deck.getAdventureDeck().add("D5");

        //queens
        deck.getAdventureDeck().add("F30");
        deck.getAdventureDeck().add("F25");
        //quest2
        deck.getAdventureDeck().add("B15");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("F50");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("F40");
        deck.getAdventureDeck().add("F50");

        //sponsor
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("F35");

        deck.getEventDeck().add("Q4");
        deck.getEventDeck().add("Plague");
        deck.getEventDeck().add("Prosperity");
        deck.getEventDeck().add("Queen's Favor");
        deck.getEventDeck().add("Q3");


        players.get(0).getHand().clear();
        players.get(1).getHand().clear();
        players.get(2).getHand().clear();
        players.get(3).getHand().clear();

        List<String> hand1 = List.of("F5","F5","F10","F10","F15","F15","F20","F20","D5","D5","D5","D5");
        players.get(0).addCards(hand1);
        List<String> hand2 = List.of("F25","F30","H10","H10","S10","S10","S10","B15","B15","L20","L20","E30");
        players.get(1).addCards(hand2);
        List<String> hand3 = List.of("F25","F30","H10","H10","S10","S10","S10","B15","B15","L20","L20","E30");
        players.get(2).addCards(hand3);
        List<String> hand4 = List.of("F25","F30","F70","H10","H10","S10","S10","S10","B15","B15","L20","L20");
        players.get(3).addCards(hand4);

    }


    //0_winner_quest
    public void initializeTestFour(){
        setCounts();
        eventHappened = false;
        currentRound = -1;
        questPlayers = new ArrayList<>();
        sponsorPlay = new ArrayList<>();
        totalHandPlayed = 0;
        currentStage = 0;
        totalStage = 0;
        deck = new Deck();

        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("F10");

        //sponsor draws 14
        deck.getAdventureDeck().add("F5");
        deck.getAdventureDeck().add("F10");
        deck.getAdventureDeck().add("F15");
        deck.getAdventureDeck().add("D5");
        deck.getAdventureDeck().add("D5");
        deck.getAdventureDeck().add("D5");
        deck.getAdventureDeck().add("D5");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("H10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");
        deck.getAdventureDeck().add("S10");

        deck.getEventDeck().add("Q2");

        players.get(0).getHand().clear();
        players.get(1).getHand().clear();
        players.get(2).getHand().clear();
        players.get(3).getHand().clear();

        List<String> hand1 = List.of("F50","F70","D5","D5","H10","H10","S10","S10","B15","B15","L20","L20");
        players.get(0).addCards(hand1);
        List<String> hand2 = List.of("F5","F5","F10","F15","F15","F20","F20","F25","F30","F30","F40","E30");
        players.get(1).addCards(hand2);
        List<String> hand3 = List.of("F5","F5","F10","F15","F15","F20","F20","F25","F25","F30","F40","L20");
        players.get(2).addCards(hand3);
        List<String> hand4 = List.of("F5","F5","F10","F15","F15","F20","F20","F25","F25","F30","F50","E30");
        players.get(3).addCards(hand4);


    }
}