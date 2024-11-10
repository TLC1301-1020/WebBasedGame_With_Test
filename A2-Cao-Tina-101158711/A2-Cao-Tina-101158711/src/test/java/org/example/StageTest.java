package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class StageTest {

    @Test
    @DisplayName("Get stage and foe card value")
    public void testStageAndFoeCardValue(){
        String foeCard = "F5";
        Stage stage = new Stage(1,foeCard, List.of("D5","E15","H20","H30"));
        Assertions.assertEquals(75,stage.getTotalValue());
        Assertions.assertEquals(5,stage.getCardValue(foeCard));
    }

}