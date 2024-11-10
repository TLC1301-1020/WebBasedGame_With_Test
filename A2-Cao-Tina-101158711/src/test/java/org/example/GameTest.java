
package org.example;

import org.junit.jupiter.api.Assertions;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;


public class GameTest {

    @Test
    @DisplayName("R1.1 - Player Initialization")
    public void testPlayersInitialized() {
        Game game = new Game();
        List<Player> players = game.getPlayers();
        Assertions.assertEquals(4, players.size(), "The number of players is incorrect.");
        for (int i = 1; i <= 4; ++i) {
            Assertions.assertEquals("Player" + i, players.get(i - 1).getName(), "Player name is not correct.");
        }
    }

    @Test
    @DisplayName("R2.1 - Distribute adventure cards to players")
    public void testAdventureCardsDistributed(){
        Game game = new Game();
        for(Player player: game.getPlayers()){
            Assertions.assertEquals(12, player.getHand().size(),"Each player should receive 12 adventure cards");

        }
        Assertions.assertEquals(100 - (game.getPlayers().size()*12), game.getDeck().getAdventureDeck().size(), "Deck amount incorrect");
    }

    @Test
    @DisplayName("R6 - Identify winner(s)")
    public void testIdentifyWinners() {
        Game game = new Game();
        game.getPlayers().get(0).updateShields(30);
        game.getPlayers().get(1).updateShields(7);
        game.getPlayers().get(2).updateShields(10);
        game.getPlayers().get(3).updateShields(3);

        List<Player> winners = game.checkWinners();
        Assertions.assertEquals(3, winners.size(), "There should be 3 winners.");
        Assertions.assertFalse(winners.contains(game.getPlayers().get(3)),"Player 4 should not be a winner.");
        Assertions.assertTrue(winners.contains(game.getPlayers().get(0)), "Player 1 should be a winner.");
        Assertions.assertTrue(winners.contains(game.getPlayers().get(1)), "Player 2 should be a winner.");
        Assertions.assertTrue(winners.contains(game.getPlayers().get(2)), "Player 3 should be a winner.");
    }

    @Test
    @DisplayName("R6 - Positive value shield")
    public void testPlayerShield() {
        Game game = new Game();
        Player player = game.getPlayers().getFirst();
        player.updateShields(-20);

        Assertions.assertEquals(0, player.getShields(), "Player shields should be reset to 0 if negative.");
    }

    @Test
    @DisplayName("R7 - Game terminates when there's winner(s)")
    public void testTerminate(){
        Game game = new Game();
        game.getPlayers().get(0).updateShields(20);
        game.getPlayers().get(3).updateShields(10);
        Menu menu = new Menu(game);
        menu.displayMainMenu();
        Assertions.assertEquals(2,game.checkWinners().size());
    }

    @Test
    @DisplayName("R8 - Game prompts player to trim hand if more than 12 cards")
    public void testTrimCardFromPlayerClass() {
        Game game = new Game();
        Player player = game.getPlayers().getFirst();
        player.getHand().clear();

        player.addCards(List.of("F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13"));
        Assertions.assertEquals(13, player.getHand().size(), "Player should have 13 cards initially.");

        String cardsToTrim = "F5";
        player.trimHand(cardsToTrim);

        Assertions.assertEquals(12, player.getHand().size(), "Player should have 13 cards after trimming.");
        Assertions.assertFalse(player.getHand().contains("F5"), "Player's hand should not contain F5.");
    }

    @Test
    @DisplayName("R10.1 - Test find a sponsor")
    public void testIterateSponsor(){
        Game game = new Game();
        Menu menu = new Menu(game);
        menu.setCurrentPlayer(game.getPlayers().getFirst());
        menu.nextSponsor(menu.getCurrentplayer());
        Assertions.assertEquals("Player2", menu.getSponsorplayer().getName());

        menu.setCurrentPlayer(game.getPlayers().getLast());
        menu.nextSponsor(menu.getCurrentplayer());
        Assertions.assertEquals("Player1",menu.getSponsorplayer().getName());
    }

    @Test
    @DisplayName("R13 - Identifies quest participants")
    void testFindParticipants() {

        Scanner scanner = mock(Scanner.class);
        Game game = new Game();
        Menu menu = new Menu(game);

        menu.setScanner(scanner);
        when(scanner.nextInt())
                .thenReturn(1)  //participant
                .thenReturn(1)  //participant
                .thenReturn(2); //rejected
        List<Player> participants = menu.findParticipants();

        Assertions.assertEquals(2, participants.size());
        for(int i = 0; i < participants.size(); i++){
            System.out.println(participants.get(i).getName());
        }
        Assertions.assertEquals("Player1", participants.get(0).getName());
        Assertions.assertEquals("Player2", participants.get(1).getName());
    }

    @Test
    @DisplayName("R14.1 - test only one foe card being used at each stage")
    void testOnlyFoeCard() {
        Game game = mock(Game.class);
        Deck deck = mock(Deck.class);
        when(game.getDeck()).thenReturn(deck);

        Player sponsorPlayer = mock(Player.class);
        List<String> sponsorHand = new ArrayList<>(Arrays.asList("F1", "F2", "W1", "W2"));
        when(sponsorPlayer.getHand()).thenReturn(sponsorHand);

        Menu menu = new Menu(game);
        menu.setCurrentPlayer(sponsorPlayer);
        menu.setScanner(new Scanner("F1\nF2\nquit\n"));
        menu.setSponsorplayer(sponsorPlayer);

        String foeCard = menu.buildFoeCard(1);

        Assertions.assertEquals("F1", foeCard);
        verify(deck).discardEventCard("F1");

    }

    @Test
    @DisplayName("R14.2 - test non-repeated type weapon card can be used to build")
    public void testInvalidWeaponCardEntry() {
        Game game = mock(Game.class);
        Deck deck = mock(Deck.class);
        when(game.getDeck()).thenReturn(deck);

        Player sponsorPlayer = mock(Player.class);
        List<String> sponsorHand = new ArrayList<>(Arrays.asList("W1", "S2", "W3", "F1"));
        when(sponsorPlayer.getHand()).thenReturn(sponsorHand);

        Menu menu = new Menu(game);
        menu.setCurrentPlayer(sponsorPlayer);
        menu.setScanner(new Scanner("W1\nS2\nW3\nquit\n"));
        menu.setSponsorplayer(sponsorPlayer);
        List<String> weaponCards = menu.buildWeaponCards(1);

        Assertions.assertEquals(2, weaponCards.size());
        Assertions.assertTrue(weaponCards.contains("W1"));
        Assertions.assertFalse(weaponCards.contains("W2"));
        Assertions.assertTrue(weaponCards.contains("S2"));
    }

    @Test
    @DisplayName("R15 - test calculate participant value and pass/fail")
    public void testParticipantValue(){
        Game game = new Game();
        Menu menu = new Menu(game);
        int participantValue = 15;
        int stageValue = 10;
        Assertions.assertTrue(menu.participantPassed(participantValue,stageValue),"participant value is greater than the stage value");
    }

    @Test
    @DisplayName("R16 - test participant card discarding")
    void testCardDiscardingAndInputValidation() {
            Game mockGame = mock(Game.class);
            Deck mockDeck = mock(Deck.class);
            when(mockGame.getDeck()).thenReturn(mockDeck);


            Player player = new Player("TestPlayer");
            List<String> hand = new ArrayList<>();
            hand.add("S3");
            hand.add("F5");
            player.getHand().addAll(hand);

            Menu menu = new Menu(mockGame);
            menu.setCurrentPlayer(player);

            Scanner inputScanner = new Scanner("F5\nS3\nquit\n");
            menu.setScanner(inputScanner);

            int totalPlayed = menu.participantBuilds(0, player);


            verify(mockDeck, times(1)).discardAdventureCard("S3");

            Assertions.assertTrue(player.getHand().contains("F5"), "Player should still have F5 in hand.");
            Assertions.assertFalse(player.getHand().contains("S3"), "Player should not have S3 in hand after discarding.");

            Assertions.assertEquals(3, totalPlayed, "Total played should be 3.");

    }

    @Test
    @DisplayName("R17 - Game awards shields to quest winners and updates scores, ensuring all player records are accurate")
    void testPlayerPassesQuestStageAndGetsShields() {

        Scanner scanner = mock(Scanner.class);
        Game game = new Game();
        Menu menu = new Menu(game);

        menu.setScanner(scanner);
        when(scanner.nextInt())
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(2);
        List<Player> participants = menu.findParticipants();
        int initial = participants.getFirst().getShields();
        String event = "Q5";
        menu.participantsAddShields(event);
        Assertions.assertEquals(initial+5,participants.getFirst().getShields());
    }



    @Test
    @DisplayName("R9 - Test Discarded adventure cards are stored")
    public void testDiscardAdventureCard() {
        String card = "F10";
        Game g = new Game();

        g.getDeck().discardAdventureCard(card);
        g.getDeck().getAdventureDeck().clear();
        //check adding card to the discard adventure card deck
        Assertions.assertEquals(1, g.getDeck().getAdventureDiscardPile().size(),"Discard pile should contain 1 card");
        Assertions.assertTrue(g.getDeck().getAdventureDiscardPile().contains(card), "Discard pile should contain the discarded card.");
        g.reuseDeck();
        //check reuse the card
        Assertions.assertTrue(g.getDeck().getAdventureDiscardPile().isEmpty(),"Discard pile should be empty");
        Assertions.assertTrue(g.getDeck().getAdventureDeck().contains(card), "Discard pile should contain the discarded card.");


        card = "Plague";
        Game g2 = new Game();

        g2.getDeck().discardEventCard(card);
        g2.getDeck().getEventDeck().clear();
        //check adding card to the discard event card deck
        Assertions.assertEquals(1, g2.getDeck().getEventDiscardPile().size(),"Discard pile should contain 1 card");
        Assertions.assertTrue(g2.getDeck().getEventDiscardPile().contains(card), "Discard pile should contain the discarded card.");
        g2.reuseDeck();
        //check reuse the card
        Assertions.assertTrue(g2.getDeck().getEventDiscardPile().isEmpty(),"Discard pile should be empty");
        Assertions.assertTrue(g2.getDeck().getEventDeck().contains(card), "Discard pile should contain the discarded card.");

    }

//    @Test
//    @DisplayName("Test to add foe card")
//    public void testBuildFoeCard(){
//        Game game = new Game();
//        Menu menu = new Menu(game);
//        Player player = game.getPlayers().getFirst();
//        player.getHand().clear();
//        player.addCards(List.of("F1","E3","C5"));
//        menu.setCurrentPlayer(player);
//        System.out.println(menu.getCurrentplayer().getName() + "," + menu.getSponsorplayer().getName());
//        menu.buildQuest("Q5",player,List.of(game.getPlayers().get(0),game.getPlayers().get(2)));
//    }

//    @Test
//    @DisplayName("Test print players' hand")
//    public void testPlayersHand() {
//        Game g = new Game();
//        for (int i = 0; i < g.getPlayers().size(); i++) {
//            System.out.println(g.getPlayers().get(i).getName() + ": " + g.getPlayers().get(i).getHand());
//        }
//    }

}



