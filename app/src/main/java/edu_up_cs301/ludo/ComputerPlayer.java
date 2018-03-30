package edu_up_cs301.ludo;

import android.graphics.Color;
import android.util.Log;

import edu_up_cs301.game.GameComputerPlayer;
import edu_up_cs301.game.infoMsg.GameInfo;
import edu_up_cs301.game.infoMsg.IllegalMoveInfo;
import edu_up_cs301.game.infoMsg.NotYourTurnInfo;

/**
 * @Author: Avery Guillermo
 * created and implemented by Avery Guillermo
 * This is the super dumb AI
 */

public class ComputerPlayer extends GameComputerPlayer {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 578920931238746L;

    //instance variables

    /**
     * constructor
     *
     * @param name
     */
    public ComputerPlayer(String name){
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        // if it's not a LudoState message, ignore it; otherwise
        // cast it
        if (!(info instanceof LudoState)) return;
        LudoState myState = (LudoState)info;

        // if it's not our move, ignore it
        if (myState.getWhoseMove() != this.playerNum) return;

        // sleep for a second to make any observers think that we're thinking
        sleep(1000);

        //if it is the computer's turn to roll
        if(myState.getWhoseMove() == this.playerNum){

            if(myState.getIsRollable()) {
                Log.i("Computer Player: " + this.playerNum, "Rolling the dice");
                game.sendAction(new ActionRollDice(this));
            }

            int index;

            //if it needs to bring piece out of start
            index = myState.getTokenIndexOfFirstPieceInStart(this.playerNum);
            if (index != -1) {
                Log.i("Computer Player: " + this.playerNum, "Moving piece out of Start");
                game.sendAction(new ActionRemoveFromBase(this, index));
                return;
            }

            //if the computer needs to move a piece
            index = myState.getTokenIndexOfFirstPieceOutOfStart(this.playerNum);
            if (index != -1) {
                Log.i("Computer Player: " + this.playerNum, "Moving Piece forward on the board");
                game.sendAction(new ActionMoveToken(this, index));
                return;
            }

        }


    }

}
