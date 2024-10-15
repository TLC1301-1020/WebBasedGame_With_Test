package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Game game;
    private Scanner scanner;
    private int currentRound;
    private Player currentplayer;
    private Player sponsorplayer;
    private List<Player> participants;
    private Quest quest;
    private int cardsUsed;
    private String foeCardAtStage;

    public Menu(Game game) {
        this.participants = new ArrayList<>();
        this.game = game;
        this.scanner = new Scanner(System.in);
        currentRound = -1;
        cardsUsed = 0;
        sponsorplayer = currentplayer;
    }

    public void displayMainMenu() {
        while (game.checkWinners().isEmpty()) {
            updateRound();
            //check if current player needs to trim hand
            while (trimNeeded()) {
                trimHand(currentplayer);
            }
            //game starts
            newRoundMessage();
            String event = game.getDeck().drawEventCard();

            System.out.println("Event card drawn: " + event);
            if (event.equals("Plague")) {
                plagueCard();

            } else if (event.equals("Queen's Favor")) {
                QueensFavor();

            } else if (event.equals("Prosperity")) {
                Prosperity();
                while(trimNeeded()) {
                    trimHand(currentplayer);
                }
            } else {
                //quest card, try to find sponsor, no sponsors
                if(!findingSponsor(event)){
                    System.out.println("No sponsor for the quest, game continues.");
                    System.out.println("Your round has ended, please hit RETURN to leave the Hot seat");
                    String input = scanner.nextLine();
                    //return key
                    if (input.isEmpty()) {
                        returnKeyClicked();
                    }

                    continue;
                //sponsor found, find participants
                }else {
                    findParticipants();
                    if (participantIsEmpty()) {
                        System.out.println("No participants, game continues.");
                        System.out.println("Your round has ended, please hit RETURN to leave the Hot seat");
                        String input = scanner.nextLine();
                        //return key
                        if (input.isEmpty()) {
                            returnKeyClicked();
                        }
                        continue;
                    } else {
                        startQuest(event, sponsorplayer, participants);
                        if (stageFight()) {
                            //update shields
                            System.out.println("Quest is completed!");
                            participantsAddShields(event);

                            participantDrawAdventureCard(sponsorplayer,cardsUsed + quest.getTotalLevel());

                            while (trimNeeded()) {
                                trimHand(sponsorplayer);
                            }
                        } else {
                            System.out.println("Quest failed!");
                        }
                    }
                }
                game.getDeck().discardEventCard(event);
                if (!game.checkWinners().isEmpty()) {
                    break;
                }
            }
            System.out.println("Your round has ended, please hit RETURN to leave the Hot seat");
            String input = scanner.nextLine();
            //return key
            if (input.isEmpty()) {
                returnKeyClicked();
            }
        }
        //game terminates when at least one winner found
        if(!game.checkWinners().isEmpty()){
            printWinner();
        }
    }
    public boolean participantIsEmpty(){
        return participants.isEmpty();
    }
    public void participantsAddShields(String event){
        for(int i = 0; i < participants.size(); i++){
            participants.get(i).updateShields(Integer.parseInt(event.substring(1)));
        }
        System.out.println("Shields of winner(s) of the quest is updated, each winner gets " + Integer.parseInt(event.substring(1)) + " shields");
    }

    public boolean stageFight(){
        int currentStageIndex = 0;

        while(!participantIsEmpty() && currentStageIndex <= quest.getStagesSize()) {

            if(currentStageIndex > 0) {
                System.out.println("Stage " + (currentStageIndex) + " completed.");
            }
            loopDrawAdventureCard();

            System.out.println("Eligible participant at this stage: ");
            for(int i = 0; i < participants.size(); i++){
                System.out.println(participants.get(i).getName());
            }
            System.out.println("\n");

            for (int i = 0; i < participants.size(); i++) {
                Player currentParticipant = participants.get(i);
                System.out.println("Currently player at this stage: " + currentParticipant.getName());
                while(currentParticipant.getHand().size() > 12){
                    trimHand(currentParticipant);
                }

                int participantCards = participantBuilds(currentStageIndex, currentParticipant);

                if(participantCards == -1){
                    System.out.println("Participant left the quest");
                }else if(!participantPassed(participantCards,quest.getStageTotalValue(currentStageIndex))) {
                    System.out.println("Sorry, your value was smaller than the stage value.");
                    participants.remove(currentParticipant);
                    i--;
                }
                System.out.println("Your round has ended, please hit RETURN to leave");
                String input = scanner.nextLine();
                //return key
                if (input.isEmpty()) {
                    returnKeyClicked();
                }
            }
            currentStageIndex++;
        }
        if(participants.isEmpty())
            return false;
        return true;
    }

    public boolean participantPassed(int playerValue, int stageTotal) {
        return playerValue >= stageTotal;
    }

    public int participantBuilds(int currentStageIndex, Player currentParticipant){
        int totalPlayed = 0;
        List<String> cardPlayed = new ArrayList<>();
        List<Character> types = new ArrayList<>();
        String input;
        System.out.println("Would you like to quit the quest? Enter quit to quit");
        input = scanner.next();
        if (input.equals("quit")) {
            participants.remove(currentParticipant);
            return -1;
        }
        System.out.println("Enter the card to play in " + (currentStageIndex+1) + " stage, enter quit to stop.");
        System.out.println(currentParticipant.getHand());
        input = scanner.next();

        while (!input.equals("quit")){
            if((types.contains(input.charAt(0))) || (input.contains("F")) || (!currentParticipant.getHand().contains(input))){
                System.out.println("The input card isn't available. Please try again");
            } else {
                game.getDeck().discardAdventureCard(input);
                types.add(input.charAt(0));
                totalPlayed += Integer.parseInt(input.substring(1));
                cardPlayed.add(input);
                currentParticipant.getHand().remove(input);
                System.out.println("Card played in this stage " + cardPlayed + " please continue (quit to stop):");
            }
            System.out.println(currentParticipant.getHand());
            input = scanner.next();
        }
        System.out.println("Card played in this stage " + cardPlayed + " please continue (quit to stop):");
        System.out.println("Total played by you: " + totalPlayed);
        return totalPlayed;
    }

    public void participantDrawAdventureCard(Player sponsorplayer,int times){
        for(int i = 1; i <= times; i++){
            sponsorplayer.getHand().add(game.getDeck().drawAdventureCard());
            sponsorplayer.getSortedHand();
        }
    }

    public void loopDrawAdventureCard(){
        System.out.println("Each participant draws a card");
        for (int i = 0; i < participants.size(); i++) {
            participantDrawAdventureCard(participants.get(i),1);
        }
    }


    public void updateRound() {
        if (game.getPlayers().isEmpty()) {
            throw new IllegalStateException("No players in the game.");
        }
        if (currentRound == -1) {
            currentRound = 0;
        } else {
            currentRound = (currentRound + 1) % game.getPlayers().size();
        }
        currentplayer = game.getPlayers().get(currentRound);
    }


    public boolean trimNeeded(){
        return currentplayer.getHand().size() > 12;
    }

    public void trimHand(Player currentplayer){
        System.out.println(currentplayer.getName() + " Hand: " + currentplayer.getHand());
        System.out.println("Enter the card to be trimmed: ");
        String trim = scanner.next();
        boolean trimmed = game.TrimCards(currentplayer,trim);
        if(trimmed){
            System.out.println("Card trimmed.");
        }else{
            System.out.println("Check your input and retry.");
        }
    }

    //display messages
    public void newRoundMessage(){
        System.out.println("Currently " + currentplayer.getName() + "'s turn");
        System.out.println("Hand: " + currentplayer.getHand());
    }

    public void plagueCard(){
        System.out.println("Plague! You loses two shields.");
        currentplayer.updateShields(-2);
    }
    public void QueensFavor(){
        System.out.println("Queen's Favor! Draw two cards.");
        ArrayList<String> cards = new ArrayList<>();
        cards.add(game.getDeck().drawAdventureCard());
        cards.add(game.getDeck().drawAdventureCard());
        currentplayer.addCards(cards);
        currentplayer.getSortedHand();
        while(trimNeeded()) {
            trimHand(currentplayer);
        }
    }
    public void Prosperity(){
        System.out.println("Prosperity! Everyone draws two cards.");
        for(int i = 0; i < game.getPlayers().size(); i++){
            ArrayList<String> cards = new ArrayList<>();
            cards.add(game.getDeck().drawAdventureCard());
            cards.add(game.getDeck().drawAdventureCard());
            game.getPlayers().get(i).addCards(cards);
            game.getPlayers().get(i).getSortedHand();
        }
    }
    public void printWinner(){
        System.out.println("Game ended! The winner(s): ");
        for (int i = 0; i < game.checkWinners().size(); i++) {
            System.out.print(game.checkWinners().get(i).getName() + "\t");
        }
    }

    //Quest related

    //check if the player has enough foe cards
    public boolean enoughFoeCard(int levels){
        return currentplayer.countFoeCards() >= levels;
    }

    public boolean findingSponsor(String event) {
        int questLevel = Integer.parseInt(event.substring(1));
        int count = 4; //count down iteration
        int choice = 0; //user choice

        sponsorplayer = currentplayer;

        System.out.println("Quest!");
        while (count != 0) {
            System.out.println(getSponsorplayer().getName() + ": ");
            if (!enoughFoeCard(questLevel)) {
                System.out.println("Sorry! You don't have enough foe cards to build the quest.");
            } else {
                System.out.println("Would you be the sponsor for the quest?");
                System.out.println("1 for yes, 2 for no:");
                choice = scanner.nextInt();
            }
            if (choice == 1) {
                return true;
            } else {
                nextSponsor(sponsorplayer);
                count--;
            }
        }
        System.out.println("No sponsor found!");
        return false;
    }

    //iterate for asking next player to be the sponsor
    public void nextSponsor(Player currentSponsor){
        int index = game.getPlayers().indexOf(currentSponsor);
        if(index == 3){
            sponsorplayer = game.getPlayers().getFirst();
        }else if(index == -1) {
            sponsorplayer = game.getPlayers().get(1);
        }else{
            sponsorplayer = game.getPlayers().get(index+1);
        }
    }

    public List<Player> findParticipants(){
        if(!participants.isEmpty()){
            participants.clear();
        }
        Player current;
        int index = game.getPlayers().indexOf(sponsorplayer);

        //loop to ask every player (other than sponsor) to participate
        for(int i = 0; i < 3; i++){
            index++;
            if(index == 4){
                index = 0;
            }
            current = game.getPlayers().get(index);
            System.out.println(current.getName() + ":");
            System.out.println("Would you like to participate in the quest?");
            System.out.println("1 for yes, 2 for no");
            int choice = scanner.nextInt();
            if(choice == 1) {
                participants.add(current);
            }
        }
        returnKeyClicked();
        return participants;
    }

    public void startQuest(String event,Player sponsorplayer, List<Player> participants){
        quest = new Quest(event,sponsorplayer,participants);
        int count = 1;
        cardsUsed = 0;
        while (count <= Integer.parseInt(event.substring(1))) {
            String stageFoe = buildFoeCard(count);
            List<String> weaponCards = buildWeaponCards(count);
            boolean added = quest.initializeStages(count - 1, stageFoe, weaponCards);

            if (!added) {
                System.out.println("Retry. Current stage has to have a greater value than previous stage");
                sponsorplayer.getHand().add(stageFoe);
                game.getDeck().removeAdventureCardFromDiscarded(stageFoe);
                if(!weaponCards.isEmpty()) {
                    sponsorplayer.addCards(weaponCards);
                }
                sponsorplayer.getSortedHand();
            }else{
                count++;
            }
        }
        System.out.println("The quest has been built, hit return to leave the hot seat");
        returnKeyClicked();
    }

    public String buildFoeCard(int level) {
        System.out.println(sponsorplayer.getName() + ": " + sponsorplayer.getHand());
        System.out.println("Enter 1 foe card to be used in stage " + level + " quit to stop");
        String card = scanner.next();

        while (!card.contains("F") || card.length() > 3 || !sponsorplayer.getHand().contains(card)) {
            System.out.println("Retry, stage cannot be empty, or only one foe card can be used at this stage.");
            card = scanner.next();
        }
        foeCardAtStage = card;
        sponsorplayer.getHand().remove(card);
        game.getDeck().discardEventCard(card);
        cardsUsed++;
        return card;
    }

    public List<String> buildWeaponCards(int level){
        List<String> weaponCards = new ArrayList<>();
        System.out.println("Foe card used in this stage: " + foeCardAtStage);
        System.out.println("Weapon card used in this stage: " + weaponCards);

        System.out.println(sponsorplayer.getHand());
        System.out.println("Enter the weapon card to be used in stage " + level + " quit to stop");
        String card = scanner.next();
        List<Character> types = new ArrayList<>();

        while(!card.equals("quit")) {

            if (!sponsorplayer.getHand().contains(card) || (types.contains(card.charAt(0)))) {
                System.out.println("Retry, you do not have this card or you are using the same type.");
            } else if(card.contains("F")){
                System.out.println("Retry, you cannot build another foe card.");
            } else {
                types.add(card.charAt(0));
                sponsorplayer.getHand().remove(card);
                game.getDeck().discardEventCard(card);
                weaponCards.add(card);
                cardsUsed++;
            }
            card = scanner.next();
        }
        return weaponCards;
    }

    //getters
    public Player getCurrentplayer(){
        return currentplayer;
    }
    public Player getSponsorplayer(){
        return sponsorplayer;
    }

    public void returnKeyClicked(){
        for(int i = 0; i < 20; ++i){
            System.out.println();
        }
    }
    //setters
    public void setCurrentPlayer(Player player){
        currentplayer = player;
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }
    public void setSponsorplayer(Player player){
        sponsorplayer = player;
    }
}

