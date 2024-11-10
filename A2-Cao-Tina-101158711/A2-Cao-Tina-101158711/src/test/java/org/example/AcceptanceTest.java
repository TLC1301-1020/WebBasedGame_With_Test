package org.example;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AcceptanceTest {
    private Menu menu;
    private Game game;
    private Deck mockDeck;
    private Scanner mockScanner;

    @BeforeEach
    public void setup() {
        mockDeck = mock(Deck.class);
        mockScanner = mock(Scanner.class);

        game = new Game() {
            @Override
            public Deck getDeck() {
                return mockDeck;
            }
        };


        List<String> hand1 = Arrays.asList("F5", "F5", "F15", "F15", "D5", "S10", "S10", "H10", "H10", "B15", "B15", "L20");
        List<String> hand2 = Arrays.asList("F5", "F5", "F5", "F10", "F10", "F10", "D5", "H10", "B15", "B15", "L20", "E30");
        List<String> hand3 = Arrays.asList("F5", "F5", "F5", "F15", "D5", "S10", "H10", "S10", "H10", "B15", "L20", "S10");
        List<String> hand4 = Arrays.asList("F5", "F15", "F15", "F40", "D5", "D5", "S10", "H10", "H10", "B15", "L20", "E30");
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).getHand().clear();
        }

        game.getPlayers().get(0).addCards(hand1);
        game.getPlayers().get(1).addCards(hand2);
        game.getPlayers().get(2).addCards(hand3);
        game.getPlayers().get(3).addCards(hand4);

        menu = new Menu(game);
        mockScanner = mock(Scanner.class);
        menu.setScanner(mockScanner);
        //draw card
        when(mockDeck.drawEventCard()).thenReturn("Q4");

        //participate and sponsor input
        when(mockScanner.nextInt()).thenReturn(2)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(-1);

        //input for set up stages
        when(mockScanner.nextLine())
                .thenReturn("F10")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("B15")
                .thenReturn("quit")

                .thenReturn("F10")
                .thenReturn("B15")
                .thenReturn("H10")
                .thenReturn("quit")

                .thenReturn("F5")
                .thenReturn("L20")
                .thenReturn("E30")
                .thenReturn("quit")

                //player 1 stage1
                .thenReturn("F5")  //player 1 discards
                .thenReturn("no")   //no opt out
                .thenReturn("D5")   //play hand
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")


                //player 3 stage 1
                .thenReturn("F5")
                .thenReturn("no")
                .thenReturn("S10")
                .thenReturn("D5")
                .thenReturn("quit")
                .thenReturn("\n")

                //player 4 stage 1
                .thenReturn("F5")
                .thenReturn("no")
                .thenReturn("D5")
                .thenReturn("H10")
                .thenReturn("quit")
                .thenReturn("\n")

                //p1 stage 2
                .thenReturn("no")
                .thenReturn("H10")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")
                //p3 stage 2
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")
                //p4 stage 2
                .thenReturn("no")
                .thenReturn("H10")
                .thenReturn("B15")
                .thenReturn("quit")
                .thenReturn("\n")
                //p3 stage 3
                .thenReturn("no")
                .thenReturn("L20")
                .thenReturn("H10")
                .thenReturn("S10")
                .thenReturn("quit")
                .thenReturn("\n")
                //p4 stage 3
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("S10")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")
                //p3 stage 4
                .thenReturn("no")
                .thenReturn("B15")
                .thenReturn("H10")
                .thenReturn("L20")
                .thenReturn("quit")
                .thenReturn("\n")
                //p4 stage 4
                .thenReturn("no")
                .thenReturn("D15")
                .thenReturn("S10")
                .thenReturn("L20")

                .thenReturn("E30")
                .thenReturn("quit")
                .thenReturn("\n")

                //ended player 2 trim
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("S10")
                .thenReturn("\n");






        //participants draw cards
        when(mockDeck.drawAdventureCard())
                .thenReturn("F30")
                .thenReturn("S10")
                .thenReturn("B15")
                .thenReturn("F10")
                .thenReturn("L20")
                .thenReturn("L20")
                .thenReturn("B15")
                .thenReturn("S10")
                .thenReturn("F30")
                .thenReturn("L20")
                .thenReturn("S10");



    }
    @Test
    public void testSponsorshipFlow() {
        menu.displayMainMenu();
        Player player1 = game.getPlayers().get(0); // Assuming players are in a list
        assertEquals(0, player1.getShields(), "Player 1 should have no shields.");
        assertTrue(player1.getHand().containsAll(Arrays.asList("F5", "F10", "F15", "F15", "F30", "H10", "B15", "B15", "L20")));


        Player player2 = game.getPlayers().get(1);
        assertEquals(12, player2.getHand().size(), "Player 2 should have 12 cards in hand.");


        Player player3 = game.getPlayers().get(2);
        assertEquals(0, player3.getShields(), "Player 3 should have no shields.");
        System.out.println(player3.getHand());
        assertTrue(player3.getHand().containsAll(Arrays.asList("F5", "F5", "F15", "F30", "S10")));


        Player player4 = game.getPlayers().get(3);
        assertEquals(4, player4.getShields(), "Player 4 should have gained 4 shields.");
        assertTrue(player4.getHand().containsAll(Arrays.asList("F15", "F15", "F40", "L20")));



    }

}