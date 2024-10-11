package org.example;

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

}
