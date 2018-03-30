package edu_up_cs301.ludo;

import edu_up_cs301.game.GamePlayer;
import edu_up_cs301.game.actionMsg.GameAction;

/**
 * Edited by Avery Guillermo on 3/28/19
 */

public class ActionRemoveFromBase extends GameAction {

    int index;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ActionRemoveFromBase(GamePlayer player, int index) {
        super(player);
        this.index = index;
    }

    public int getIndex(){ return index; }


}
