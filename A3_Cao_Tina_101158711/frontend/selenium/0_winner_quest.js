let webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;

async function test () {
    // Access the page and load the page
    let driver = await new webdriver.Builder().forBrowser('chrome').build();

    await driver.get('http://127.0.0.1:8081/');

    await driver.sleep(2000);

        // Press start and wait
        await driver.findElement(By.id('four-button')).click();
        await new Promise(res => setTimeout(res, 2000));
        await driver.sleep(2000);


        // draws a 2-stage quest and decides to sponsor it
        await driver.findElement(By.id('event-button')).click();
        let msgBox = await driver.findElement(By.id('message-box'));
        await driver.wait(until.elementTextContains(msgBox,"Would you like to sponsor the quest?"));
        await driver.sleep(2000);

        await driver.findElement(By.id('yes-button')).click();
        await driver.wait(until.elementTextContains(msgBox,"Would you like to participate in the quest?"));
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

        // Build quest
        // Wait when condition
        await driver.wait(until.elementTextIs(msgBox,"Enter the cards to build stage 1."));
        await driver.findElement(By.id('build-input')).sendKeys("F50 D5 S10 H10 B15 L20");
        await driver.findElement(By.id('build-button')).click();
        await driver.sleep(2000);

        await driver.wait(until.elementTextIs(msgBox,"Enter cards to build stage 2."));
        await driver.findElement(By.id('build-input')).sendKeys("F70 D5 S10 H10 B15 L20");
        await driver.findElement(By.id('build-button')).click();
        await driver.sleep(2000);

        //p2 trim plays and fails
        currentPlayerName = await driver.findElement(By.id('player-name'));
        await driver.wait(until.elementTextIs(currentPlayerName,"Player2"));
        await driver.wait(until.elementTextIs(msgBox,"Please trim your hand by 1 cards."));
        await driver.findElement(By.id('discard-input')).sendKeys("F5");
        await driver.findElement(By.id('discard-button')).click();
        await driver.sleep(2000);

        await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
        await driver.findElement(By.id('play-input')).sendKeys("E30");
        await driver.findElement(By.id('play-button')).click();
        await driver.sleep(2000);

        //p3 plays and fails
        currentPlayerName = await driver.findElement(By.id('player-name'));
        await driver.wait(until.elementTextIs(currentPlayerName,"Player3"));
        await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."));
        await driver.findElement(By.id('discard-input')).sendKeys("F15");
        await driver.findElement(By.id('discard-button')).click();
        await driver.sleep(2000);

        await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
        await driver.findElement(By.id('play-input')).sendKeys("No");
        await driver.findElement(By.id('play-button')).click();
        await driver.sleep(2000);

        //p4 plays and fails
        currentPlayerName = await driver.findElement(By.id('player-name'));
        await driver.wait(until.elementTextIs(currentPlayerName,"Player4"));
        await driver.wait(until.elementTextIs(msgBox,"Please trim your hand first."));
        await driver.findElement(By.id('discard-input')).sendKeys("F10");
        await driver.findElement(By.id('discard-button')).click();
        await driver.sleep(2000);

        await driver.wait(until.elementTextIs(msgBox,"Quest built. Please play your card in stage 1."));
        await driver.findElement(By.id('play-input')).sendKeys("No");
        await driver.findElement(By.id('play-button')).click();
        await driver.sleep(2000);


        currentPlayerName = await driver.findElement(By.id('player-name'));
        await driver.wait(until.elementTextIs(currentPlayerName,"Player1"));
        await driver.findElement(By.id('discard-input')).sendKeys("F5 F10");
        await driver.findElement(By.id('discard-button')).click();
        await driver.sleep(2000);

        await driver.wait(until.elementTextIs(msgBox,"Cards discarded. Current player turn ended! Please return."));
        await driver.findElement(By.id('quit-button')).click();
        await driver.sleep(2000);

        await driver.wait(until.elementTextIs(msgBox,"Game quit."));
        await driver.sleep(2000);


}

test();