package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8081")
public class GameController {

    @Autowired
    private Game game;

    @GetMapping("/start")
    public Map<String, Object> startGame() {
        Map<String, Object> response = new HashMap<>();

        game.initializePlayers();
        game.initializeGame();
        game.nextRound();

        response.put("players", game.getPlayers());
        response.put("currentPlayer", game.getCurrentPlayer());

        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("message", "Game started! Please draw an event card.");

        return response;
    }


    @GetMapping("/event")
    public Map<String, Object> drawEvent() {
        Map<String, Object> response = new HashMap<>();
        game.drawEventCard();
        //check that this person has drawn event
        game.switchEventHappened();

        if (game.getEventCard().equals("Plague")) {
            game.Plague();
            response.put("eventCard", game.getEventCard());
            response.put("currentPlayer", game.getCurrentPlayer());

            response.put("message", "Your turn ended. Please return.");


        } else if (game.getEventCard().equals("Queen's Favor")) {
            game.QueensFavor();
            response.put("eventCard", game.getEventCard());
            response.put("currentPlayer", game.getCurrentPlayer());
            int trim = game.trimAmount(game.getCurrentPlayer());
            if (trim > 0) {
                response.put("message", "Please trim your hand by " + trim + " cards!");
            }else{
                response.put("message", "Your turn ended. Please return");
            }


        } else if (game.getEventCard().equals("Prosperity")) {
            game.Prosperity();
            response.put("eventCard", game.getEventCard());
            response.put("currentPlayer", game.getCurrentPlayer());

            int trim = game.trimAmount(game.getCurrentPlayer());
            if (trim > 0) {
                response.put("message", "Please trim your hand by " + trim + " cards!");
            }else{
                response.put("message", "Your turn ended. Please return");
            }


            /*quest event*/
        } else {

            game.setRespondingPlayer(game.getCurrentPlayer());
            response.put("eventCard", game.getEventCard());
            response.put("currentPlayer", game.getRespondingPlayer());
            response.put("message", "Would you like to sponsor the quest?");
        }

        //hand size
        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("players", game.getPlayers());

        return response;

    }

    @GetMapping("/no")
    public Map<String, Object> noButton() {
        Map<String, Object> response = new HashMap<>();

        //no sponsor found for current player
        if(!game.getSponsorFound()) {
            //no more player to ask
            if (game.getSponsorCount() == 0) {
                game.setCounts();
                response.put("message", "No sponsor found. Hit return key to end your round!");
                response.put("currentPlayer", game.getCurrentPlayer());
                //ask next player
            }else{
                game.nextRespondingPlayer(game.getRespondingPlayer());
                game.updateSponsorCountCheck();
                response.put("message", "Would you like to sponsor the quest?");
                response.put("currentPlayer", game.getRespondingPlayer());
            }
            //there is a sponsor
        }else {
            //no participant found
            if (!game.getParticipantFound() && game.getParticipateCount() == 0) {
                game.setCounts();
                response.put("message", "No participant found. Hit return key to end your round!");
                response.put("currentPlayer", game.getCurrentPlayer());

                //participant found and no more to ask
            } else if (game.getParticipantFound() && game.getParticipateCount() == 0) {
                game.setCounts();
                game.createQuest();
                int stage = game.getCurrentStage() + 1;
                response.put("message", "Enter cards to build stage " + stage  + ".");
                response.put("currentPlayer", game.getSponsorPlayer());

                //participate not finished asking, ask next
            } else {
                game.nextRespondingPlayer(game.getRespondingPlayer());
                game.updateParticipantCountCheck();
                response.put("message", "Would you like to participate in the quest?");
                response.put("currentPlayer", game.getRespondingPlayer());
            }

        }
        //hand size
        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());
        return response;

    }

    @GetMapping("yes")
    public Map<String, Object> yesButton() {
        Map<String, Object> response = new HashMap<>();

        //found a sponsor
        if (!game.getSponsorFound()) {
            game.addQuestPlayers();
            game.sponsorFound();
            game.nextRespondingPlayer(game.getRespondingPlayer());
            response.put("message", "Would you like to participate in the quest?");

            game.updateParticipantCountCheck();

            response.put("currentPlayer", game.getRespondingPlayer());
            response.put("handOne", game.getPlayers().get(0).getHand().size());
            response.put("handTwo", game.getPlayers().get(1).getHand().size());
            response.put("handThree", game.getPlayers().get(2).getHand().size());
            response.put("handFour", game.getPlayers().get(3).getHand().size());
            return response;
        }
        //already have a sponsor
        game.participantFound();

        //more to ask
        if(game.getParticipateCount() > 0){
            game.addQuestPlayers();
            game.updateParticipantCountCheck();
            response.put("message", "Would you like to participate in the quest?");
            game.nextRespondingPlayer(game.getRespondingPlayer());
            response.put("currentPlayer", game.getRespondingPlayer());

            //last to ask
        }else if(game.getParticipateCount() == 0){
            game.addQuestPlayers();
            game.createQuest();
            int stage = game.getCurrentStage()+1;
            response.put("message", "Enter the cards to build stage " + stage + ".");
            response.put("currentPlayer", game.getSponsorPlayer());
        }


        //hand size
        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        return response;
    }

    @GetMapping("/quit")
    public Map<String, Object> quitGame(){
        Map<String, Object> response = new HashMap<>();

        response.put("message", "Game quit.");
        response.put("players", game.getPlayers());

        return response;
    }


    @PostMapping("/build")
    public Map<String, Object> buildStage(@RequestBody ArrayList<String> cards) {
        Map<String, Object> response = new HashMap<>();
        //assume first card is foe card
        for (String card : cards) {
            game.addBuildingCards(card);
        }

        game.createStage(game.getCurrentStage());

        if (game.getCurrentStage() < game.getTotalStage()) {
            game.updateCurrentStage();
            int stage = game.getCurrentStage() + 1;
            response.put("message", "Enter cards to build stage " + stage + ".");
            response.put("currentPlayer", game.getSponsorPlayer());

        } else {
            game.updateChallengerSize();
            game.newStageDrawCards();
            response.put("currentPlayer", game.getChallenger());
            game.clearCurrentStage();

            //if challenger has hand > 12

            int trim = game.trimAmount(game.getChallenger());
            if (trim > 0) {
                response.put("currentPlayer", game.getChallenger());
                response.put("message", "Please trim your hand by " + trim + " cards.");

                response.put("handOne", game.getPlayers().get(0).getHand().size());
                response.put("handTwo", game.getPlayers().get(1).getHand().size());
                response.put("handThree", game.getPlayers().get(2).getHand().size());
                response.put("handFour", game.getPlayers().get(3).getHand().size());

                response.put("players", game.getPlayers());

                return response;
            }

            int stage = game.getCurrentStage() + 1;
            response.put("currentPlayer", game.getChallenger());
            response.put("message", "Quest built. Please play your card in stage " + stage + ".");

        }
        //hand size
        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());
        response.put("players", game.getPlayers());


        return response;

    }

    @PostMapping("/discard")
    public Map<String, Object> discardCards(@RequestBody ArrayList<String> cards) {
        Map<String, Object> response = new HashMap<>();

        if (game.getEventCard().equals("Queen's Favor")|| game.getEventCard().equals("Prosperity") || game.getEventCard().equals("Plague")) {

            //trimming hand normally
            Player player;
            player = game.getCurrentPlayer();
            for (String card : cards) {
                game.TrimCards(player, card);
            }


            //player trim after event
            if(game.getEventHappened()){
                response.put("message", "Cards discarded. Your turn ended! Please return.");
            }else {
                response.put("message", "Cards discarded. Please draw an event!");
            }

            response.put("currentPlayer", game.getCurrentPlayer());
            response.put("players", game.getPlayers());
            //hand size
            response.put("handOne", game.getPlayers().get(0).getHand().size());
            response.put("handTwo", game.getPlayers().get(1).getHand().size());
            response.put("handThree", game.getPlayers().get(2).getHand().size());
            response.put("handFour", game.getPlayers().get(3).getHand().size());
            return response;

        }//end of normal play trim


        //trim for the current challenger
        if(game.getChallenger() != null && game.getChallenger().getHand().size() > 12) {
            //trimming hand by current challenger

            if (game.getChallenger().getHand().size() > 12) {
                for (String card : cards) {
                    game.TrimCards(game.getChallenger(), card);
                }

                int stage = game.getCurrentStage() + 1;
                response.put("message", "Quest built. Please play your card in stage " + stage + ".");
                response.put("currentPlayer", game.getChallenger());
                //hand size
                response.put("handOne", game.getPlayers().get(0).getHand().size());
                response.put("handTwo", game.getPlayers().get(1).getHand().size());
                response.put("handThree", game.getPlayers().get(2).getHand().size());
                response.put("handFour", game.getPlayers().get(3).getHand().size());
                response.put("players", game.getPlayers());
                return response;
            }

        }//end of challenger trim


        if(game.getSponsorPlayer() != null && game.getSponsorPlayer().getHand().size() > 12) {
            //trimming hand by sponsor, return response of current player and message to return

            if (game.getSponsorTrim()) {

                Player p = game.getSponsorPlayer();
                for (String card : cards) {
                    game.TrimCards(p, card);
                }

                game.clearSponsor();

                if (!game.checkWinners().isEmpty()) {
                    response.put("message", "Game ended! Winner of the game: " + game.getWinnerNames());
                    response.put("handOne", game.getPlayers().get(0).getHand().size());
                    response.put("handTwo", game.getPlayers().get(1).getHand().size());
                    response.put("handThree", game.getPlayers().get(2).getHand().size());
                    response.put("handFour", game.getPlayers().get(3).getHand().size());
                    response.put("players", game.getPlayers());
                    response.put("currentPlayer", game.getCurrentPlayer());
                    return response;
                }

                response.put("message", "Cards discarded. Current player turn ended! Please return.");
                response.put("currentPlayer", game.getCurrentPlayer());
                response.put("handOne", game.getPlayers().get(0).getHand().size());
                response.put("handTwo", game.getPlayers().get(1).getHand().size());
                response.put("handThree", game.getPlayers().get(2).getHand().size());
                response.put("handFour", game.getPlayers().get(3).getHand().size());
                response.put("players", game.getPlayers());
                return response;
            }
        }

        //trimming hand normally
        Player player;
        player = game.getCurrentPlayer();
        for (String card : cards) {
            game.TrimCards(player, card);
        }

        //player trim after event
        if(game.getEventHappened()){
            response.put("message", "Cards discarded. Your turn ended! Please return.");
        }else {
            response.put("message", "Cards discarded. Please draw an event!");
        }

        response.put("message", "Cards trimmed. Please hit return.");
        response.put("currentPlayer", game.getCurrentPlayer());
        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());
        response.put("players", game.getPlayers());
        return response;
    }



    @PostMapping("/play")
    public Map<String, Object> playQuest(@RequestBody ArrayList<String> cards){
        Map<String, Object> response = new HashMap<>();

        if(!cards.contains("No")){
            for (String card : cards) {
                game.participantStageCards(card);
            }
        }

        int trim = game.trimAmount(game.getChallenger());
        if (trim > 0) {
            response.put("currentPlayer", game.getChallenger());
            response.put("message", "Please trim your hand first.");
            return response;
        }


        game.participantPassed(game.getCurrentStage());
        game.updateChallengerCompleted();

        //quest failed - Sponsor draws and trim if needed
        if(game.noChallenger()){
            //reset part
            game.setCounts();
            game.resetChallengerTracker();
            game.sponsorGetCards();

            trim = game.trimAmount(game.getSponsorPlayer());
            if(trim > 0){
                response.put("currentPlayer", game.getSponsorPlayer());
                response.put("message", "No more participants. Quest failed! Sponsor trim hand by " + trim + " cards!");
            }else{
                game.clearQuest();
                response.put("currentPlayer", game.getCurrentPlayer());
                response.put("message", "No more participants. Quest failed! Your turn ended. Please hit return.");
            }

            response.put("handOne", game.getPlayers().get(0).getHand().size());
            response.put("handTwo", game.getPlayers().get(1).getHand().size());
            response.put("handThree", game.getPlayers().get(2).getHand().size());
            response.put("handFour", game.getPlayers().get(3).getHand().size());
            response.put("players", game.getPlayers());
            return response;

        }//end if quest fails

        //all challengers went once
        if(game.getChallengerCompleted() == game.getChallengerSize()) {

            //quest completed - Participants update shields, check winner and sponsor trim
            if (game.getCurrentStage() == game.getTotalStage()) {
                //update shield, update sponsor hand
                game.endQuestUpdate();



                trim = game.trimAmount(game.getSponsorPlayer());
                if (trim > 0) {
                    response.put("message", "Quest passed! Shields are updated. Sponsor trim hand by " + trim + " cards!");
                    response.put("currentPlayer", game.getSponsorPlayer());
                } else {

                    if (!game.checkWinners().isEmpty()) {
                        response.put("message", "Game ended! Winner of the game: " + game.getWinnerNames());
                        response.put("handOne", game.getPlayers().get(0).getHand().size());
                        response.put("handTwo", game.getPlayers().get(1).getHand().size());
                        response.put("handThree", game.getPlayers().get(2).getHand().size());
                        response.put("handFour", game.getPlayers().get(3).getHand().size());
                        response.put("players", game.getPlayers());
                        response.put("currentPlayer", game.getCurrentPlayer());
                        return response;
                    }

                    response.put("message", "Quest completed and shields are updated. Your turn ended. Please hit return.");
                    response.put("currentPlayer", game.getCurrentPlayer());
                }



                //stage completed - update challenger size, reset challenger completed and update current stage, participants draw cards
            } else {
                game.resetChallengerCompleted();
                game.updateChallengerSize();
                game.updateCurrentStage();
                game.newStageDrawCards();

                int stage = game.getCurrentStage()+1;
                response.put("message", "Stage completed! Every participant draws one card. Enter cards to play stage " + stage + ".");
                response.put("currentPlayer", game.getChallenger());
            }


            response.put("handOne", game.getPlayers().get(0).getHand().size());
            response.put("handTwo", game.getPlayers().get(1).getHand().size());
            response.put("handThree", game.getPlayers().get(2).getHand().size());
            response.put("handFour", game.getPlayers().get(3).getHand().size());
            response.put("players", game.getPlayers());

            return response;

        }//end of all challenger went once

        //more challenger to go
        if(game.getChallengerCompleted() < game.getChallengerSize()) {

            trim = game.trimAmount(game.getChallenger());
            if (trim > 0) {
                response.put("currentPlayer", game.getChallenger());
                response.put("message", "Please trim your hand first.");
                response.put("handOne", game.getPlayers().get(0).getHand().size());
                response.put("handTwo", game.getPlayers().get(1).getHand().size());
                response.put("handThree", game.getPlayers().get(2).getHand().size());
                response.put("handFour", game.getPlayers().get(3).getHand().size());
                response.put("players", game.getPlayers());
                return response;

            }
            response.put("currentPlayer", game.getChallenger());
            int stage = game.getCurrentStage() + 1;
            response.put("message", "Enter cards to play stage " + stage + ".");

        }

        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());
        response.put("players", game.getPlayers());
        return response;
    }


    @GetMapping("/check")
    public Map<String, Object> printWinner(){
        Map<String, Object> response = new HashMap<>();
        response.put("message",", winner: " + game.getWinnerNames());
        return response;
    }

    @GetMapping("/return")
    public Map<String, Object> nextRound(){
        Map<String, Object> response = new HashMap<>();

        game.nextRound();
        int trim = game.trimAmount(game.getCurrentPlayer());
        if (trim > 0) {
            response.put("message", "Please trim your hand by " + trim + " cards!");
        }else{
            response.put("message","Please draw an event card!");
        }
        response.put("currentPlayer", game.getCurrentPlayer());
        //no event yet for returned player
        game.switchEventHappened();
        //clear questPlayer list if return after quest
        game.cleanQuestPlayers();

        //hand size
        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("players", game.getPlayers());

        return response;
    }

    @GetMapping("/tOne")
    public Map<String, Object> testOne() {
        Map<String, Object> response = new HashMap<>();


        game.initializePlayers();
        game.initializeTestOne();
        game.nextRound();


        response.put("players", game.getPlayers());
        response.put("currentPlayer", game.getCurrentPlayer());

        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("message", "Game started! Please draw an event card.");
        return response;
    }

    @GetMapping("/tTwo")
    public Map<String, Object> testTwo(){
        Map<String, Object> response = new HashMap<>();
        game.initializePlayers();
        game.initializeTestTwo();
        game.nextRound();

        response.put("players", game.getPlayers());
        response.put("currentPlayer", game.getCurrentPlayer());

        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("message", "Game started! Please draw an event card.");

        return response;
    }


    @GetMapping("/tThree")
    public Map<String, Object> testThree(){
        Map<String, Object> response = new HashMap<>();
        game.initializePlayers();
        game.initializeTestThree();
        game.nextRound();

        response.put("players", game.getPlayers());
        response.put("currentPlayer", game.getCurrentPlayer());

        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("message", "Game started! Please draw an event card.");

        return response;
    }

    @GetMapping("/tFour")
    public Map<String, Object> testFour(){
        Map<String, Object> response = new HashMap<>();

        game.initializePlayers();
        game.initializeTestFour();


        game.nextRound();

        response.put("players", game.getPlayers());
        response.put("currentPlayer", game.getCurrentPlayer());

        response.put("handOne", game.getPlayers().get(0).getHand().size());
        response.put("handTwo", game.getPlayers().get(1).getHand().size());
        response.put("handThree", game.getPlayers().get(2).getHand().size());
        response.put("handFour", game.getPlayers().get(3).getHand().size());

        response.put("message", "Game started! Please draw an event card.");

        return response;
    }
}










