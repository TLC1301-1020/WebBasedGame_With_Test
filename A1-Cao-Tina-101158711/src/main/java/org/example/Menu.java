package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        while (true) {
            updateRound();
            System.out.println("Currently " + currentplayer + "'s turn");
            System.out.println("Hand: " + currentplayer.getHand());
            String event = game.getDeck().drawEventCard();

            System.out.println("Event card drawn: " + event);
            if(event.equals("Plague")) {
                System.out.println("Plague! You loses two shields.");
                currentplayer.updateShields(-2);
            }else if(event.equals("Queen's favor")) {

            }
        }
    }

    //update round player in order
    public void updateRound() {
        if (currentRound == -1) {
            currentRound = 0;
        } else {
            currentRound = (currentRound + 1) % 4;
        }
        currentplayer = game.getPlayers().get(currentRound);
    }

    private void printRoundPlayer(){
        System.out.println("Current round player: " + currentplayer);
    }

    //clear when return hit
    public void returnKeyClicked(){
        for(int i = 0; i < 50; ++i){
            System.out.println('\n');
        }
    }


    public int getCurrentRound() {
        return currentRound;
    }

    public Player getCurrentplayer(){
        return currentplayer;
    }

    public void trimHand(){
        System.out.println("Enter the card to be trimmed: ");
        String trim = scanner.next();

        boolean trimmed = game.TrimCards(currentplayer,trim);
        if(trimmed){
            System.out.println("Card trimmed.");
        }else{
            System.out.println("Check your input and retry.");
        }
    }

}

