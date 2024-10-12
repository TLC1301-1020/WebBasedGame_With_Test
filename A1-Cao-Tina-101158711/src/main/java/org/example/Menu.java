package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Game game;
    private Scanner scanner;
    private int currentRound;
    private Player currentplayer;
    public Menu(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
        currentRound = -1;
    }

    public void displayMainMenu() {
        while (game.checkWinners().isEmpty()) {
            updateRound();
            //check if current player needs to trim hand
            while(trimNeeded()) {
                trimHand();
            }

            //game starts
            newRoundMessage();
            String event = game.getDeck().drawEventCard();
            System.out.println("Event card drawn: " + event);

            if(event.equals("Plague")) {
                plagueCard();
            }else if(event.equals("Queen's Favor")) {
                QueensFavor();
            }else if(event.equals("Prosperity")){
                Prosperity();
            }else {
                //TODO: quest logic
            }

            System.out.println("Your round has ended, please hit RETURN to leave the Hot seat");
            String input = scanner.nextLine();
            //return key
            if (input.isEmpty()){
                returnKeyClicked();
            }
        //while end
        }
        //game terminates when at least one winner found
        System.out.println("Game ended! The winner(s): ");
        for(int i = 0; i < game.checkWinners().size(); i++){
            System.out.print(game.checkWinners().get(i).getName() + "\t");
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

    }

    public boolean trimNeeded(){
        return currentplayer.getHand().size() >= 13;
    }

    public void trimHand(){
        System.out.println(currentplayer.getName());
        System.out.println("Hand: " + currentplayer.getHand());
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
    public void plagueCard(){

    }
    public void QueensFavor(){

    }
    public void Prosperity(){

    }
    public void newRoundMessage(){
        System.out.println("Currently " + currentplayer.getName() + "'s turn");
        System.out.println("Hand: " + currentplayer.getHand());
    }

    public Player getCurrentplayer(){
        return currentplayer;
    }

    //clear screen
    public void returnKeyClicked(){
        for(int i = 0; i < 20; ++i){
            System.out.println();
        }
    }




}

