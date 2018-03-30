package edu_up_cs301.ludo;

import edu_up_cs301.game.GameComputerPlayer;
import edu_up_cs301.game.infoMsg.GameInfo;

/**
 * Created by Luke on 3/4/2018.
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

    }
}
