package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class twoWinnerGameTwoWinnerTest {

    private WebDriver driver;
    private GameController gameController;
    Map<String, Object> mockResponse = new HashMap<>();

    @BeforeEach
    public void setUp() {
        gameController = Mockito.mock(GameController.class);

        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        Player p3 = new Player("player3");
        Player p4 = new Player("player4");
        p1.getHand().clear();
        p2.getHand().clear();
        p3.getHand().clear();
        p4.getHand().clear();

        p1.addCards(List.of("F5", "F5", "F10", "F10", "F15", "F15", "D5", "H10", "H10", "B15", "B15", "L20"));
        p2.addCards(List.of("F40", "F50", "H10", "H10", "S10", "S10", "S10", "B15", "B15", "L20", "L20", "E30"));
        p3.addCards(List.of("F5", "F5", "F5", "F5", "D5", "D5", "D5", "S10", "S10", "S10", "S10", "S10"));
        p4.addCards(List.of("F50", "F70", "H10", "H10", "S10", "S10", "S10", "B15", "B15", "L20", "L20", "E30"));
        List<Player> ps = new ArrayList<>();
        ps.add(p1);
        ps.add(p2);
        ps.add(p3);
        ps.add(p4);
        mockResponse.put("players", ps);
        mockResponse.put("currentPlayer", ps.get(0));
        mockResponse.put("message", "Game started! Please draw an event card.");
        mockResponse.put("handOne", ps.get(0).getHand().size());
        mockResponse.put("handTwo", ps.get(1).getHand().size());
        mockResponse.put("handThree", ps.get(2).getHand().size());
        mockResponse.put("handFour", ps.get(3).getHand().size());
    }

    @Test
    public void testStartGame() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-file-access-from-files");
        options.addArguments("--disable-gpu");
        Path driverPath = Paths.get("..", "frontend", "node_modules", "chromedriver", "lib", "chromedriver", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", driverPath.toAbsolutePath().toString());
        driver = new ChromeDriver();

        when(gameController.startGame()).thenReturn(mockResponse);


        //Path indexPath = Paths.get("..", "frontend", "index.html");
        //driver.get(indexPath.toAbsolutePath().toString());
        driver.get("http://127.0.0.1:8081");

        Thread.sleep(2000);

        WebElement startButton = driver.findElement(By.id("start-button"));
        startButton.click();

        Thread.sleep(2000);

        WebElement playerName = driver.findElement(By.id("player-name"));
        assertEquals("Player1", playerName.getText());

        Thread.sleep(2000);
        WebElement playerHand = driver.findElement(By.id("player-hand"));
        assertEquals("[F5, F5, F10, F10, F15, F15, D5, H10, H10, B15, B15, L20]", playerHand.getText());

        Thread.sleep(2000);

        WebElement message = driver.findElement(By.id("message-box"));
        assertEquals("Game started! Please draw an event card.", message.getText());

        Thread.sleep(2000);
        WebElement handOne = driver.findElement(By.id("hand-player-1"));
        assertEquals("12", handOne.getText());
        WebElement handTwo = driver.findElement(By.id("hand-player-2"));
        assertEquals("12", handTwo.getText());
        WebElement handThree = driver.findElement(By.id("hand-player-3"));
        assertEquals("12", handThree.getText());
        WebElement handFour = driver.findElement(By.id("hand-player-4"));
        assertEquals("12", handFour.getText());

        Thread.sleep(2000);
        WebElement eventInfo = driver.findElement(By.id("event-info"));
        assertEquals("", eventInfo.getText());

        Thread.sleep(5000);
        driver.quit();
    }

}