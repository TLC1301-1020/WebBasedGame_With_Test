package org.example;

import java.util.*;


public class Menu {
    private Game game;
    private Scanner scanner;
    private boolean questStatus;

    public Menu(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {

        while (game.checkWinners().isEmpty()) {

            game.nextRound();

            //new round starts
            //display current player info
            game.currentPlayerInfo();

            //check if current player needs to trim hand
            while (game.trimNeeded(game.getCurrentPlayer())) {

                System.out.println("Enter the card to be trimmed: ");
                String input = scanner.next();
                game.TrimCards(game.getCurrentPlayer(),input);
            }

            game.drawEventCard();

            System.out.println("Event card drawn: " + game.getEventCard());

            if (game.getEventCard().equals("Plague")) {
                game.Plague();

            } else if (game.getEventCard().equals("Queen's Favor")) {
                game.QueensFavor();

                //check if current player needs to trim hand
                while (game.trimNeeded(game.getCurrentPlayer())) {

                    game.printPlayerHand(game.getCurrentPlayer());
                    System.out.println("Enter the card to be trimmed: ");
                    String input = scanner.next();
                    game.TrimCards(game.getCurrentPlayer(),input);
                }

            } else if (game.getEventCard().equals("Prosperity")) {
                game.Prosperity();

                //check if current player needs to trim hand
                while (game.trimNeeded(game.getCurrentPlayer())) {
                    game.printPlayerHand(game.getCurrentPlayer());
                    System.out.println("Enter the card to be trimmed: ");
                    String input = scanner.next();
                    game.TrimCards(game.getCurrentPlayer(),input);
                }

            } else if (game.getEventCard().contains("Q")) {
                //quest is built, played, and players info updated
                if(questBuild()){
                    stageFights();
                    if (game.endQuestUpdate()) {
                        System.out.println("Shields are updated for the winners of the quest.");
                    }else{
                        System.out.println("No winners of the quest!");
                    }

                    //check if sponsor player needs to trim hand
                    while (game.trimNeeded(game.getSponsorPlayer())) {
                        game.printPlayerHand(game.getSponsorPlayer());
                        System.out.println("Enter the card to be trimmed: ");
                        String input = scanner.next();
                        game.TrimCards(game.getCurrentPlayer(),input);
                    }
                }
            }


            //game terminates when at least one winner found
            if (!game.checkWinners().isEmpty()) {
                game.displayWinners();
            }

            //player turn ends, return to leave hot seat
            System.out.println("Your round has ended, enter anything to leave the Hot seat");
            String input = scanner.next();
            //return key
            if (input.isEmpty()) {
                returnKeyClicked();
            }
        }
        System.out.println("Game ended!");

    }


    //finding sponsor and participants
    //return true if both found, false otherwise
    public boolean questBuild() {
        int questLevel = Integer.parseInt(game.getEventCard().substring(1));
        int count = 4; //count down iteration
        int choice = 0; //user choice

        //another variable storing the player so we don't lose track of the game
        game.setRespondingPlayer(game.getCurrentPlayer());

        //iterate to find sponsor
        while (count != 0) {

            if (game.getRespondingPlayer().foeCardAmount() < questLevel) {
                System.out.println("Sorry! You don't have enough foe cards to build the quest.");

            } else {
                System.out.println(game.getRespondingPlayer().getName() + ", would you be the sponsor for the quest?");
                System.out.println("1 for yes, 2 for no:");
                choice = scanner.nextInt();
            }

            //found sponsor if choice = 1
            if (choice == 1) {
                game.sponsorFound();
                game.addQuestPlayers(); //add the sponsor to the list
                questStatus = true;
                game.nextRespondingPlayer(game.getRespondingPlayer());
                break;
            } else {
                game.updateSponsorCountCheck();
                game.nextRespondingPlayer(game.getRespondingPlayer());
                count--;
            }
        }

        //sponsor found, find participants
        if (questStatus) {

            questStatus = false;
            int index = game.getPlayers().indexOf(game.getRespondingPlayer());

            //loop to ask every player (other than sponsor) to participate
            for (int i = 0; i < 3; i++) {
                index++;
                if (index == 4) {
                    index = 0;
                }

                Player p = game.getRespondingPlayer();
                System.out.println(p.getName() + ": ");
                System.out.println("Would you like to participate in the quest?");
                System.out.println("1 for yes, 2 for no");

                choice = scanner.nextInt();
                if (choice == 1) {
                    questStatus = true;
                    game.addQuestPlayers();
                    game.updateParticipantCountCheck();
                }

                game.nextRespondingPlayer(p);
            }

            //sponsor and participants are found
            if (questStatus) {
                game.createQuest();
                int stage = Integer.parseInt(game.getEventCard().substring(1));

                //sponsor builds stages
                for (int i = 1; i <= stage; i++) {
                    System.out.println(game.getSponsorPlayer().getHand());
                    System.out.println("Enter one foe card to build stage " + i);
                    String input = scanner.next();
                    game.addBuildingCards(input);
                    System.out.println(game.getSponsorPlayer().getHand());
                    System.out.println("Enter the weapon cards to build stage " + i + ", quit to stop");
                    input = scanner.next();
                    while (!input.equals("quit")) {
                        game.addBuildingCards(input);
                        System.out.println(game.getSponsorPlayer().getHand());
                        System.out.println("Enter the weapon cards to build stage " + i + ", quit to stop");
                        input = scanner.next();
                    }
                    game.createStage(i - 1);
                }
            }
        }
        return questStatus;
    }

    //players play at the stages
    public void stageFights() {
        int count = Integer.parseInt(game.getEventCard().substring(1));

        //each stage
        for (int i = 1; i <= count; i++) {

            //no more participants in the quest
            if(game.noChallenger()){
                break;
            }

            //each remaining participants draw one card
            game.newStageDrawCards();

            int countDown = game.getParticipantSize();

            //each challenger
            while(countDown > 0){
                Player temp = game.getChallenger();
                System.out.println("Current Player: " + temp.getName());
                System.out.println(game.getChallenger().getHand());
                System.out.println("Enter the cards to be played in stage " + i + ", quit to stop.");
                String input = scanner.next();

                while (!input.equals("quit")) {
                    game.participantStageCards(input);
                    input = scanner.next();
                }
                System.out.println("Total played: " + game.getTotalHandPlayed());

                //if challenger failed the current stage
                if (!game.participantPassed(i - 1)) {
                    System.out.println("You failed to complete the stage.");
                }
                //one challenger at the stage is done
                countDown--;
            }
        }

    }

    //mock of flashing
    public void returnKeyClicked() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

    }



}