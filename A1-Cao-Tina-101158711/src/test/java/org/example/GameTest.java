
package org.example;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


public class GameTest {

    @Test
    @DisplayName("R1 - Player Initialization")
    public void testPlayersInitialized() {
        Game game = new Game();
        List<Player> players = game.getPlayers();
        Assertions.assertEquals(4, players.size(), "The number of players is incorrect.");
        for (int i = 1; i <= 4; ++i) {
            Assertions.assertEquals("Player" + i, players.get(i - 1).getName(), "Player name is not correct.");
        }
    }

    @Test
    @DisplayName("R1 - Adventure Deck Initialization")
    public void testAdventureDeckInitialized() {
        Deck deck = new Deck();
        //50 weapon cards + 50 foe cards = 100 cards in adventure deck
        List<String> adventureDeck = deck.getAdventureDeck();
        Assertions.assertEquals(100, adventureDeck.size(), "Adventure deck amount isn't correct");

        long countF5 = adventureDeck.stream().filter(card -> card.equals("F5")).count();
        Assertions.assertEquals(8, countF5, "There should be 8 F5 cards in the adventure deck.");

        long countF70 = adventureDeck.stream().filter(card -> card.equals("F70")).count();
        Assertions.assertEquals(1, countF70, "There should be 1 F70 card in the adventure deck.");

        long countD5 = adventureDeck.stream().filter(card -> card.equals("D5")).count();
        Assertions.assertEquals(6, countD5, "There should be 6 D5 cards in the adventure deck.");

        long countE30 = adventureDeck.stream().filter(card -> card.equals("E30")).count();
        Assertions.assertEquals(2, countE30, "There should be 2 E30 cards in the adventure deck.");
    }

    @Test
    @DisplayName("R1 - Event Deck Initialization")
    public void testEventDeckInitialized() {
        Deck deck = new Deck();
        //12 Q cards and 5 E cards = 17 cards in event deck
        List<String> eventDeck = deck.getEventDeck();
        Assertions.assertEquals(17, eventDeck.size(), "The number of event card is not correct");

        long countPlague = eventDeck.stream().filter(card -> card.equals("Plague")).count();
        Assertions.assertEquals(1, countPlague, "There should be 1 plague card in the event deck");

        long countQueensFavor = eventDeck.stream().filter(card -> card.equals("Queen's Favor")).count();
        Assertions.assertEquals(2, countQueensFavor, "There should be 2 'Queen's Favor' cards in the event deck.");

        long countProsperity = eventDeck.stream().filter(card -> card.equals("Prosperity")).count();
        Assertions.assertEquals(2, countProsperity, "There should be 2 'Prosperity' cards in the event deck.");
    }

    @Test
    @DisplayName("R2 - Distribute out adventure cards to players")
    public void testAdventureCardsDistributed(){
        Game game = new Game();
        for(Player player: game.getPlayers()){
            Assertions.assertEquals(12, player.getHand().size(),"Each player should receive 12 adventure cards");
        }
    }

    @Test
    @DisplayName("R2 - Deck size reduced after distribution")
    public void testDeckUpdatedAfterDistribution(){
        Game game = new Game();
        Assertions.assertEquals(100 - 48, game.getDeck().getAdventureDeck().size(), "Deck amount incorrect");
    }

    @Test
    @DisplayName("R2 - Player cards sorting")
    public void testSortedHands(){
        Player player = new Player("Test player");
        player.addCards(List.of("H10", "F5", "S10", "F15", "B15", "H5", "F20", "L20"));
        List<String> sortedHand = player.getSortedHand();
        List<String> expectedOrder = List.of("F5", "F15", "F20", "H5", "H10", "S10", "B15", "L20");
        Assertions.assertEquals(expectedOrder, sortedHand, "The player's hand should be sorted correctly");
    }

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
    @DisplayName("R4 - Event card drawn test")
    public void testDrawEventCard(){
        Deck deck = new Deck();
        int initialSize = deck.getEventDiscardPile().size();
        String card;
        card = deck.drawEventCard();
        Assertions.assertNotNull(card);

        //check the discarded pile is updated, size increased by one
        Assertions.assertEquals(initialSize+1, deck.getEventDiscardPile().size());
    }

    @Test
    @DisplayName("R5 - Game affected by Plague")
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
    @DisplayName("R5 - Game effected by Queen's Favor cards")
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
        Player player = game.getPlayers().get(0);
        player.updateShields(-20);

        player.getShields();
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
    public void testTrimCardFromGameClass() {
        Game game = new Game();
        Player player = game.getPlayers().get(0);
        player.getHand().clear();

        player.addCards(List.of("F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13"));
        // Check initial hand size
        Assertions.assertEquals(13, player.getHand().size(), "Player should have 15 cards initially.");
        // Cards to trim
        String cardsToTrim = "F5";
        game.TrimCards(player,cardsToTrim);
        Assertions.assertEquals(12, player.getHand().size(), "Player should have 13 cards after trimming.");
        Assertions.assertFalse(player.getHand().contains("F5"), "Player's hand should not contain F5.");
        //check discarded pile
        Assertions.assertEquals(1, game.getDeck().getAdventureDiscardPile().size(),"Discarded pile should have one card");
    }

    @Test
    @DisplayName("R8 - Game prompts player to trim hand if more than 12 cards")
    public void testTrimCardFromPlayerClass() {
        Game game = new Game();
        Player player = game.getPlayers().get(0);
        player.getHand().clear();

        player.addCards(List.of("F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13"));
        // Check initial hand size
        Assertions.assertEquals(13, player.getHand().size(), "Player should have 15 cards initially.");

        // Cards to trim
        String cardsToTrim = "F5";
        player.trimHand(cardsToTrim);

        Assertions.assertEquals(12, player.getHand().size(), "Player should have 13 cards after trimming.");
        Assertions.assertFalse(player.getHand().contains("F5"), "Player's hand should not contain F5.");
    }

    @Test
    @DisplayName("R9 - Test finding sponsor loop")
    public void testFindingSponsor(){
        Game game = new Game();
        Menu menu = new Menu(game);
        menu.setCurrentPlayer(game.getPlayers().getFirst());
    }

    @Test
    @DisplayName("R10 - Check iteration to find a sponsor")
    public void testIterateSponsor(){
        Game game = new Game();
        Menu menu = new Menu(game);
        menu.setCurrentPlayer(game.getPlayers().getFirst());
        menu.nextSponsor();
        Assertions.assertEquals("Player2", menu.getSponsorplayer().getName());

        menu.setCurrentPlayer(game.getPlayers().getLast());
        menu.nextSponsor();
        Assertions.assertEquals("Player1",menu.getSponsorplayer().getName());
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

        quest.initializeStages(1,"F1",List.of("H10","S20"));
        Assertions.assertEquals("F1",quest.getStage(1).getFoeCard());
        Assertions.assertEquals(31,quest.getStage(1).getTotalValue());
    }

    @Test
    @DisplayName("Test Discarded adventure cards are stored")
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
    }

    @Test
    @DisplayName("Test Discarded event cards are stored")
    public void testDiscardEventCard() {

        String card = "Plague";
        Game g = new Game();

        g.getDeck().discardEventCard(card);
        g.getDeck().getEventDeck().clear();
        //check adding card to the discard event card deck
        Assertions.assertEquals(1, g.getDeck().getEventDiscardPile().size(),"Discard pile should contain 1 card");
        Assertions.assertTrue(g.getDeck().getEventDiscardPile().contains(card), "Discard pile should contain the discarded card.");
        g.reuseDeck();
        //check reuse the card
        Assertions.assertTrue(g.getDeck().getEventDiscardPile().isEmpty(),"Discard pile should be empty");
        Assertions.assertTrue(g.getDeck().getEventDeck().contains(card), "Discard pile should contain the discarded card.");
    }

    @Test
    @DisplayName("Check if enough foe card by player")
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

    @Test
    @DisplayName("Get stage and foe card value")
    public void testStageAndFoeCardValue(){
        String foeCard = "F5";
        Stage stage = new Stage(1,foeCard, List.of("D5","E15","H20","H30"));
        Assertions.assertEquals(75,stage.getTotalValue());
        Assertions.assertEquals(5,stage.getCardValue(foeCard));
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
//        menu.startQuest("Q5",player,List.of(game.getPlayers().get(0),game.getPlayers().get(2)));
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



