package edu_up_cs301.ludo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
 import edu_up_cs301.game.GamePlayer;
import edu_up_cs301.game.actionMsg.GameAction;
import edu_up_cs301.game.infoMsg.GameState;

/**
 * This contains the official state for the Ludo Game.
 * <p>
 *
 *
 * based on GameFramework by Steven R. Vegdahl
 * CounterGame
 * TickTackToe2
 * PigGame - implemented by us in lab
 *
 * @author Raj Nayyar
 * @author Avery Guillermo
 * @author Luke Danowski
 */

public class LudoState extends GameState {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 1433248382648392873L;
    //instance variables
    private Random dice;
    private int diceVal;
    private boolean isRollable;
    Token[] pieces;
    private int numPlayers;
    private int playerID_active;
    private int[] playerScore;
    private boolean stillPlayersTurn;
    private boolean canBringOutOfStart;
    private boolean canMovePiece;
    private boolean killedAPiece;
    private boolean scoredAPoint;

    /**
     * Fixed by Avery Guillermo!
     *
     * constructor, initializing from parameter
     * <p>
     * the value to which the counter's value should be initialized
     */
    public LudoState() {
        scoredAPoint = false;
        killedAPiece = false;
        canMovePiece = false;
        stillPlayersTurn = false;
        canBringOutOfStart = false;
        playerID_active = 0;
        numPlayers = 4;
        dice = new Random();
        diceVal = 1;
        isRollable = true;
        pieces = new Token[]{
                new Token(0, 3, 1.5), //red piece1
                new Token(0, 4.5, 3), //red piece2
                new Token(0, 3, 4.5), //red piece3
                new Token(0, 1.5, 3), //red piece4
                new Token(1, 12, 1.5), //green piece1
                new Token(1, 13.5, 3), //green piece2
                new Token(1, 12, 4.5), //green piece3
                new Token(1, 10.5, 3), //green piece4
                new Token(2, 12, 10.5), //yellow piece1
                new Token(2, 13.5, 12), //yellow piece2
                new Token(2, 12, 13.5), //yellow piece3
                new Token(2, 10.5, 12), //yellow piece4
                new Token(3, 3, 10.5), //blue piece1
                new Token(3, 4.5, 12), //blue piece2
                new Token(3, 3, 13.5), //blue piece3
                new Token(3, 1.5, 12)}; //blue piece4

        //scores are 0;
        playerScore = new int[]{0, 0, 0, 0};
    }


    /**
     * Fixed by Avery Guillermo!
     *
     * copy constructor; makes a copy of the original object
     *
     * @param original the object from which the copy should be made
     */
    public LudoState(LudoState original) {
        this.scoredAPoint = original.scoredAPoint;
        this.killedAPiece = original.killedAPiece;
        this.canMovePiece = original.canMovePiece;
        this.numPlayers = original.numPlayers;
        this.playerID_active = original.playerID_active;
        this.dice = original.dice;
        this.diceVal = original.diceVal;
        this.isRollable = original.isRollable;
        this.stillPlayersTurn = original.stillPlayersTurn;

        //set the piece array
        this.pieces = new Token[original.pieces.length];
        for( int i=0; i<original.pieces.length; i++) {
            pieces[i] = new Token(original.pieces[i]);
        }

        //set the scores
        this.playerScore = new int[original.playerScore.length];
        for(int i=0; i<original.playerScore.length; i++){
            this.playerScore[i] = original.playerScore[i];
        }

    }

    /**
     * Tells whose move it is.
     *
     * @return the index (0 or 1) of the player whose move it is.
     */
    public int getWhoseMove() {
        return playerID_active;
    }


    /**
     * getter method for score
     * @return score array
     */
    public int getPlayerScore(int index){
        return playerScore[index];
    }

    /**
     * getter method for numPlayers
     * @return number of active players
     */
    public int getNumPlayers(){
        return numPlayers;
    }

    /**
     * generates a new random roll if player is allowed to play.
     * considers if it is their turn and if roll a 6, they get to roll again
     *
     //     * @param playerID who sent action
     * @return is move possible with current roll
     */
    public boolean newRoll() {
        if (isRollable) {
            //handle the case of rerolls on a 6 with a switch
            switch (diceVal = dice.nextInt(6) + 1) {
                case 6:
                    isRollable = false; //so it stays true;
                default:
                    isRollable = !isRollable;
            }
            //if no moves exist based on diceVal just go to next player
            updateMovesAvailable();
            return true;
        }
        return false;
    }

    /**
     * Fixed by Avery Guillermo: Didn't handle the case of in homestretch and diceVal + numSpacesMoved != HomeBase
     *
     * Called after a dice roll. We go through the active player's pieces and checks for pieces
     * which can move by the rules of the game, each piece has a boolean which is changed to
     * reflect the outcome of the check;
     * @return true if valid moves exist
     */
    private boolean updateMovesAvailable() {

        //checks if any moves available;
        boolean oneTrue = false;

        //set movable boolean
        for (int i = (playerID_active*4); i < (playerID_active*4 +4); i ++) {
            //first, check the pieces in home base
            if (pieces[i].getIsHome()) {
                if (diceVal == 6) {
                    //is in home base and a 6 has been rolled
                    pieces[i].setIsMovable(true);
                    oneTrue = true;
                } else {
                    pieces[i].setIsMovable(false);
                }
            }
            else if (pieces[i].getNumSpacesMoved() < 51) {
                //in normal route
                pieces[i].setIsMovable(true);
                oneTrue = true;
            }
            else if (pieces[i].getNumSpacesMoved() + diceVal < 57) {
                //in home stretch and total will be less than or equal to 56
                pieces[i].setIsMovable(true);
                oneTrue = true;
            }
            else if (pieces[i].getNumSpacesMoved() + diceVal > 57){
                pieces[i].setIsMovable(false);

            }
            else {
                pieces[i].setIsMovable(false);
            }
        }

        return oneTrue;
    }


    /**
     * increments player score
     */
    public void incPlayerScore() {
        playerScore[playerID_active]++;
    }

    /**
     * Fixed By Avery Guillermo!
     *
     * returns the number of pieces which are in play
     //     * @param playerID
     * @return
     */
    public int getNumMovableTokens(int playerID) {
        int count = 0;
        for (int i = (playerID*4); i < (playerID*4 + 4); i++) {
            if (pieces[i].getIsHome() == false && pieces[i].getReachedHomeBase() == false && pieces[i].getIsMovable() == true){
                int currentLocation = pieces[i].getNumSpacesMoved();
                int homeBaseLocation = 56;
                if(currentLocation>50){ //Inside homestretch
                    if(diceVal+currentLocation <= homeBaseLocation){
                        count++;
                    }
                    // else don't increment because it can't move
                }
                else{
                    count++;
                }
            }
        }
        return count;
    }

    //implemented by Avery
    public void changePlayerTurn(){
        this.stillPlayersTurn = false;

        if ((++playerID_active) >= numPlayers) {
            playerID_active = 0;
        }
        this.isRollable = true;
    }

    public void setIsRollable(boolean bol){
        this.isRollable = bol;
    }


    /**Edited by Avery Guillermo
     *
     * @param playerID
     * @param index
     * @return
     */
    public boolean advanceToken(int playerID, int index) {

        int indexOfCurrentToken = index;

        //only act on movable pieces. Always updated after each dice roll.
        if(pieces[indexOfCurrentToken].getIsMovable()) {

            int currentLocation = pieces[indexOfCurrentToken].getNumSpacesMoved();
            int homeBaseLocation = 56;
            //Inside homestretch
            if(currentLocation>50){
                if(diceVal+currentLocation == homeBaseLocation){
                    pieces[indexOfCurrentToken].incNumSpacesMoved(diceVal);
                    incPlayerScore();
                    pieces[indexOfCurrentToken].setReachedHomeBase(true);
                    this.scoredAPoint = true;
                    Log.i("Player Scored a Point","His score is now "+playerScore[playerID]);
                }
                else if(diceVal+currentLocation < homeBaseLocation){
                    pieces[indexOfCurrentToken].incNumSpacesMoved(diceVal);
                }

            }
            else{
                pieces[indexOfCurrentToken].incNumSpacesMoved(diceVal);
            }


            int spacesPieceHasTraveled = pieces[indexOfCurrentToken].getNumSpacesMoved();

            // check for overlap on non-safe tiles
            if (spacesPieceHasTraveled < 51) {// 51 is the number of tiles till homestretch

                //consider where piece is
                switch (spacesPieceHasTraveled) {
                    //safe tiles
                    case 8:
                    case 13:
                    case 21:
                    case 26:
                    case 34:
                    case 39:
                    case 47:
                        break;  //do nothing
                    default:    //else
                        //check to see if we overlap and need to kick another piece back home
                        //iterate through all active pieces
                        for (int i = 0; i < 16; i++) {
                            if (    pieces[i].getOwner() != playerID_active &&  //not one of my own
                                    !pieces[i].getIsHome() &&                   //is in play
                                    pieces[i].getCurrentXLoc() == pieces[indexOfCurrentToken].getCurrentXLoc() &&
                                    pieces[i].getCurrentYLoc() == pieces[indexOfCurrentToken].getCurrentYLoc())
                            {
                                //changes boolean and numSpacesMoved back to 0
                                pieces[i].setIsHome(true);
                                killedAPiece = true;
                                Log.i("A Piece was killed ", "The owner of the piece was: " + pieces[i].getOwner());
                            }
                        }
                }
            }
            //determine whether or not to change the player's turn
//            if(diceVal == 6 || this.killedAPiece == true || this.scoredAPoint == true){
//                //According to Ludo Rules, if the player either rolled a six, killed a piece, or the player scored a point
//                // then grant them another turn!
//                stillPlayersTurn = true;
//                isRollable = true;
//            }
//            else{//diceVal != 6
//                //Change the current player's turn!
//                changePlayerTurn();
//            }

            if(diceVal != 6 && this.killedAPiece == false && this.scoredAPoint == false){
                //Change the current player's turn!
                changePlayerTurn();
            }
            else if(this.killedAPiece == true || this.scoredAPoint == true){
                //According to Ludo Rules, if the player either rolled a six, killed a piece, or the player scored a point
                // then grant them another turn!
                stillPlayersTurn = true;
                isRollable = true;
            }
            else{ // diceVal == 6
                //the player rolled a six, so they get to roll again!
                stillPlayersTurn = true;
                isRollable = true;
            }
            //reset all the boolean variables for the next roll
            canBringOutOfStart = false;
            canMovePiece = false; //now that the player moved the token, the player can't use the diceVal to move anymore!
            killedAPiece = false;
            scoredAPoint = false;

            return true;
        }
        else {
            //do nothing
            return true;
        }
    }



    //implemented by Avery!
    public int getTokenIndexOfFirstPieceInStart(int playerID){
        for(int i = (playerID*4); i<(playerID*4 + 4); i++){//traverse through the only the pieces the player owns
            if(pieces[i].getIsHome() == true){
                return i;
            }
        }
        return -1;

    }

    //implemented by Avery!
    //used for the computer player!
    public int getTokenIndexOfFirstPieceOutOfStart(int playerID){
        for(int i = (playerID*4); i<(playerID*4 + 4); i++){//traverse through the only the pieces the player owns
            if(pieces[i].getIsHome() == false && pieces[i].getReachedHomeBase() == false && pieces[i].getIsMovable() == true){
                return i;
            }
        }
        return -1;

    }


    @Override
    public String toString()
    {

        String output = "";
        for (int i = 0; i < 4; i++) {
            output+= "\nPlayer " + pieces[i].getOwner() + ": ";
            for (int j = i; j < 16; j += 4) {
                output+= "\nPiece " + (j-i)/4 + " at " + pieces[j].getNumSpacesMoved() +
                        " and " + (pieces[j].getIsHome()? "is home." : "is not home.");
            }

        }

        return output;
    }

    /**
     * getter method for dice value
     *
     * @return value of dice
     */
    public int getDiceVal() {
        return this.diceVal;
    }

    /**
     * getter method for roll boolean
     * @return true if allowed to make roll
     */
    public boolean getIsRollable() {
        return isRollable;
    }


    public void setStillPlayersTurn(boolean b){
        this.stillPlayersTurn = b;
    }

    public boolean getStillPlayersTurn(){
        return this.stillPlayersTurn;
    }

    public void setCanBringOutOfStart(boolean b){
        this.canBringOutOfStart = b;
    }

    public boolean isCanBringOutOfStart() {
        return canBringOutOfStart;
    }

    public void setCanMovePiece(boolean b){
        this.canMovePiece = b;
    }

    public boolean getCanMovePiece(){
        return this.canMovePiece;
    }
}
