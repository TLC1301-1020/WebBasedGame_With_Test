package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestTest {

    @Test
    @DisplayName("R12 - Test sponsor inputs of each stage are in increasing order")
    void testStagesHaveIncreasingValues() {

        Player sponsor = mock(Player.class);
        Player participant1 = mock(Player.class);
        Player participant2 = mock(Player.class);
        List<Player> participants = Arrays.asList(participant1, participant2);

        when(sponsor.getHand()).thenReturn(Arrays.asList("F3", "W4", "W5"));

        Quest quest = new Quest("Q3", sponsor, participants);

        boolean stage1Valid = quest.initializeStages(0, "F2", List.of("W5"));
        boolean stage2Invalid = quest.initializeStages(1, "F3", List.of("W4"));
        boolean stage2Valid = quest.initializeStages(1, "F3", List.of("W5"));

        Assertions.assertTrue(stage1Valid, "Stage 1 should be valid");
        Assertions.assertFalse(stage2Invalid, "Stage 2 should be rejected for having the same or lower value");
        Assertions.assertTrue(stage2Valid, "Stage 2 should be accepted for having a strictly greater value than Stage 1");
    }


}