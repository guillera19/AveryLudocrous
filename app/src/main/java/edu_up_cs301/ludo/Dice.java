package edu_up_cs301.ludo;

import java.util.Random;

import edu_up_cs301.game.GamePlayer;

/**
 * Created by Luke on 2/28/2018.
 */

public class Dice {

    //instance variables
    private Random generator;
    private int current;
    private int hasMe; //playerID
    private boolean isUnused;

    /**
     * constructor
     */
    public Dice(int playerID){
        //initiate the randomness!
        this.generator = new Random();

        //know who is allowed to roll
        setHasMe(playerID);

        //force player to roll
        this.isUnused = false;
    }


    //public methods

    public boolean newRoll(int playerID){

        if( playerID == this.hasMe) {
            rollDice();
            return true;
        }
        else
            return false;
    }


    public void setHasMe(int nextPlayerID){
            this.hasMe = nextPlayerID;
        }

    public int getHasMe(){
        return this.hasMe;
    }

    /**
     * getter method, indicates the value has been accessed
     * @return current
     */
    public int getCurrentValue(){
        this.isUnused = false;
        return current;
    }

    //private methods

    /**
     * generates a random roll and resets the boolean to indicate the value has not been accessed yet
     */
    private void rollDice(){
        this.current = this.generator.nextInt(6)+1;
        this.isUnused = true;
    }

    private int whoHasMe() {return this.hasMe; }

}
