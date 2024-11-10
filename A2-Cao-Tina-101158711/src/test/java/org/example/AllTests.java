package org.example;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({DeckTest.class, GameTest.class, MenuTest.class, PlayerTest.class,StageTest.class,QuestTest.class})
public class AllTests {

}
