package edu_up_cs301.ludo;

import android.util.Log;

import edu_up_cs301.game.GameComputerPlayer;
import edu_up_cs301.game.infoMsg.GameInfo;

/**
 * ComputerSmartPlayer
 * This will contain the smart AI
 * Has Not Been Implemented
 */

public class ComputerSmartPlayer extends GameComputerPlayer {


    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ComputerSmartPlayer(String name) {
        super(name);
    }

    int indexFirstPiece, outOfBaseIndex, indexFirstPieceInStart, getIndexFirstPieceOutOfStart;
    int furthestPieceTravelled;

    @Override
    protected void receiveInfo(GameInfo info) {
        Log.i("smart computer recieve", " " + playerNum);
        // if it's not a LudoState message, ignore it; otherwise
        // cast it
        if (!(info instanceof LudoState)) return;
        LudoState myState = (LudoState) info;
        // if it's not our move, ignore it
        if (myState.getWhoseMove() != this.playerNum) return;
        // sleep for 0.3 seconds to slow down the game
        sleep(500);
        //if it is the computer's turn to roll
        if (myState.getWhoseMove() == this.playerNum) {
            if (myState.getIsRollable()) {
                Log.i("Computer Player: " + this.playerNum, "Rolling the dice");
                game.sendAction(new ActionRollDice(this));
                return;
            }

            indexFirstPiece = myState.getIndexOfFirstPlayerPiece(this.playerNum);
            indexFirstPieceInStart = myState.getTokenIndexOfFirstPieceInStart(this.playerNum);
            furthestPieceTravelled = myState.getPieceFurthestTravelled(this.playerNum);
            outOfBaseIndex = 0;
            for (int i = indexFirstPiece; i < (indexFirstPiece + 4); i++) {
                if (!(myState.pieces[i].getIsHome())) {
                    outOfBaseIndex++;
                }
            }
            Log.i("The_Number of Pieces Out of Base is: ", "" + outOfBaseIndex);

            getIndexFirstPieceOutOfStart = myState.getTokenIndexOfFirstPieceOutOfStart(this.playerNum);
            if (outOfBaseIndex == 0) {
                if (myState.getDiceVal() == 6) {

                    int[] order = myState.getOrder(this.playerNum);
                    Log.i("Order 0",""+order[0]);
                    Log.i("Order 1",""+order[1]);
                    Log.i("Order 2",""+order[2]);
                    Log.i("Order 3",""+order[3]);
                    game.sendAction(new ActionRemoveFromBase(this, indexFirstPieceInStart));
                    return;

                }
            }
            if (outOfBaseIndex > 0 && outOfBaseIndex < 4) {
                if (myState.getDiceVal() == 6) {
                    Log.i("First Piece In Start",""+indexFirstPieceInStart);
                    game.sendAction(new ActionRemoveFromBase(this, indexFirstPieceInStart));
                    return;
                }
                //if(myState.getDiceVal()!=6)
                else {
                    game.sendAction(new ActionMoveToken(this, determineWhichPieceToMove(myState)));
                    return;
                }
            }
            if (outOfBaseIndex == 4) {
                game.sendAction(new ActionMoveToken(this, determineWhichPieceToMove(myState)));
                return;
            }

        }
    }


    public int determineWhichPieceToMove(LudoState myState) {
        int pieceIndex, pieceScoreIndex; //The Players Pieces, The Player's Piece's Score.
        int[] pieceScoreArray = new int[]{0, 0, 0, 0};
        int[] safeSpaceArray = new int[]{8, 13, 21, 26, 34, 39, 47};
        int[] order = myState.getOrder(this.playerNum);
        boolean[] isOnSafeSpace = new boolean[]{false, false, false, false};
        boolean[] willBeOnSafeSpace = new boolean[]{false, false, false, false};
        boolean boardHalfTravelled = false;
        int quarter = 13, half = 26, threefourths = 39;
        int diceVal = myState.getDiceVal();

        for (pieceIndex = indexFirstPiece, pieceScoreIndex = 0; pieceIndex < (pieceIndex + 4) &&
                pieceScoreIndex<4 ; pieceIndex++, pieceScoreIndex++) {
            if (myState.pieces[pieceIndex].getIsMovable()) {
                Log.i("piece Index", "" + pieceIndex + "\n");
                int range = myState.pieces[pieceIndex].getNumSpacesMoved() + diceVal;


                //is the piece currently on a safe tile?
                for (int j = 0; j < 7; j++) {
                    if (myState.pieces[pieceIndex].getNumSpacesMoved() == safeSpaceArray[j]) {
                        isOnSafeSpace[pieceScoreIndex] = true;
                        if (j < 3) {
                            pieceScoreArray[pieceScoreIndex] = 1;
                            if (order[1] == pieceIndex) {
                                pieceScoreArray[pieceScoreIndex] = 2;
                            }
                        } else if (j < 6) {
                            pieceScoreArray[pieceScoreIndex] = 0;
                            if (order[1] == pieceIndex) {
                                pieceScoreArray[pieceScoreIndex] = 1;
                            }
                        } else if (j <= 7) {
                            pieceScoreArray[pieceScoreIndex] = 0;
                        }
                    }
                }

                //If range == home strech
                if (range > 51 && !(myState.pieces[pieceIndex].getNumSpacesMoved() < 51)) {
                    Log.i("Piece Range equals home-strech", "!");
                    pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 10;
                    return pieceIndex;
                }

                //If Safe Spaces are in reach
                for (int j = 0; j < 7; j++) {
                    if (range == safeSpaceArray[j]) {
                        Log.i("Safe Space", j + " is in range");
                        willBeOnSafeSpace[pieceScoreIndex] = true;
                        if (j < 3) {
                            pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 4;
                        } else if (j < 6) {
                            pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 6;
                        } else if (j <= 7) {
                            pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 8;
                        }
                    }
                }

                //If the HomeTile is in Reach
                if (range == 57) {
                    Log.i("Home Tile Is In Reach", "!");
                    pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 3;
                }
//            int firstPieceOutOfStart = myState.getTokenIndexOfFirstPieceOutOfStart(this.playerNum);
//            //if the computer needs to move a piece
//            if (firstPieceOutOfStart != -1 && pieceIndex == firstPieceOutOfStart) {
//                pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex]+1;
//            }

                //If Piece is in home-strech
                if (myState.pieces[pieceIndex].getNumSpacesMoved() > 51 && range != 57) {
                    pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 2;
                }

                //Ensuring that even the least ahead pieces can move

                //If farthest piece has moved more than a quarter and it is not on the home tile
                if (outOfBaseIndex > 1 && myState.pieces[order[3]].getNumSpacesMoved() >= quarter &&
                        myState.pieces[order[3]].getNumSpacesMoved() != 57 &&
                        isOnSafeSpace[pieceScoreIndex] == false) {
                    Log.i("furthest piece has gone farther an a quarter", ".");

                    //if second farthest is not in home
                    if (myState.pieces[order[2]].getIsHome() == false && pieceIndex == order[2]) {
                        Log.i("Piece 2 will most likely move", " ");
                        pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 3;
                    }

                    //if piece is on starting square
                    if (myState.pieces[pieceIndex].getNumSpacesMoved() == 0 &&
                            myState.pieces[pieceIndex].getIsHome() == false) {
                        pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 1;
                    }
                }

                //if the second farthest piece has moved more than half and it is not at the home tile
                if (outOfBaseIndex > 1 && myState.pieces[order[2]].getNumSpacesMoved() >= half &&
                        myState.pieces[order[2]].getNumSpacesMoved() != 57 &&
                        isOnSafeSpace[pieceScoreIndex] == false) {
                    Log.i("Second Furthest piece has gone farther an a half", ".");

                    if (myState.pieces[order[1]].getIsHome() == false && pieceIndex == order[1]) {
                        pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 4;
                    }
                }

                //If third farthest piece has moved more than half and it is not on the home tile
                if (outOfBaseIndex > 1 && myState.pieces[order[1]].getNumSpacesMoved() >= half &&
                        myState.pieces[order[1]].getNumSpacesMoved() != 57 &&
                        isOnSafeSpace[pieceScoreIndex] == false) {
                    Log.i("Third Furthest piece has gone farther an a half", ".");
                    if (!myState.pieces[order[0]].getIsHome() && pieceIndex == order[1]) {
                        pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 5;
                    }
                }

                //if the piece is not on a safe space and has moved more than three fourths of the board
                if (myState.pieces[pieceIndex].getNumSpacesMoved() > threefourths && !isOnSafeSpace[pieceScoreIndex]) {
                    pieceScoreArray[pieceScoreIndex] = pieceScoreArray[pieceScoreIndex] + 5;
                }

                if (myState.pieces[pieceIndex].getNumSpacesMoved() >= 57) {
                    pieceScoreArray[pieceScoreIndex] = 0;
                }

            }
        }
        int somePieceIsMovable=0;
        for(int i=0; i<4;i++){
            if(pieceScoreArray[i] >0){
                somePieceIsMovable++;
            }
        }
        if(somePieceIsMovable==0){
            if((myState.pieces[indexFirstPiece].getNumSpacesMoved()+myState.getDiceVal()<=57)){
                pieceScoreArray[0]=1;
            }
            else if((myState.pieces[indexFirstPiece+1].getNumSpacesMoved()+myState.getDiceVal())<=57){
                pieceScoreArray[1]=1;
            }

            else if((myState.pieces[indexFirstPiece+2].getNumSpacesMoved()+myState.getDiceVal())<=57){
                pieceScoreArray[2]=1;
            }

            else if((myState.pieces[indexFirstPiece+3].getNumSpacesMoved()+myState.getDiceVal())<=57){
                pieceScoreArray[3]=1;
            }
        }
        int greatestScore = 0;
        int pieceReturn =0;
        int k;
        for(k=0;k<4;k++){
            if(greatestScore <pieceScoreArray[k]){
                greatestScore = pieceScoreArray[k];
                pieceReturn = k;
            }
        }
        Log.i("==================","=======================");
        Log.i("Piece Index:",""+pieceIndex);
        Log.i("Dice Val",""+myState.getDiceVal());
        Log.i("pieceScoreArray[0]",""+pieceScoreArray[0]);
        Log.i("pieceScoreArray[1]",""+pieceScoreArray[1]);
        Log.i("pieceScoreArray[2]",""+pieceScoreArray[2]);
        Log.i("pieceScoreArray[3]",""+pieceScoreArray[3]);
        Log.i("greatest score",""+greatestScore);
        Log.i("returning",""+(indexFirstPiece+pieceReturn));
        Log.i("==================","=======================");
        Log.i("==================","=======================");
        Log.i("Order 0",""+order[0]);
        Log.i("Order 1",""+order[1]);
        Log.i("Order 2",""+order[2]);
        Log.i("Order 3",""+order[3]);
        Log.i("Furthest"+myState.getPieceFurthestTravelled(this.playerNum),"order[3]"+order[3]);
        Log.i("==================","=======================");
        if(myState.getPieceFurthestTravelled(this.playerNum) == order[3]){
            Log.i("THE FURTHEST PIECE MATCHES ORDER 3","");
        }
        Log.i("==================","=======================");

        return indexFirstPiece+pieceReturn;

    }
}