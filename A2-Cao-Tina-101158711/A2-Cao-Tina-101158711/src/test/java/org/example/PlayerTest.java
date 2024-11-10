package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("R2 - Player cards sorting")
    public void testSortedHands(){
        Player player = new Player("Test player");
        player.addCards(List.of("H10", "F5", "S10", "F15", "B15", "H5", "F20", "L20"));
        List<String> sortedHand = player.getSortedHand();
        List<String> expectedOrder = List.of("F5", "F15", "F20", "H5", "H10", "S10", "B15", "L20");
        Assertions.assertEquals(expectedOrder, sortedHand, "The player's hand should be sorted correctly");
    }

}