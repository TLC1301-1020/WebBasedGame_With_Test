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

