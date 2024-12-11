let webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;

async function test () {
    // Access the page and load the page
    let driver = await new webdriver.Builder().forBrowser('chrome').build();

    await driver.get('http://127.0.0.1:8081/');
    await driver.sleep(2000);

    // Press start and wait
    await driver.findElement(By.id('three-button')).click();
    await new Promise(res => setTimeout(res, 2000));

    //p1 draws 4
    await driver.findElement(By.id('event-button')).click();
    let msgBox = await driver.findElement(By.id('message-box'));
    await driver.wait(until.elementTextContains(msgBox,"Would you like to sponsor the quest?"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 1000);
    let currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 1000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 1000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);


    //build quest 4 stages
    await driver.wait(until.elementTextIs(msgBox,"Enter the cards to build stage 1."), 10000);
    await driver.findElement(By.id('build-input')).sendKeys("F5");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 2."),10000);
    await driver.findElement(By.id('build-input')).sendKeys("F10");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 3."),10000);
    await driver.findElement(By.id('build-input')).sendKeys("F15");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);


    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 4."),10000);
    await driver.findElement(By.id('build-input')).sendKeys("F20");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);

    //p2 participate
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F10");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."));
    await driver.findElement(By.id('discard-input')).sendKeys("F20");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    //p2  stage 2
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 2."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("H10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("H10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("H10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    //stage 3
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 3."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 3."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 3."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);


    //stage 4
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 4."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("L20");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 4."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("L20");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 4."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("L20");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Quest passed! Shields are updated. Sponsor trim hand by 4 cards!"),10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5 F5 F10 F10");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Current player turn ended! Please return."),10000);
    await driver.findElement(By.id('return-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Please draw an event card!"),10000);
    await driver.sleep(1000);

    //p2 draws plague
    await driver.findElement(By.id('event-button')).click();
    await driver.wait(until.elementTextIs(msgBox,"Your turn ended. Please return."),10000);
    await driver.findElement(By.id('return-button')).click();
    await driver.sleep(1000);

    //p3 turn
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Please draw an event card!"),10000);
    await driver.findElement(By.id('event-button')).click();
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Your turn ended! Please return."),100000);
    await driver.findElement(By.id('return-button')).click();
    await driver.sleep(1000);

    //p4 turn queens
     currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.sleep(1000);

    await driver.findElement(By.id('discard-input')).sendKeys("F20");
    await driver.findElement(By.id('discard-button')).click();
    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Please draw an event!"),10000);
    await driver.sleep(1000);

    await driver.findElement(By.id('event-button')).click();
    await driver.sleep(1000);

    await driver.findElement(By.id('discard-input')).sendKeys("F25 F30");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Your turn ended! Please return."),10000);
    await driver.findElement(By.id('return-button')).click();
    await driver.sleep(1000);

    //p1 turn
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5 F10");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Please draw an event!"),10000);
    await driver.findElement(By.id('event-button')).click();
    await driver.sleep(1000);

    //p1 draws Q3
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Would you like to sponsor the quest?"),10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 10000);
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 10000);
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 10000);
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    //build stage
    await driver.wait(until.elementTextIs(msgBox,"Enter the cards to build stage 1."), 10000);
    await driver.findElement(By.id('build-input'), 10000).sendKeys("F15");
    await driver.findElement(By.id('build-button'), 10000).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 2."), 10000);
    await driver.findElement(By.id('build-input')).sendKeys("F15 D5");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);


    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 3."), 10000);
    await driver.findElement(By.id('build-input')).sendKeys("F20 D5");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);


    //p2 discards one card from prosperity and one from quest
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5 F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    //p3
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."), 10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F10");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."), 10000);
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    //p4 and failed
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."), 10000);
    await driver.findElement(By.id('discard-input')).sendKeys("F20");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."), 10000);
    await driver.findElement(By.id('play-input')).sendKeys("H10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);


    //p2  stage 2
    currentPlayerName = await driver.findElement(By.id('player-name'), 10000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 2."),10000);
    await driver.findElement(By.id('play-input'),1000).sendKeys("B15 H10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."),10000);
    await driver.findElement(By.id('play-input'),1000).sendKeys("B15 S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 10000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 3."),10000);
    await driver.findElement(By.id('play-input')).sendKeys("L20 S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 10000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 3."),10000);
    await driver.findElement(By.id('play-input'),1000).sendKeys("E30");
    await driver.findElement(By.id('play-button'),1000).click();
    await driver.sleep(1000);


    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Quest passed! Shields are updated. Sponsor trim hand by 3 cards!"),100000);
    await driver.findElement(By.id('discard-input'),1000).sendKeys("F15 F15 F15");
    await driver.findElement(By.id('discard-button'),1000).click();
    await driver.sleep(1000);


    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Current player turn ended! Please return."),100000);
    await driver.findElement(By.id('return-button')).click();

    await driver.sleep(1000);


    await driver.wait(until.elementTextIs(msgBox,"Game ended! Winner of the game: Player3"),10000);
    await driver.findElement(By.id('quit-button')).click();

    await driver.sleep(1000);

}

test();