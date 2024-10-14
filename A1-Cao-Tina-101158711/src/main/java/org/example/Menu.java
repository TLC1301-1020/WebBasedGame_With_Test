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

    public Menu(Game game) {
        this.participants = new ArrayList<>();
        this.game = game;
        this.scanner = new Scanner(System.in);
        currentRound = -1;
        cardsUsed = 0;
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
            } else {
                if(!findingSponsor(event)){
                    System.out.println("No sponsor for the quest, game continues.");
                    break;
                }else{
                    findParticipants();
                }

                //there's participant(s)
                if(!participants.isEmpty()){
                    //quest play
                    startQuest(event,sponsorplayer, participants);
                    if(stageFight()){
                        //update shields
                        participantsAddShields();
                    }else{
                        System.out.println("Quest failed!");
                    }
                }
                game.getDeck().discardEventCard(event);
                if (!game.checkWinners().isEmpty()) {
                    break;
                }else {
                    //sponsor update cards
                    for (int i = 0; i < cardsUsed + quest.getTotalLevel(); i++) {
                        participantDrawAdventureCard(sponsorplayer);
                    }
                    while(trimNeeded()){
                        trimHand(sponsorplayer);
                    }
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
        printWinner();
    }

    public boolean participantIsEmpty(){
        return participants.isEmpty();
    }
    public void participantsAddShields(){
        for(int i = 0; i < participants.size(); i++){
            participants.get(i).updateShields(+2);

        }
        System.out.println("Shields of winner(s) of the quest is updated");
    }

    public boolean stageFight(){
        int currentStageIndex = 0;

        while(!participantIsEmpty() && currentStageIndex <= quest.getStagesSize()) {

            if(currentStageIndex > 0) {
                System.out.println("Stage " + (currentStageIndex) + " completed.");
            }
            loopDrawAdventureCard();

            for (int i = 0; i < participants.size(); i++) {
                Player currentParticipant = participants.get(i);
                System.out.println(currentParticipant.getName());
                while(currentParticipant.getHand().size()>12){
                    trimHand(currentParticipant);
                }
                int participantCards = participantBuilds(currentStageIndex, currentParticipant);

                if(!participantPassed(participantCards,quest.getStageTotalValue(currentStageIndex))){
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
        return playerValue > stageTotal;
    }

    public int participantBuilds(int currentStageIndex, Player currentParticipant){
        int totalPlayed = 0;
        List<Character> types = new ArrayList<>();

        System.out.println("Enter the card to play in " + (currentStageIndex+1) + " stage, enter quit to stop.");
        System.out.println(currentParticipant.getHand());
        String input = scanner.next();

        while (!input.equals("quit")){
            if((types.contains(input.charAt(0))) || (input.contains("F")) || (!currentParticipant.getHand().contains(input))){
                System.out.println("The input card isn't available. Please try again");
            } else {
                game.getDeck().discardAdventureCard(input);
                types.add(input.charAt(0));
                totalPlayed += Integer.parseInt(input.substring(1));
                currentParticipant.getHand().remove(input);
                System.out.println("Card played, please continue:");
            }
            System.out.println(currentParticipant.getHand());
            input = scanner.next();
        }
        System.out.println("Total played by you: " + totalPlayed);
        return totalPlayed;
    }

    public void participantDrawAdventureCard(Player sponsorplayer){
        sponsorplayer.getHand().add(game.getDeck().drawAdventureCard());
        sponsorplayer.getSortedHand();
    }

    public void loopDrawAdventureCard(){
        System.out.println("Each participant draws a card");
        for (int i = 0; i < participants.size(); i++) {
            participantDrawAdventureCard(participants.get(i));
        }
    }

    //update round player in order
    public void updateRound() {
        if (currentRound == -1) {
            currentRound = 0;
        } else if (currentRound == 3) {
            currentplayer = game.getPlayers().get(currentRound);
            currentRound = 0;
        }else {
            currentRound = (currentRound + 1) % 4;
            }
        currentplayer = game.getPlayers().get(currentRound);
        sponsorplayer = currentplayer;
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
            while(trimNeeded()) {
                trimHand(currentplayer);
            }
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
                nextSponsor();
                count--;
            }
        }
        System.out.println("No sponsor found!");
        return false;
    }

    //iteration for sponsor finding
    public void nextSponsor(){
        int index = game.getPlayers().indexOf(sponsorplayer);

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
        return participants;
    }
    public void startQuest(String event,Player sponsorplayer, List<Player> participants){
        quest = new Quest(event,sponsorplayer,participants);
        int count = 1;
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
        System.out.println(sponsorplayer.getHand());
        System.out.println("Enter 1 foe card to be used in stage" + level);
        String card = scanner.next();

        while (!card.contains("F") || card.length() > 3 || !sponsorplayer.getHand().contains(card)) {
            System.out.println("Retry, only one foe card in your hand can be used.");
            card = scanner.next();
        }
        sponsorplayer.getHand().remove(card);
        game.getDeck().discardEventCard(card);
        cardsUsed++;
        return card;
    }

    public List<String> buildWeaponCards(int level){
        List<String> weaponCards = new ArrayList<>();
        System.out.println(sponsorplayer.getHand());
        System.out.println("Enter the weapon card to be used in stage " + level);
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

    public int getCardUsed(){
        return cardsUsed;
    }

    public Quest getQuest(){
        return quest;
    }
    //setters
    public void setCurrentPlayer(Player player){
        currentplayer = player;
        sponsorplayer = currentplayer;
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public void setCardsUsed(int value){
        cardsUsed = value;
    }


}

