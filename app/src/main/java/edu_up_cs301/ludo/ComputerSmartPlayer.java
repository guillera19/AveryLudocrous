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

    @Override
    protected void receiveInfo(GameInfo info) {
        Log.i("smart computer recieve", " " + playerNum);
        // if it's not a LudoState message, ignore it; otherwise
        // cast it
        if (!(info instanceof LudoState)) return;
        LudoState myState = (LudoState)info;
        // if it's not our move, ignore it
        if (myState.getWhoseMove() != this.playerNum) return;
        // sleep for 0.3 seconds to slow down the game
        sleep(300);
        //if it is the computer's turn to roll
        if(myState.getWhoseMove() == this.playerNum){
            if(myState.getIsRollable()) {
                Log.i("Computer Player: " + this.playerNum, "Rolling the dice");
                game.sendAction(new ActionRollDice(this));
            }
            int index;
            index = myState.getTokenIndexOfFirstPieceOutOfStart(this.playerNum);
            //if the computer needs to move a piece
            if (index != -1) {
                game.sendAction(new ActionMoveToken(this, index));
                return; //return because if the player made a move, then it can't possibly bring a piece out of base
            }
            //if it needs to bring piece out of start
            index = myState.getTokenIndexOfFirstPieceInStart(this.playerNum);
            if (index != -1) {
                game.sendAction(new ActionRemoveFromBase(this, index));
            }
        }
    }
}
