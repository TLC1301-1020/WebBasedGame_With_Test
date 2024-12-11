let webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;

async function test () {
    // Access the page and load the page
    let driver = await new webdriver.Builder().forBrowser('chrome').build();
    
    await driver.get('http://127.0.0.1:8081/');
    await driver.sleep(1000);
    
    // Press start and wait
    await driver.findElement(By.id('two-button')).click();
    await new Promise(res => setTimeout(res, 2000));

    //p1  draws a 4-stage quest and decides to sponsor it
    await driver.findElement(By.id('event-button')).click();
    let msgBox = await driver.findElement(By.id('message-box'));
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to sponsor the quest?"), 100000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 100000);
    let currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.findElement(By.id('yes-button'),1000).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 100000);
    currentPlayerName = await driver.findElement(By.id('player-name'),1000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 100000);
    await driver.findElement(By.id('yes-button'),1000).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 100000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.findElement(By.id('yes-button')).click();

    await driver.sleep(1000);

    //build quest 4 stages
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter the cards to build stage 1."), 100000);
    await driver.findElement(By.id('build-input')).sendKeys("F5");
    await driver.findElement(By.id('build-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 2."),100000);
    await driver.findElement(By.id('build-input')).sendKeys("F5 D5");
    await driver.findElement(By.id('build-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 3."),100000);
    await driver.findElement(By.id('build-input')).sendKeys("F10 H10");
    await driver.findElement(By.id('build-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 4."),100000);
    await driver.findElement(By.id('build-input')).sendKeys("F10 B15");
    await driver.findElement(By.id('build-button')).click();

    await driver.sleep(1000);

    //p2 participate
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand by 1 cards."),100000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."), 100000);
    await driver.findElement(By.id('play-input')).sendKeys("H10");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p3 fails
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."), 100000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."), 100000);

    await driver.sleep(1000);

    await driver.findElement(By.id('play-input')).sendKeys("No");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."), 100000);
    await driver.findElement(By.id('discard-input')).sendKeys("F10");
    await driver.findElement(By.id('discard-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."), 100000);
    await driver.findElement(By.id('play-input')).sendKeys("H10");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p2  stage 2
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 2."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("S10");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p4 stage 2
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("S10");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p2  stage 3
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 3."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("H10 S10");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p4 stage 3
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 3."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("H10 S10");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p2  stage 4
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 4."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("S10 B15");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    //p4 stage 4
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 4."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("S10 B15");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Quest passed! Shields are updated. Sponsor trim hand by 4 cards!"),100000);
    await driver.findElement(By.id('discard-input')).sendKeys("F5 F10 F15 F15");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Current player turn ended! Please return."),100000);
    await driver.findElement(By.id('return-button')).click();

    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);


    await driver.wait(until.elementTextIs(msgBox,"Please draw an event card!"),100000);
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);

    await driver.sleep(1000);

    //quest 3 by p2, p3 sponsor
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.findElement(By.id('event-button')).click();
    await driver.wait(until.elementTextIs(msgBox,"Would you like to sponsor the quest?"),100000);
    await driver.findElement(By.id('no-button')).click();

    await driver.sleep(1000);


    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Would you like to sponsor the quest?"),100000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 1000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.findElement(By.id('yes-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 1000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"), 100000);
    await driver.findElement(By.id('no-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 1000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(1000);

    //build stage
    currentPlayerName = await driver.findElement(By.id('player-name'), 100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 100000);
    await driver.findElement(By.id('build-input')).sendKeys("F5");
    await driver.wait(until.elementTextIs(msgBox,"Enter the cards to build stage 1."), 100000);
    await driver.findElement(By.id('build-button')).click();

    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 2."), 100000);
    await driver.findElement(By.id('build-input')).sendKeys("F5 D5");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 3."), 100000);
    await driver.findElement(By.id('build-input')).sendKeys("F5 H10");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(1000);

    //player2
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."), 100000);
    await driver.findElement(By.id('play-input')).sendKeys("D5");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'),100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 1."), 100000);
    await driver.findElement(By.id('play-input')).sendKeys("D5");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'),100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 2."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'),100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("B15");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'),100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 3."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("E30");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'),100000);
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 3."),100000);
    await driver.findElement(By.id('play-input')).sendKeys("E30");
    await driver.findElement(By.id('play-button')).click();

    await driver.sleep(1000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"), 100000);
    await driver.wait(until.elementTextIs(msgBox,"Quest passed! Shields are updated. Sponsor trim hand by 3 cards!"),100000);
    await driver.findElement(By.id('discard-input')).sendKeys("F20 F25 F30");
    await driver.findElement(By.id('discard-button')).click();
    await driver.wait(until.elementTextIs(msgBox,"Game ended! Winner of the game: Player2 Player4"),100000);
    await driver.findElement(By.id('quit-button')).click();
    await driver.sleep(1000);


}

test();