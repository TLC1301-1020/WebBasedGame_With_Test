package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class DeckTest {

    @Test
    @DisplayName("R1.2 - Adventure Deck Initialization")
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
    @DisplayName("R1.3 - Event Deck Initialization")
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

}