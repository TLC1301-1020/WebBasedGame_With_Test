package org.example;

public class Main {
    public static void main(String[] args) {
        // Initialize the game
        Game game = new Game();

        // Initialize the menu with the game instance
        Menu menu = new Menu(game);
        menu.displayMainMenu();
    }
}
