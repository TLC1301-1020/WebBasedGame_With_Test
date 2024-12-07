
const apiBaseUrl = "http://localhost:8080";

async function startGame() {
    try {
        const response = await fetch(`${apiBaseUrl}/start`);
        const result = await response.text();
        console.log("Start Game Response:", result);

        // Set name of the current user
        let resultJson = JSON.parse(result);
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON["name"];
        document.getElementById("player-hand").innerText = currentPlayerJSON["hand"];

        //set message
        let message = resultJson["message"];
        document.getElementById("message-box").innerText = `${message}`;


        // Set Shields of all users
        let playersArray = resultJson["players"];
        playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
        });


        let handOne = resultJson["handOne"];
        document.getElementById("hand-player-1").innerText = `${handOne}`;
        let handTwo = resultJson["handTwo"];
        document.getElementById("hand-player-2").innerText = `${handTwo}`;
        let handThree = resultJson["handThree"];
        document.getElementById("hand-player-3").innerText = `${handThree}`;
        let handFour = resultJson["handFour"];
        document.getElementById("hand-player-4").innerText = `${handFour}`;

        document.getElementById("event-info").innerText = null;



    } catch (error) {
        console.error("Error in startGame:", error);
    }
}

async function showEvent() {
    try {
        const response = await fetch(`${apiBaseUrl}/event`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log("Response from server:", JSON.stringify(result));

        let eventCard = result.eventCard;
        document.getElementById("event-info").innerText = eventCard;

        let message = result.message;
        document.getElementById("message-box").innerText = `${message}`;


        let playersArray = result["players"];
        console.log(result);
        console.log(playersArray);

        playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
        });


        let currentPlayerJSON = result.currentPlayer;
        document.getElementById("player-name").innerText = currentPlayerJSON.name;
        document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

        document.getElementById("hand-player-1").innerText = result.handOne;
        document.getElementById("hand-player-2").innerText = result.handTwo;
        document.getElementById("hand-player-3").innerText = result.handThree;
        document.getElementById("hand-player-4").innerText = result.handFour;

    } catch (error) {
        console.error("Error in showing event:", error);
    }
}

async function yesButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/yes`);
        const result = await response.text();
        console.log("yes button Response:", result);

        let resultJson = JSON.parse(result);
        // currentPlayer
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON.name;
        document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

        // message
        let message = resultJson.message;
        document.getElementById("message-box").innerText = `${message}`;


        document.getElementById("hand-player-1").innerText = resultJson.handOne;
        document.getElementById("hand-player-2").innerText = resultJson.handTwo;
        document.getElementById("hand-player-3").innerText = resultJson.handThree;
        document.getElementById("hand-player-4").innerText = resultJson.handFour;



    } catch (error) {
        console.error("Error in returning:", error);
    }
}

async function noButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/no`);
        const result = await response.text();
        console.log("no button Response:", result);

        let resultJson = JSON.parse(result);
        // currentPlayer
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON.name;
        document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

        // message
        let message = resultJson.message;
        document.getElementById("message-box").innerText = `${message}`;

        document.getElementById("hand-player-1").innerText =  resultJson.handOne;
        document.getElementById("hand-player-2").innerText = resultJson.handTwo;
        document.getElementById("hand-player-3").innerText = resultJson.handThree;
        document.getElementById("hand-player-4").innerText = resultJson.handFour;



    } catch (error) {
        console.error("Error in returning:", error);
    }
}
async function buildButton(){

try{
    let textfield = document.getElementById("build-input");
       if(textfield.value === ""){
            alert("You have to enter cards to play");
            return;
       }else{
            let text = textfield.value;

            let textarr = text.split(/[,\.\s-]+/);

            const response = await fetch(`${apiBaseUrl}/build`, {
                            method: 'POST',
                            headers: {
                                'Content-type': 'application/json'
                            },
                            body: JSON.stringify(textarr)
                        });

            const result = await response.text();
            console.log("Play Cards Response:", result);

            // currentPlayer
            let currentPlayerJSON = JSON.parse(result)["currentPlayer"];
            document.getElementById("player-name").innerText = currentPlayerJSON.name;
            document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

            // message
            let message = JSON.parse(result).message;
            document.getElementById("message-box").innerText = `${message}`;

            let resultJson = JSON.parse(result);
            document.getElementById("hand-player-1").innerText =  resultJson.handOne;
            document.getElementById("hand-player-2").innerText = resultJson.handTwo;
            document.getElementById("hand-player-3").innerText = resultJson.handThree;
            document.getElementById("hand-player-4").innerText = resultJson.handFour;



            // Empty the textfield
            textfield.value = null;
        }
    } catch (error) {
        console.error("Error in building cards:", error);
    }
}

async function playButton(){
try{
    let textfield = document.getElementById("play-input");
    let text = textfield.value;

    let textarr = text.split(/[,\.\s-]+/);

    const response = await fetch(`${apiBaseUrl}/play`, {
                    method: 'POST',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify(textarr)
                });

    const result = await response.text();
    console.log("Play Cards Response:", result);

    // currentPlayer
    let currentPlayerJSON = JSON.parse(result)["currentPlayer"];
    document.getElementById("player-name").innerText = currentPlayerJSON.name;
    document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

    // message
    let message = JSON.parse(result).message;
    document.getElementById("message-box").innerText = `${message}`;

    let playersArray = JSON.parse(result)["players"];
    playersArray.forEach(playerObj => {
        let playername = playerObj["name"];
        let playerNumber = playername.substring(6);
        let shieldID = "shields-" + playerNumber;
        document.getElementById(shieldID).innerText = playerObj["shields"];
    });


    let resultJson = JSON.parse(result);
    document.getElementById("hand-player-1").innerText =  resultJson.handOne;
    document.getElementById("hand-player-2").innerText = resultJson.handTwo;
    document.getElementById("hand-player-3").innerText = resultJson.handThree;
    document.getElementById("hand-player-4").innerText = resultJson.handFour;

    // Empty the textfield
    textfield.value = null;
    } catch (error) {
        console.error("Error in playing cards:", error);
    }
}


async function discardCards() {
    try {
        let textfield = document.getElementById("discard-input");
        if (textfield.value === ""){
            alert("You have to enter cards to discard");
            return;
        }else{
            // Turn the texts into an arraylist
            let text = textfield.value;

            // Turn text into array
            let textarr = text.split(/[,\.\s-]+/);

            // Send it to the server
            const response = await fetch(`${apiBaseUrl}/discard`, {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(textarr)
            });

            const result = await response.text();
            console.log("Discard Cards Response:", result);

            // currentPlayer
            let currentPlayerJSON = JSON.parse(result)["currentPlayer"];
            document.getElementById("player-name").innerText = currentPlayerJSON.name;
            document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

            // message
            let message = JSON.parse(result).message;
            document.getElementById("message-box").innerText = `${message}`;

            let resultJson = JSON.parse(result);
            document.getElementById("hand-player-1").innerText =  resultJson.handOne;
            document.getElementById("hand-player-2").innerText = resultJson.handTwo;
            document.getElementById("hand-player-3").innerText = resultJson.handThree;
            document.getElementById("hand-player-4").innerText = resultJson.handFour;

            let playersArray = JSON.parse(result)["players"];
            playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
            });

            // Empty the textfield
            textfield.value = null;
        }
    } catch (error) {
        console.error("Error in discarding cards:", error);
    }
}

async function returnButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/return`);
        const result = await response.text();
        console.log("return button Response:", result);

        let resultJson = JSON.parse(result);
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON.name;
        document.getElementById("player-hand").innerText = currentPlayerJSON.hand;

        let message = JSON.parse(result).message;
        document.getElementById("message-box").innerText = `${message}`;

        document.getElementById("hand-player-1").innerText =  resultJson.handOne;
        document.getElementById("hand-player-2").innerText = resultJson.handTwo;
        document.getElementById("hand-player-3").innerText = resultJson.handThree;
        document.getElementById("hand-player-4").innerText = resultJson.handFour;

        document.getElementById("event-info").innerText = null;


        let playersArray = resultJson["players"];
        playersArray.forEach(playerObj => {
        let playername = playerObj["name"];
        let playerNumber = playername.substring(6);
        let shieldID = "shields-" + playerNumber;
        document.getElementById(shieldID).innerText = playerObj["shields"];
        });


    } catch (error) {
        console.error("Error in returning:", error);
    }
}

async function quitButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/quit`);
        const result = await response.text();
        console.log("quit button Response:", result);

        let resultJson = JSON.parse(result);

        // message
        let message = resultJson.message;
        document.getElementById("message-box").innerText = `${message}`;

        document.getElementById("player-name").innerText = null;
        document.getElementById("player-hand").innerText = null;


        let playersArray = resultJson["players"];


            playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let hand = "hand-player-" + playerNumber;
            document.getElementById(hand).innerText = playerObj["hand"];
        });


        document.getElementById("event-info").innerText = null;

        for(let i=1;i<5;i++){
            let shieldID = "shields-" + i;
            document.getElementById(shieldID).innerText = null;
        }


        document.getElementById("build-input").value = null;
        document.getElementById("play-input").value = null;
        document.getElementById("discard-input").value = null;

    } catch (error) {
        console.error("Error in returning:", error);
    }
}

async function oneButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/tOne`);
        const result = await response.text();
        console.log("Start Game Response:", result);
        // Set name of the current user
        let resultJson = JSON.parse(result);
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON["name"];
        document.getElementById("player-hand").innerText = currentPlayerJSON["hand"];
        //set message
        let message = resultJson["message"];
        document.getElementById("message-box").innerText = `${message}`;
        // Set Shields of all users
        let playersArray = resultJson["players"];
        playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
        });
        let handOne = resultJson["handOne"];
        document.getElementById("hand-player-1").innerText = `${handOne}`;
        let handTwo = resultJson["handTwo"];
        document.getElementById("hand-player-2").innerText = `${handTwo}`;
        let handThree = resultJson["handThree"];
        document.getElementById("hand-player-3").innerText = `${handThree}`;
        let handFour = resultJson["handFour"];
        document.getElementById("hand-player-4").innerText = `${handFour}`;

        document.getElementById("event-info").innerText = null;



    } catch (error) {
        console.error("Error:", error);
    }
}


async function twoButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/tTwo`);
        const result = await response.text();
        console.log("Start Game Response:", result);
        // Set name of the current user
        let resultJson = JSON.parse(result);
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON["name"];
        document.getElementById("player-hand").innerText = currentPlayerJSON["hand"];
        //set message
        let message = resultJson["message"];
        document.getElementById("message-box").innerText = `${message}`;
        // Set Shields of all users
        let playersArray = resultJson["players"];
        playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
        });
        let handOne = resultJson["handOne"];
        document.getElementById("hand-player-1").innerText = `${handOne}`;
        let handTwo = resultJson["handTwo"];
        document.getElementById("hand-player-2").innerText = `${handTwo}`;
        let handThree = resultJson["handThree"];
        document.getElementById("hand-player-3").innerText = `${handThree}`;
        let handFour = resultJson["handFour"];
        document.getElementById("hand-player-4").innerText = `${handFour}`;

        document.getElementById("event-info").innerText = null;



    } catch (error) {
        console.error("Error:", error);
    }
}

async function threeButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/tThree`);
        const result = await response.text();
        console.log("Start Game Response:", result);
        // Set name of the current user
        let resultJson = JSON.parse(result);
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON["name"];
        document.getElementById("player-hand").innerText = currentPlayerJSON["hand"];
        //set message
        let message = resultJson["message"];
        document.getElementById("message-box").innerText = `${message}`;
        // Set Shields of all users
        let playersArray = resultJson["players"];
        playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
        });


        let handOne = resultJson["handOne"];
        document.getElementById("hand-player-1").innerText = `${handOne}`;
        let handTwo = resultJson["handTwo"];
        document.getElementById("hand-player-2").innerText = `${handTwo}`;
        let handThree = resultJson["handThree"];
        document.getElementById("hand-player-3").innerText = `${handThree}`;
        let handFour = resultJson["handFour"];
        document.getElementById("hand-player-4").innerText = `${handFour}`;

        document.getElementById("event-info").innerText = null;



    } catch (error) {
        console.error("Error:", error);
    }
}

async function fourButton() {
    try {
        const response = await fetch(`${apiBaseUrl}/tFour`);

        const result = await response.text();
        console.log("Start Game Response:", result);
        // Set name of the current user
        let resultJson = JSON.parse(result);
        let currentPlayerJSON = resultJson["currentPlayer"];
        document.getElementById("player-name").innerText = currentPlayerJSON["name"];
        document.getElementById("player-hand").innerText = currentPlayerJSON["hand"];
        //set message
        let message = resultJson["message"];
        document.getElementById("message-box").innerText = `${message}`;

        // Set Shields of all users

        let playersArray = resultJson["players"];
        playersArray.forEach(playerObj => {
            let playername = playerObj["name"];
            let playerNumber = playername.substring(6);
            let shieldID = "shields-" + playerNumber;
            document.getElementById(shieldID).innerText = playerObj["shields"];
        });


        let handOne = resultJson["handOne"];
        document.getElementById("hand-player-1").innerText = `${handOne}`;
        let handTwo = resultJson["handTwo"];
        document.getElementById("hand-player-2").innerText = `${handTwo}`;
        let handThree = resultJson["handThree"];
        document.getElementById("hand-player-3").innerText = `${handThree}`;
        let handFour = resultJson["handFour"];
        document.getElementById("hand-player-4").innerText = `${handFour}`;

        document.getElementById("event-info").innerText = null;

    } catch (error) {
        console.error("Error:", error);
    }
}


document.getElementById("one-button").addEventListener("click", oneButton);
document.getElementById("two-button").addEventListener("click", twoButton);
document.getElementById("three-button").addEventListener("click", threeButton);
document.getElementById("four-button").addEventListener("click", fourButton);

document.getElementById("discard-button").addEventListener("click", discardCards);
document.getElementById('build-button').addEventListener("click",buildButton);
document.getElementById('play-button').addEventListener("click",playButton);
document.getElementById("start-button").addEventListener("click", startGame);
document.getElementById("event-button").addEventListener("click", showEvent);



document.getElementById("yes-button").addEventListener("click", yesButton);
document.getElementById("no-button").addEventListener("click", noButton);
document.getElementById("return-button").addEventListener("click", returnButton);
document.getElementById('quit-button').addEventListener('click', quitButton);


