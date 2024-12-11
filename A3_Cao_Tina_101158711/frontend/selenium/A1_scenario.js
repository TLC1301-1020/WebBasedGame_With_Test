let webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;

async function test () {
    // Access the page and load the page
    let driver = await new webdriver.Builder().forBrowser('chrome').build();

    await driver.get('http://127.0.0.1:8081/');
    await driver.sleep(2000);
    // Press start and wait
    await driver.findElement(By.id('one-button')).click();
    await new Promise(res => setTimeout(res, 2000));


    //quest4 drawn
    //p1 declines
    await driver.findElement(By.id('event-button')).click();
    await driver.sleep(2000);

    let msgBox = await driver.findElement(By.id('message-box'));
    await driver.wait(until.elementTextContains(msgBox,"Would you like to sponsor the quest?"));
    await driver.findElement(By.id('no-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to sponsor the quest?"));
    let currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"));
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"));
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"));
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 2000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"));
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"), 2000);
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"));
    await driver.findElement(By.id('yes-button')).click();
    await driver.sleep(2000);

    //p2 builds
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"));
    await driver.wait(until.elementTextIs(msgBox,"Enter the cards to build stage 1."));
    await driver.findElement(By.id('build-input')).sendKeys("F10");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 2."));
    await driver.findElement(By.id('build-input')).sendKeys("F10 B15");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 3."));
    await driver.findElement(By.id('build-input')).sendKeys("F10 B15 H10");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 4."));
    await driver.findElement(By.id('build-input')).sendKeys("F5 L20 E30");
    await driver.findElement(By.id('build-button')).click();
    await driver.sleep(2000);


    //p1 s1
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"));
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand by 1 cards."));
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("D5 S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"));
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."));
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("S10 D5");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"));
    await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."));
    await driver.findElement(By.id('discard-input')).sendKeys("F5");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
    await driver.findElement(By.id('play-input')).sendKeys("D5 H10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(2000);

    //s2
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player1"));
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 2."));
    await driver.findElement(By.id('play-input'),2000).sendKeys("H10 S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"));
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."));
    await driver.findElement(By.id('play-input'),2000).sendKeys("B15 S10");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"));
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 2."));
    await driver.findElement(By.id('play-input')).sendKeys("H10 B15");
    await driver.findElement(By.id('play-button')).click();
    await driver.sleep(2000);

    //s3
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"));
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 3."));
    await driver.findElement(By.id('play-input'),2000).sendKeys("L20 H10 S10");
    await driver.findElement(By.id('play-button'),2000).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"));
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 3."));
    await driver.findElement(By.id('play-input'),2000).sendKeys("D5 S10 L20");
    await driver.findElement(By.id('play-button'),2000).click();
    await driver.sleep(2000);

    //s4
    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player3"));
    await driver.wait(until.elementTextIs(msgBox,"Stage completed! Every participant draws one card. Enter cards to play stage 4."));
    await driver.findElement(By.id('play-input'),2000).sendKeys("B15 H10 L20");
    await driver.findElement(By.id('play-button'),2000).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player4"));
    await driver.wait(until.elementTextIs(msgBox,"Enter cards to play stage 4."));
    await driver.findElement(By.id('play-input'),2000).sendKeys("D5 S10 L20 E30");
    await driver.findElement(By.id('play-button'),2000).click();
    await driver.sleep(2000);

    currentPlayerName = await driver.findElement(By.id('player-name'));
    await driver.wait(until.elementTextIs(currentPlayerName,"Player2"));
    await driver.wait(until.elementTextIs(msgBox,"Quest passed! Shields are updated. Sponsor trim hand by 4 cards!"));
    await driver.findElement(By.id('discard-input')).sendKeys("S10 S10 S10 S10");
    await driver.findElement(By.id('discard-button')).click();
    await driver.sleep(2000);

    await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Current player turn ended! Please return."));
    await driver.findElement(By.id('return-button')).click();
    await driver.sleep(3000);

    await driver.findElement(By.id('quit-button')).click();
    await driver.sleep(2000);


}

test();