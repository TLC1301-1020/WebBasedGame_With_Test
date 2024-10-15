package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    @DisplayName("R3 - Check player order")
    public void testUpdateRound(){
        Game game = new Game();
        Menu menu = new Menu(game);

        menu.updateRound();
        Assertions.assertEquals("Player1", menu.getCurrentplayer().getName());
        menu.updateRound();
        Assertions.assertEquals("Player2", menu.getCurrentplayer().getName());
        menu.updateRound();
        Assertions.assertEquals("Player3", menu.getCurrentplayer().getName());
        menu.updateRound();
        Assertions.assertEquals("Player4", menu.getCurrentplayer().getName());
        //iterate back to player1
        menu.updateRound();
        Assertions.assertEquals("Player1", menu.getCurrentplayer().getName());
    }

    @Test
    @DisplayName("R5.1 - Game affected by Plague")
    public void testPlagueDrawn(){
        Game game = new Game();
        Menu menu = new Menu(game);
        Player player = game.getPlayers().getFirst();

        player.updateShields(3);
        menu.setCurrentPlayer(player);
        menu.plagueCard();
        Assertions.assertEquals(1,player.getShields(),"Player should have one shield left");
    }

    @Test
    @DisplayName("R5.2 - Game affected by Queen's Favor cards")
    public void testQueenFavorDrawn(){
        Game game = new Game();
        Menu menu = new Menu(game);
        Player player = game.getPlayers().getFirst();

        player.getHand().clear();
        menu.setCurrentPlayer(player);
        menu.QueensFavor();
        Assertions.assertEquals(2,menu.getCurrentplayer().getHand().size(),"There should be 2 cards in hand");
    }

    @Test
    @DisplayName("R5.3 - Game affected by Prosperity")
    public void testProsperity(){
        Game game = new Game();
        Menu menu = new Menu(game);
        menu.setCurrentPlayer(game.getPlayers().getFirst());
        menu.Prosperity();

        Assertions.assertEquals(14,game.getPlayers().getFirst().getHand().size());
        Assertions.assertEquals(14,game.getPlayers().get(1).getHand().size());
        Assertions.assertEquals(14,game.getPlayers().get(2).getHand().size());
        Assertions.assertEquals(14,game.getPlayers().get(3).getHand().size());
    }

    @Test
    @DisplayName("R8 - Test detect trim needed")
    public void testDetectTrimming(){
        Game game = new Game();
        Menu menu = new Menu(game);
        Player p = game.getPlayers().getFirst();
        System.out.println("Current player hand " + p.getHand());
        p.getHand().add("H3");
        menu.setCurrentPlayer(p);
        System.out.println(menu.trimNeeded());
    }

    @Test
    @DisplayName("R11 - Test Stage initialization")
    public void testStageInitialization(){
        Game game = new Game();
        Menu menu = new Menu(game);
        Player play = game.getPlayers().getFirst();
        menu.setCurrentPlayer(play);
        play.getHand().clear();
        play.addCards(List.of("F1","F3","H10","S20"));
        Quest quest = new Quest("Q3",play,List.of(game.getPlayers().get(1),game.getPlayers().get(3)));

        quest.initializeStages(0,"F1",List.of("H10","S20"));
        Assertions.assertEquals("F1",quest.getStageAtLevel(1).getFoeCard());
        Assertions.assertEquals(31,quest.getStageAtLevel(1).getTotalValue());
    }

    @Test
    @DisplayName("R10 - Check if enough foe card by player to be sponsor")
    public void enoughFoeCard(){
        Game game = new Game();
        Menu menu = new Menu(game);
        Player player = game.getPlayers().getFirst();
        player.getHand().clear();
        player.addCards(List.of("F1","F3"));
        menu.setCurrentPlayer(player);
        Assertions.assertFalse(menu.enoughFoeCard(5));
        Assertions.assertTrue(menu.enoughFoeCard(2));
    }

}