package edu_up_cs301.ludo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu_up_cs301.game.GameHumanPlayer;
import edu_up_cs301.game.GameMainActivity;
import edu_up_cs301.game.R;
import edu_up_cs301.game.actionMsg.GameAction;
import edu_up_cs301.game.infoMsg.GameInfo;
import edu_up_cs301.game.infoMsg.IllegalMoveInfo;
import edu_up_cs301.game.infoMsg.NotYourTurnInfo;

/**
 * contains gui
 *
 * @Author: Avery Guillermo--> Edited on March 27, 2018
 *
 * TODO: everybody needs access to pieces, but only a copy of them,
 */

public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {

    // to satisfy Serializable interface
    private static final long serialVersionUID = 3548793282648392873L;


    private LudoSurfaceView surfaceView;
    private Button rollDiceButton;
    private int[][] redPath;
    private int[][] greenPath;
    private int[][] bluePath;
    private int[][] yellowPath;

    // most recent state, an appropriately filled view of the game as given to us from LudoLocalGame

    private LudoState state = new LudoState(); //IMPORTANT! For Now, create a new Ludo State

    // the android activity that we are running
    private GameMainActivity myActivity;




    /**
     * constructor
     *
     * @param name of player
     */
    public HumanPlayer(String name) {
        super(name);
        generalInit();
    }

    private void generalInit() {

        redPath = new int[][]{{1, 6}, {2, 6},
                {3, 6}, {4, 6}, {5, 6}, {6, 5}, {6, 4}, {6, 3},
                {6, 2}, {6, 1}, {6, 0}, {7, 0}, {8, 0},
                {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5},
                {9, 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6},
                {14, 7}, {14, 8}, {13, 8}, {12, 8}, {11, 8}, {10, 8},
                {9, 8}, {8, 9}, {8, 10}, {8, 11}, {8, 12}, {8, 13},
                {8, 14}, {7, 14}, {6, 14}, {6, 13}, {6, 12}, {6, 11},
                {6, 10}, {6, 9}, {5, 8}, {4, 8}, {3, 8}, {2, 8},
                {1, 8}, {0, 8}, {0, 7}, {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}};

        greenPath = new int[][]{{8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5},
                {9, 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6},
                {14, 7}, {14, 8}, {13, 8}, {12, 8}, {11, 8}, {10, 8},
                {9, 8}, {8, 9}, {8, 10}, {8, 11}, {8, 12}, {8, 13},
                {8, 14}, {7, 14}, {6, 14}, {6, 13}, {6, 12}, {6, 11},
                {6, 10}, {6, 9}, {5, 8}, {4, 8}, {3, 8}, {2, 8},
                {1, 8}, {0, 8}, {0, 7}, {0, 6}, {1, 6}, {2, 6},
                {3, 6}, {4, 6}, {5, 6}, {6, 5}, {6, 4}, {6, 3},
                {6, 2}, {6, 1}, {6, 0}, {7, 0}, {7, 1}, {7, 2},
                {7, 3}, {7, 4}, {7, 5}, {7, 6}};

        bluePath = new int[][]{{6, 13}, {6, 12}, {6, 11},
                {6, 10}, {6, 9}, {5, 8}, {4, 8}, {3, 8}, {2, 8},
                {1, 8}, {0, 8}, {0, 7}, {0, 6}, {1, 6}, {2, 6},
                {3, 6}, {4, 6}, {5, 6}, {6, 5}, {6, 4}, {6, 3},
                {6, 2}, {6, 1}, {6, 0}, {7, 0}, {8, 0},
                {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5},
                {9, 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6},
                {14, 7}, {14, 8}, {13, 8}, {12, 8}, {11, 8}, {10, 8},
                {9, 8}, {8, 9}, {8, 10}, {8, 11}, {8, 12}, {8, 13},
                {8, 14}, {7, 14}, {7, 13}, {7, 12}, {7, 11}, {7, 10}, {7, 9}, {7, 8}};

        yellowPath = new int[][]{{13, 8}, {12, 8}, {11, 8}, {10, 8},
                {9, 8}, {8, 9}, {8, 10}, {8, 11}, {8, 12}, {8, 13},
                {8, 14}, {7, 14}, {6, 14}, {6, 13}, {6, 12}, {6, 11},
                {6, 10}, {6, 9}, {5, 8}, {4, 8}, {3, 8}, {2, 8},
                {1, 8}, {0, 8}, {0, 7}, {0, 6}, {1, 6}, {2, 6},
                {3, 6}, {4, 6}, {5, 6}, {6, 5}, {6, 4}, {6, 3},
                {6, 2}, {6, 1}, {6, 0}, {7, 0}, {8, 0},
                {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5},
                {9, 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6},
                {14, 7}, {13, 7}, {12, 7}, {11, 7}, {10, 7}, {9, 7}, {8, 7}};

    }

    /**
     * Sets this player as the one attached to the GUI. Saves the
     * activity, links listeners to IO to invoke specific methods.
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        //remember the activity
        myActivity = activity;

        activity.setContentView(R.layout.ludo_game_view);

        //find references
        surfaceView = (LudoSurfaceView) activity.findViewById(R.id.board_canvas);
        rollDiceButton = (Button) activity.findViewById(R.id.button_Roll);
        //set the listeners
        surfaceView.setOnTouchListener(this);
        rollDiceButton.setOnClickListener(this);

    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }


    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {

        if (surfaceView == null) return;


        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof LudoState))
            // if we do not have a LudoState, ignore
            return;
        else {
            surfaceView.setState((LudoState)info);
            state = (LudoState)info;
            surfaceView.invalidate();
            Log.i("human player", "receiving");
        }

    }

    /*
     * Implemented by Avery Guillermo
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //make sure that the thing that was touched was the surface view
        if(v.getId() == surfaceView.getId()){
            float xTouch = event.getX();
            float yTouch = event.getY();
            float width = surfaceView.getWidth();
            float box = width / 15;

            //create instance of gameAction
            GameAction action = null;
            int index;
            index = getIndexOfPieceTouched(xTouch, yTouch, box);

            if(index == -1){
                Log.i("OnTouch", "No piece was pressed");
            }
            else{
                Log.i("OnTouch", "The piece that was touched was: " + index);
                //TODO: send game actions here now that we have the id of the pieces array of the piece that was pressed
                //TODO: can either send move piece actions or move piece out of start depending on the if the base is out of start
                if(checkIfAHomeBaseWasTouched(xTouch, yTouch,box)==false){ //the user is trying to move a piece forward
                    action = new ActionMoveToken(this, index);
                    game.sendAction(action);
                }
                else{ // the user is trying to move a piece out of start base
                    action = new ActionRemoveFromBase(this, index);
                    game.sendAction(action);
                }
            }
        }

/*
        switch(v.getId()){
            case R.id.board_canvas: // if surfaceView is pressed, tell the game
                state.newRoll();
                action = new ActionMoveToken(this, index);
                game.sendAction(action);
                return true;
        }
        */



        return false;
    }

    //TODO: OPTIMIZE when we assign the paths to the player
    //TODO: This was just to see that when all the pieces were touched, we get the index back
    public int getIndexOfPieceTouched(float xTouch, float yTouch, float box){
        boolean aHomeBaseWasTouched = checkIfAHomeBaseWasTouched(xTouch, yTouch, box);

        float xPos, yPos;
        for(int i =0; i<16; i++) { //traverse through all the pieces
            if (aHomeBaseWasTouched == false) {//check the board tiles
                if (state.pieces[i].getOwner() == 0) {//use the red path
                    xPos = (redPath[state.pieces[i].getNumSpacesMoved()][0]) * box;
                    yPos = (redPath[state.pieces[i].getNumSpacesMoved()][1]) * box;
                    if ((xTouch >= xPos) && (xTouch <= (xPos + box))) {
                        if ((yTouch >= yPos) && (yTouch <= (yPos + box))) {
                            return i;
                        }
                    }
                } if (state.pieces[i].getOwner() == 1) {//use the green path
                    xPos = (greenPath[state.pieces[i].getNumSpacesMoved()][0]) * box;
                    yPos = (greenPath[state.pieces[i].getNumSpacesMoved()][1]) * box;
                    if ((xTouch >= xPos) && (xTouch <= (xPos + box))) {
                        if ((yTouch >= yPos) && (yTouch <= (yPos + box))) {
                            return i;
                        }
                    }

                } if (state.pieces[i].getOwner() == 2) {//use the blue path
                    xPos = (bluePath[state.pieces[i].getNumSpacesMoved()][0]) * box;
                    yPos = (bluePath[state.pieces[i].getNumSpacesMoved()][1]) * box;
                    if ((xTouch >= xPos) && (xTouch <= (xPos + box))) {
                        if ((yTouch >= yPos) && (yTouch <= (yPos + box))) {
                            return i;
                        }
                    }
                } if (state.pieces[i].getOwner() == 3) {//use the yellow path
                    xPos = (yellowPath[state.pieces[i].getNumSpacesMoved()][0]) * box;
                    yPos = (yellowPath[state.pieces[i].getNumSpacesMoved()][1]) * box;
                    if ((xTouch >= xPos) && (xTouch <= (xPos + box))) {
                        if ((yTouch >= yPos) && (yTouch <= (yPos + box))) {
                            return i;
                        }
                    }
                }
            }
            else {//check the pieces in the start tiles
                float x, y;
                if (state.pieces[i].getOwner() == 0) {//red piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    if ((xTouch >= x) && (xTouch <= (x + box))) {
                        if ((yTouch >= y) && (yTouch <= (y + box))) {
                            return i;
                        }
                    }
                }
                if (state.pieces[i].getOwner() == 1) {//green piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    if ((xTouch >= x) && (xTouch <= (x + box))) {
                        if ((yTouch >= y) && (yTouch <= (y + box))) {
                            return i;
                        }
                    }

                }
                if (state.pieces[i].getOwner() == 2) {//blue piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    if ((xTouch >= x) && (xTouch <= (x + box))) {
                        if ((yTouch >= y) && (yTouch <= (y + box))) {
                            return i;
                        }
                    }
                }
                if (state.pieces[i].getOwner() == 3) {//yellow piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    if ((xTouch >= x) && (xTouch <= (x + box))) {
                        if ((yTouch >= y) && (yTouch <= (y + box))) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;//return negative 1 if no pieces were pressed!
    }

    public boolean checkIfAHomeBaseWasTouched(float xTouch, float yTouch, float box){

        //first check if the coordinates touched were in start base or actually on the board coordinates
        if((xTouch> (box*0)) && (xTouch< (box*6)) && (yTouch> (box*0)) && (yTouch< (box*6))){
            return true;
        }
        else if ((xTouch> (box*9)) && (xTouch< (box*15)) && (yTouch> (box*0)) && (yTouch< (box*6))){
            return true;
        }
        else if ((xTouch> (box*0)) && (xTouch< (box*6)) && (yTouch> (box*9)) && (yTouch< (box*15))){
            return true;
        }
        else if ((xTouch> (box*9)) && (xTouch< (box*15)) && (yTouch> (box*9)) && (yTouch< (box*15))){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public void onClick(View v) {

        //create instance of gameAction associated with button pressed
        GameAction action = null;

        if(v.getId() == rollDiceButton.getId()) {
            action = new ActionRollDice(this);
            Log.i("Onclick", "Human Player Rolling Dice");
            game.sendAction(action);
        }
    }

}

