package edu_up_cs301.ludo;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu_up_cs301.game.GameHumanPlayer;
import edu_up_cs301.game.GameMainActivity;
import edu_up_cs301.game.R;
import edu_up_cs301.game.actionMsg.GameAction;
import edu_up_cs301.game.infoMsg.GameInfo;
import edu_up_cs301.game.infoMsg.IllegalMoveInfo;
import edu_up_cs301.game.infoMsg.NotYourTurnInfo;

/**
 * HumanPlayer
 * The human determines, through this class, which actions to send
 * specifically: which piece they want moved and if they roll a dice.
 */

public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener, Spinner.OnItemSelectedListener {
    // to satisfy Serializable interface
    private static final long serialVersionUID = 3548793282648392873L;
    private LudoSurfaceView surfaceView;
    private Button rollDiceButton;
    private Spinner songsSpinner;
    private String[] songList = {"Star Wars", "Weird Russian Song", "Darude", "Attack on Titan", "Lion King"};
    // most recent state, an appropriately filled view of the game as given to us from LudoLocalGame
    private LudoState state;
    // the android activity that we are running
    private GameMainActivity myActivity;
    MediaPlayer currentSong;

    /**
     * constructor
     *
     * @param name of player
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * setAsGui
     *
     * @param activity GameMainActivity object
     *                 Sets this player as the one attached to the GUI. Saves the
     *                 activity, links listeners to IO to invoke specific methods.
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

        songsSpinner = (Spinner) activity.findViewById(R.id.spinner_Songs);
        ArrayAdapter<String> songAdapter =
                new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, songList);
        songsSpinner.setAdapter(songAdapter);
        songsSpinner.setOnItemSelectedListener(this);

        /**
         External Citation
         Date:     April 16 2019
         Problem:  Did not know how to add music to App
         Resource: https://stackoverflow.com/questions/37244357/how-to-play-music-in-android-
         studio?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         Solution: I used the example code from this website. Some of the code below is from this website
         */
        currentSong = MediaPlayer.create(myActivity, R.raw.starwars);
        currentSong.setLooping(true);
        currentSong.start();

    }

    /**
     * getTopview
     *
     * @return the gui that is displayed currenty.
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * Callback method, called when player gets a message
     *
     * @param info the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (surfaceView == null) return;
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        } else if (!(info instanceof LudoState))
            // if we do not have a LudoState, ignore
            return;
        else {
            surfaceView.setState((LudoState) info);
            state = (LudoState) info;
            surfaceView.invalidate();
            Log.i("human player", "receiving");
            //make the roll dice visible or invisible depending on if its the player's turn
            if (state.getWhoseMove() == playerNum) {
                rollDiceButton.setAlpha(1f);
                rollDiceButton.setClickable(true);
            } else {
                rollDiceButton.setAlpha(0.4f);
                rollDiceButton.setClickable(false);
            }

        }
    }

    /**
     * onTouch
     *
     * @param v     View object
     * @param event Event Object
     * @return Always returns true
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //make sure that the thing that was touched was the surface view
        if (v.getId() == surfaceView.getId()) {
            if (state.getWhoseMove() == this.playerNum) {
                float xTouch = event.getX();
                float yTouch = event.getY();
                float width = surfaceView.getWidth();
                float box = width / 15;
                //create instance of gameAction
                GameAction action = null;
                int index;
                index = getIndexOfPieceTouched(xTouch, yTouch, box);
                if (index == -1) {
                    Log.i("OnTouch", "No piece was pressed");
                } else {
                    //Checks to see if the the user touched inside the homebase
                    Log.i("OnTouch", "The piece that was touched was: " + index);
                    if (checkIfAHomeBaseWasTouched(xTouch, yTouch, box) == false) { //the user is trying to move a piece forward
                        action = new ActionMoveToken(this, index);
                        game.sendAction(action);
                    } else { // the user is trying to move a piece out of start base
                        //TODO I CHANGED  THIS!
                        if (state.getTokenIndexOfFirstPieceInStart(playerNum) != -1) {
                            action = new ActionRemoveFromBase(this, index);
                            game.sendAction(action);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * getIndexOfPieceTouched
     * Each peice has an index, this method gets that index
     *
     * @param xTouch
     * @param yTouch
     * @param box
     * @return
     */
    public int getIndexOfPieceTouched(float xTouch, float yTouch, float box) {
        boolean aHomeBaseWasTouched = checkIfAHomeBaseWasTouched(xTouch, yTouch, box);
        int playerID = this.playerNum;
        float xPos, yPos;
        for (int i = (playerID * 4); i < (playerID * 4 + 4); i++) {//traverse through the pieces the player owns
            if (aHomeBaseWasTouched == false) {//check the board tiles
                if (state.pieces[i].getIsHome() == false) {
                    xPos = (state.pieces[i].getCurrentXLoc() * box);
                    yPos = (state.pieces[i].getCurrentYLoc() * box);
                    if ((xTouch >= xPos) && (xTouch <= (xPos + box))) {
                        if ((yTouch >= yPos) && (yTouch <= (yPos + box))) {
                            return i;
                        }
                    }
                }
            } else {//check the pieces in the start tiles
                xPos = (float) state.pieces[i].getStartXPos() * box;
                yPos = (float) state.pieces[i].getStartYPos() * box;
                if ((xTouch >= xPos) && (xTouch <= (xPos + box))) {
                    if ((yTouch >= yPos) && (yTouch <= (yPos + box))) {
                        return i;
                    }
                }
            }
        }
        return -1;//return negative 1 if no pieces were pressed!
    }

    /**
     * checkIfAHomeBaseWasTouched
     *
     * @param xTouch
     * @param yTouch
     * @param box
     * @return
     */
    public boolean checkIfAHomeBaseWasTouched(float xTouch, float yTouch, float box) {
        //first check if the coordinates touched were in start base or actually on the board coordinates
        if ((xTouch > (box * 0)) && (xTouch < (box * 6)) && (yTouch > (box * 0)) && (yTouch < (box * 6))) {
            return true;
        } else if ((xTouch > (box * 9)) && (xTouch < (box * 15)) && (yTouch > (box * 0)) && (yTouch < (box * 6))) {
            return true;
        } else if ((xTouch > (box * 0)) && (xTouch < (box * 6)) && (yTouch > (box * 9)) && (yTouch < (box * 15))) {
            return true;
        } else if ((xTouch > (box * 9)) && (xTouch < (box * 15)) && (yTouch > (box * 9)) && (yTouch < (box * 15))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * onClick
     *
     * @param v a view object
     */
    @Override
    public void onClick(View v) {
        //create instance of gameAction associated with button pressed
        if (v.getId() == rollDiceButton.getId()) {
            if (state.getWhoseMove() == this.playerNum) {
                if (state.getIsRollable() == true) {
                    Log.i("Onclick", "Human Player Rolling Dice");
                    game.sendAction(new ActionRollDice(this));
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /**
         External Citation
         Date:     April 16 2019
         Problem:  Did not know how to add music to App
         Resource: https://stackoverflow.com/questions/37244357/how-to-play-music-in-android-
         studio?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         Solution: I used the example code from this website. Some of the code below is from this website
         */
        /**
         External Citation
         Date:     April 16 2019
         Problem:  Needed a way to create an mp3 file from youtube.
         Resource: https://www.onlinevideoconverter.com/mp3-converter
         Solution: I used this website to create the mp3 file. Also, all the music is from youtube and is free.
         */
        if (position == 0) { //star wars
            currentSong.stop();
            currentSong = MediaPlayer.create(myActivity, R.raw.starwars);
            currentSong.setLooping(true);
            currentSong.start();
        } else if (position == 1) { //Weird Russian Song
            currentSong.stop();
            currentSong = MediaPlayer.create(myActivity, R.raw.russiansong);
            currentSong.setLooping(true);
            currentSong.start();
        } else if (position == 2) {//Darude
            currentSong.stop();
            currentSong = MediaPlayer.create(myActivity, R.raw.darude);
            currentSong.setLooping(true);
            currentSong.start();
        } else if (position == 3) {//Attack on Titan
            currentSong.stop();
            currentSong = MediaPlayer.create(myActivity, R.raw.attackontitan);
            currentSong.setLooping(true);
            currentSong.start();
        } else if (position == 4) {//Lion King
            currentSong.stop();
            currentSong = MediaPlayer.create(myActivity, R.raw.lionking);
            currentSong.setLooping(true);
            currentSong.start();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing. I don't care about this event.
    }

}
