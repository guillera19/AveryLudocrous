package edu_up_cs301.ludo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import edu_up_cs301.game.util.FlashSurfaceView;


/**
 * onDraw method Created by nayyar19 and guillermo19 on 2/3/2018.
 *
 * @author Avery Guillermo
 */

public class LudoSurfaceView extends FlashSurfaceView {

    protected LudoState state = new LudoState();
    private int[][] redPath;
    private int[][] greenPath;
    private int[][] bluePath;
    private int[][] yellowPath;


    public LudoSurfaceView(Context context) {
        super(context);
        generalInit();
    }

    public LudoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        generalInit();
    }

    public void setState(LudoState state) {
        this.state = state;
    }


    /**
     * \
     * generalInit
     * <p>
     * Initialization stuff used by all ctors
     */
    private void generalInit() {
        setWillNotDraw(false);

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
     * override the onDraw() method to draw cool stuff for students to see
     *
     * @param canvas - draw on this
     */
    @Override
    public void onDraw(Canvas canvas) {
        //define the canvas instance variables
        float heightAndWidth = canvas.getWidth();//height and width are the same because the surface view is a square
        float box = heightAndWidth / 15;
        int boxInt = canvas.getWidth() / 15;

        //Draw a green filled square
        //Creating Color Objects
        Paint greenPaint = new Paint();
        Paint redPaint = new Paint();
        Paint bluePaint = new Paint();
        Paint yellowPaint = new Paint();
        Paint whitePaint = new Paint();
        Paint blackPaint = new Paint();
        Paint whitePaint2 = new Paint();
        //Creating the colors
        redPaint.setColor(Color.rgb(193, 23, 23));
        greenPaint.setColor(Color.rgb(50, 220, 50));
        bluePaint.setColor(Color.rgb(10, 10, 230));
        yellowPaint.setColor(Color.rgb(242, 228, 38));
        whitePaint.setColor(Color.rgb(255, 255, 255));
        whitePaint2.setColor(Color.rgb(255, 255, 255));
        blackPaint.setColor(Color.rgb(0, 0, 0));


        //Drawing all HomeStretch and Opening Tiles
        float i, j;
        for (i = box; i < (box * 6); i = i + box) {
            j = (box * 7);
            canvas.drawRect(i, j, (float) (i + box), (float) (j + box), redPaint);
        } //Red Homestretch
        canvas.drawRect(box, (box * 6), (box * 2), (box * 7), redPaint); //Red Open Tile
        for (i = (box * 9); i < (box * 13); i = i + box) {
            j = (box * 7);
            canvas.drawRect(i, j, (float) (i + box), (float) (j + box), yellowPaint);
        } //Yellow Homestretch
        canvas.drawRect((box * 13), (box * 8), (box * 14), (box * 9), yellowPaint); //Yellow Open Tile
        for (j = box; j < (box * 6); j = j + box) {
            i = (box * 7);
            canvas.drawRect(i, j, (float) (i + box), (float) (j + box), greenPaint);
        } //Green Homestretch
        canvas.drawRect((box * 8), box, (box * 9), (box * 2), greenPaint); //Green Open Tile
        for (j = (box * 9); j < (box * 13); j = j + box) {
            i = (box * 7);
            canvas.drawRect(i, j, (float) (i + box), (float) (j + box), bluePaint);
        } //Blue Homestretch
        canvas.drawRect((box * 6), (box * 13), (box * 7), (box * 14), bluePaint); //Blue Open Tile

        //Drawing ALL Valid and Invalid small tile squares
        for (i = 0; i < ((box * 15) - box); i = i + box) {
            for (j = 0; j < ((box * 15)); j = j + box) {
                whitePaint2.setStyle(Paint.Style.STROKE);
                whitePaint2.setStrokeWidth(3);
                canvas.drawRect(i, j, (float) (i + box), (float) (i + box), whitePaint2);
            }
        }

        //Red Large Tile Section Creation
        canvas.drawRect(0, 0, (box * 6), (box * 6), redPaint);
        Point d11 = new Point(boxInt * 3, 0);
        Point d12 = new Point(boxInt * 6, boxInt * 3);
        Point d13 = new Point((boxInt * 3), (boxInt * 6));
        Point d14 = new Point((boxInt * 0), (boxInt * 3)); //Bottom Left
        //Drawing the red diamond
        Path redDiamond = new Path();
        redDiamond.moveTo(d11.x, d11.y);
        redDiamond.lineTo(d12.x, d12.y);
        redDiamond.lineTo(d13.x, d13.y);
        redDiamond.lineTo(d14.x, d14.y);
        redDiamond.close();
        canvas.drawPath(redDiamond, whitePaint);
        drawStartTiles(box, canvas, redPaint, 0, 0);

        //Green Large Tile Section Creation
        canvas.drawRect((box * 9), 0.0f, (box * 15), (box * 6), greenPaint);
        Point d21 = new Point(boxInt * 12, 0);
        Point d22 = new Point(boxInt * 15, boxInt * 3);
        Point d23 = new Point((boxInt * 12), (boxInt * 6));
        Point d24 = new Point((boxInt * 9), (boxInt * 3)); //Bottom Left
        //Drawing the green diamond
        Path greenDiamond = new Path();
        greenDiamond.moveTo(d21.x, d21.y);
        greenDiamond.lineTo(d22.x, d22.y);
        greenDiamond.lineTo(d23.x, d23.y);
        greenDiamond.lineTo(d24.x, d24.y);
        greenDiamond.close();
        canvas.drawPath(greenDiamond, whitePaint);
        drawStartTiles(box, canvas, greenPaint, boxInt * 9, 0);

        //Blue Large Tile Section Creation
        canvas.drawRect((box * 0), box * 9, (box * 6), (box * 15), bluePaint);
        Point d31 = new Point(boxInt * 3, boxInt * 9);
        Point d32 = new Point(boxInt * 6, boxInt * 12);
        Point d33 = new Point((boxInt * 3), (boxInt * 15));
        Point d34 = new Point((boxInt * 0), (boxInt * 12)); //Bottom Left
        //Drawing the blue diamond
        Path blueDiamond = new Path();
        blueDiamond.moveTo(d31.x, d31.y);
        blueDiamond.lineTo(d32.x, d32.y);
        blueDiamond.lineTo(d33.x, d33.y);
        blueDiamond.lineTo(d34.x, d34.y);
        blueDiamond.close();
        canvas.drawPath(blueDiamond, whitePaint);
        drawStartTiles(box, canvas, bluePaint, 0, boxInt * 9);

        //Yellow Large Tile Section Creation
        canvas.drawRect((box * 9), (box * 9), (box * 15), (box * 15), yellowPaint);
        Point d41 = new Point(boxInt * 12, boxInt * 9);
        Point d42 = new Point(boxInt * 15, boxInt * 12);
        Point d43 = new Point((boxInt * 12), (boxInt * 15));
        Point d44 = new Point((boxInt * 9), (boxInt * 12)); //Bottom Left
        //Drawing the yellow diamond
        Path yellowDiamond = new Path();
        yellowDiamond.moveTo(d41.x, d41.y);
        yellowDiamond.lineTo(d42.x, d42.y);
        yellowDiamond.lineTo(d43.x, d43.y);
        yellowDiamond.lineTo(d44.x, d44.y);
        yellowDiamond.close();
        canvas.drawPath(yellowDiamond, whitePaint);
        drawStartTiles(box, canvas, yellowPaint, boxInt * 9, boxInt * 9);

        //Drawing Center Square
        canvas.drawRect((box * 6), (box * 6), (box * 9), (box * 9), whitePaint);
        Point p1 = new Point((boxInt * 6), (boxInt * 6)); //Top Left
        Point p2 = new Point((canvas.getWidth() / 2), (canvas.getWidth() / 2)); //Dead Center
        Point p3 = new Point((boxInt * 9), (boxInt * 6)); //Top Right
        Point p4 = new Point((boxInt * 6), (boxInt * 9)); //Bottom Left
        Point p5 = new Point((boxInt * 9), (boxInt * 9)); //Bottom Right
        //Drawing the green Center Triangle
        Path tri1 = new Path();
        tri1.moveTo(p1.x, p1.y);
        tri1.lineTo(p2.x, p2.y);
        tri1.lineTo(p3.x, p3.y);
        tri1.close();
        canvas.drawPath(tri1, greenPaint);

        //Drawing the red Center Triangle
        Path tri2 = new Path();
        tri2.moveTo(p1.x, p1.y);
        tri2.lineTo(p2.x, p2.y);
        tri2.lineTo(p4.x, p4.y);
        tri2.close();
        canvas.drawPath(tri2, redPaint);

        //Drawing the blue Center Triangle
        Path tri3 = new Path();
        tri3.moveTo(p4.x, p4.y);
        tri3.lineTo(p2.x, p2.y);
        tri3.lineTo(p5.x, p5.y);
        tri3.close();
        canvas.drawPath(tri3, bluePaint);

        //Drawing the yellow Center Triangle
        Path tri4 = new Path();
        tri4.moveTo(p3.x, p3.y);
        tri4.lineTo(p2.x, p2.y);
        tri4.lineTo(p5.x, p5.y);
        tri4.close();
        canvas.drawPath(tri4, yellowPaint);

        //draw the safe space tiles
        drawStar((boxInt * 2), (boxInt * 8), canvas, box);
        drawStar((boxInt * 8), (boxInt * 12), canvas, box);
        drawStar((boxInt * 6), (boxInt * 2), canvas, box);
        drawStar((boxInt * 12), (boxInt * 6), canvas, box);

        //draw all the pieces
        drawPieces(canvas, box);

        drawDice(canvas, box); //draw the dice
    }



    public void drawStar(int xPos,  int yPos, Canvas canvas, float box){
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.rgb(255,255,255));

        int xShift1 = (int) (0.1122449*box);
        int xShift2 = (int) (0.5*box);
        int xShift3 = (int) (0.88877551*box);
        int yShift1 = (int) (0.295918*box);
        int yShift2 = (int) (0.8877551*box);
        int yShift3 = (int) (0.673469*box);
        int yShift4 = (int) (0.08163*box);

        Point p1 = new Point(xPos+xShift1,yPos+yShift1); //Bottom Left of Lower Star
        Point p2 = new Point(xPos+xShift3,yPos+yShift1); //Bottom Right of Lower Star
        Point p3 = new Point(xPos+xShift2,yPos+yShift2); //Top of Star

        Point p4 = new Point(xPos+xShift1,yPos+yShift3); //Left point of Upper Star
        Point p5 = new Point(xPos+xShift3,yPos+yShift3); //Right point of Upper Star
        Point p6 = new Point(xPos+xShift2,yPos+yShift4); //Bottom of Star


        Path bottomStar = new Path();
        bottomStar.moveTo(p1.x,p1.y);
        bottomStar.lineTo(p2.x,p2.y);
        bottomStar.lineTo(p3.x,p3.y);
        bottomStar.lineTo(p1.x,p1.y);
        bottomStar.close();
        canvas.drawPath(bottomStar,whitePaint);

        Path topStar = new Path();
        topStar.moveTo(p4.x,p4.y);
        topStar.lineTo(p5.x,p5.y);
        topStar.lineTo(p6.x,p6.y);
        topStar.lineTo(p4.x,p4.y);
        topStar.close();
        canvas.drawPath(topStar,whitePaint);

    }


    public void drawDice(Canvas canvas, float box){
        Paint greyPaint = new Paint();         greyPaint.setColor(Color.rgb(100,100,100));
        Paint redPaint = new Paint();          redPaint.setColor(Color.rgb(255,0,0));
        Paint greenPaint = new Paint();        greenPaint.setColor(Color.rgb(0,255,0));
        Paint bluePaint = new Paint();         bluePaint.setColor(Color.rgb(0,0,255));
        Paint yellowPaint = new Paint();       yellowPaint.setColor(Color.rgb(255,255,0));
        Paint blackPaint = new Paint();        blackPaint.setColor(Color.rgb(0,0,0));

//        Log.i("\nWho's Move is it:"," "+state.getWhoseMove()+"\n");
//        Log.i("","XPOS"+xPos+"   YPOS:"+yPos);
        clearDice(canvas,box);
        if(state.getDiceVal()==6) {
            switch (state.getWhoseMove()) {
                case 0:
                    canvas.drawRect((float) (box * 2.3), (float) (box * 2.3), (float) (box * 3.7), (float) (box * 3.7), redPaint);
                    drawDots(0, 0, box, state.getDiceVal(), canvas, blackPaint);
                    break;

                case 1:
                    canvas.drawRect(((float) ((box * 11.3))), (float) (box * 2.3), ((float) ((box * 12.7))), (float) (box * 3.7), greenPaint);
                    drawDots(box * 9, 0, box, state.getDiceVal(), canvas, blackPaint);
                    break;
                case 2:
                    canvas.drawRect(((float) ((box * 11.3))), ((float) ((box * 11.3))), ((float) ((box * 12.7))), ((float) ((box * 12.7))), yellowPaint);
                    drawDots(box * 9, box * 9, box, state.getDiceVal(), canvas, blackPaint);
                    break;
                case 3:
                    canvas.drawRect(((float) ((box * 2.3))), ((float) ((box * 11.3))), ((float) (box * 3.7)), ((float) ((box * 12.7))), bluePaint);
                    drawDots(0, box * 9, box, state.getDiceVal(), canvas, blackPaint);
                    break;
            }
        }
        else {
            switch (state.getWhoseMove() - 1) {
                case 0:
                    canvas.drawRect((float) (box * 2.3), (float) (box * 2.3), (float) (box * 3.7), (float) (box * 3.7), redPaint);
                    drawDots(0, 0, box, state.getDiceVal(), canvas, blackPaint);
                    break;

                case 1:
                    canvas.drawRect(((float) ((box * 11.3))), (float) (box * 2.3), ((float) ((box * 12.7))), (float) (box * 3.7), greenPaint);
                    drawDots(box * 9, 0, box, state.getDiceVal(), canvas, blackPaint);
                    break;
                case 2:
                    canvas.drawRect(((float) ((box * 11.3))), ((float) ((box * 11.3))), ((float) ((box * 12.7))), ((float) ((box * 12.7))), yellowPaint);
                    drawDots(box * 9, box * 9, box, state.getDiceVal(), canvas, blackPaint);
                    break;
                case -1:
                    canvas.drawRect(((float) ((box * 2.3))), ((float) ((box * 11.3))), ((float) (box * 3.7)), ((float) ((box * 12.7))), bluePaint);
                    drawDots(0, box * 9, box, state.getDiceVal(), canvas, blackPaint);
                    break;

            }
        }
    }

    public void drawDots(float xPos, float yPos, float shift ,int diceVal,Canvas canvas, Paint color){
        switch (diceVal){
            case 1:
                canvas.drawCircle(xPos+ shift*3,yPos+ shift*3,18,color);
                break;
            case 2:
                canvas.drawCircle(xPos + (float)(shift*2.7), yPos + (float)(shift*2.7),18,color);
                canvas.drawCircle(xPos + (float)(shift*3.3), yPos + (float)(shift*3.3),18,color);
                break;
            case 3:
                canvas.drawCircle(xPos + (float)(shift*2.7), yPos + (float)(shift*2.7),18,color);
                canvas.drawCircle(xPos + (float)(shift*3.3), yPos + (float)(shift*3.3),18,color);
                canvas.drawCircle(xPos + (float)(shift*3), yPos + (float)(shift*3),18,color);
                break;
            case 4:
                canvas.drawCircle(xPos + (float)(shift*2.7), yPos + (float)(shift*2.7),15,color);
                canvas.drawCircle(xPos + (float)(shift*2.7), yPos + (float)(shift*3.3),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.3), yPos + (float)(shift*2.7),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.3), yPos + (float)(shift*3.3),15,color);
                break;
            case 5:
                canvas.drawCircle(xPos + (float)(shift*2.6), yPos + (float)(shift*2.6),15,color);
                canvas.drawCircle(xPos + (float)(shift*2.6), yPos + (float)(shift*3.4),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.4), yPos + (float)(shift*2.6),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.4), yPos + (float)(shift*3.4),15,color);
                canvas.drawCircle(xPos + (float)(shift*3), yPos + (float)(shift*3),15,color);
                break;
            case 6:
                canvas.drawCircle(xPos + (float)(shift*2.6), yPos + (float)(shift*2.6),15,color);
                canvas.drawCircle(xPos + (float)(shift*2.6), yPos + (float)(shift*3.4),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.4), yPos + (float)(shift*2.6),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.4), yPos + (float)(shift*3.4),15,color);
                canvas.drawCircle(xPos + (float)(shift*2.6), yPos + (float)(shift*3),15,color);
                canvas.drawCircle(xPos + (float)(shift*3.4), yPos + (float)(shift*3),15,color);
                break;
            default:
                break;
        }
    }

    public void clearDice(Canvas canvas, float box){
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.rgb(255,255,255));
        canvas.drawRect((float)(box*2.3) , (float)(box*2.3), (float)(box*3.7), (float)(box*3.7), whitePaint);
        canvas.drawRect(((float)(883.19995 + (box*2.3))), (float)(box*2.3), ((float)(883.19995 + (box*3.7))), (float)(box*3.7), whitePaint);
        canvas.drawRect(((float)((box*2.3))), ((float)(883.19995+(box*2.3))), ((float)(box*3.7)),((float)(883.19995+(box*3.7))), whitePaint);
        canvas.drawRect(((float)(883.19995 + (box*2.3))),((float)(883.19995+(box*2.3))),((float)(883.19995 + (box*3.7))),((float)(883.19995+(box*3.7))),whitePaint);


    }



    public void drawStartTiles(float box,Canvas canvas,Paint paint,int xShfit, int yShift){

        int box1X1=(int)(box*2.3),box1Y1=(int)(box*0.7), box1X2=(int)(box*3.7), box1Y2=(int)(box*2.1);
        int box2X1=(int)(box*3.9),box2Y1=(int)(box*2.3), box2X2=(int)(box*5.3), box2Y2=(int)(box*3.7);
        int box3X1=(int)(box*2.3),box3Y1=(int)(box*3.9), box3X2=(int)(box*3.7), box3Y2=(int)(box*5.3);
        int box4X1=(int)(box*0.7),box4Y1=(int)(box*2.3), box4X2=(int)(box*2.1), box4Y2=(int)(box*3.7);
        canvas.drawRect(box1X1+xShfit,box1Y1+yShift,box1X2+xShfit,box1Y2+yShift,paint);
        canvas.drawRect(box2X1+xShfit,box2Y1+yShift,box2X2+xShfit,box2Y2+yShift,paint);
        canvas.drawRect(box3X1+xShfit,box3Y1+yShift,box3X2+xShfit,box3Y2+yShift,paint);
        canvas.drawRect(box4X1+xShfit,box4Y1+yShift,box4X2+xShfit,box4Y2+yShift,paint);

    }

    //created by Avery Guillermo
    public void drawPieces(Canvas canvas, float box){

        Paint greenPaint = new Paint();
        Paint redPaint = new Paint();
        Paint bluePaint = new Paint();
        Paint yellowPaint = new Paint();
        Paint whitePaint = new Paint();
        redPaint.setColor(Color.rgb(255,0,0));
        greenPaint.setColor(Color.rgb(80, 255, 95));
        bluePaint.setColor(Color.rgb(75,150,255));
        yellowPaint.setColor(Color.rgb(230, 190, 70));
        whitePaint.setColor(Color.rgb(255,255,255));

        int xPos, yPos;
        for(int i =0; i<16; i++){
            if(state.pieces[i].getIsHome()==false){//draw the pieces out of base
                if(state.pieces[i].getOwner() ==0){//use the red path
                    xPos = redPath[state.pieces[i].getNumSpacesMoved()][0];
                    yPos = redPath[state.pieces[i].getNumSpacesMoved()][1];
                    canvas.drawCircle((box*xPos) + box/2,(box*yPos) + box/2,30,whitePaint);
                    canvas.drawCircle((box*xPos) + box/2,(box*yPos) + box/2,25,redPaint);
                }
                if(state.pieces[i].getOwner() ==1){//use the green path
                    xPos = greenPath[state.pieces[i].getNumSpacesMoved()][0];
                    yPos = greenPath[state.pieces[i].getNumSpacesMoved()][1];
                    canvas.drawCircle((box*xPos)+ box/2,(box*yPos)+ box/2,30,whitePaint);
                    canvas.drawCircle((box*xPos)+ box/2,(box*yPos)+ box/2,25,greenPaint);

                }
                if(state.pieces[i].getOwner() ==2){//use the yellow path
                    xPos = yellowPath[state.pieces[i].getNumSpacesMoved()][0];
                    yPos = yellowPath[state.pieces[i].getNumSpacesMoved()][1];
                    canvas.drawCircle((box*xPos)+ box/2,(box*yPos)+ box/2,30,whitePaint);
                    canvas.drawCircle((box*xPos)+ box/2,(box*yPos)+ box/2,25,yellowPaint);

                }
                if(state.pieces[i].getOwner() ==3){//use the blue path
                    xPos = bluePath[state.pieces[i].getNumSpacesMoved()][0];
                    yPos = bluePath[state.pieces[i].getNumSpacesMoved()][1];
                    canvas.drawCircle((box*xPos)+ box/2,(box*yPos)+ box/2,30,whitePaint);
                    canvas.drawCircle((box*xPos)+ box/2,(box*yPos)+ box/2,25,bluePaint);
                }
            }
            else{//draw the pieces in the start square
                float x, y;
                if(state.pieces[i].getOwner() ==0){//red piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    canvas.drawCircle(x,y,30,whitePaint);
                    canvas.drawCircle(x,y,25,redPaint);
                }
                if(state.pieces[i].getOwner() ==1){//green piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    canvas.drawCircle(x,y,30,whitePaint);
                    canvas.drawCircle(x,y,25,greenPaint);

                }
                if(state.pieces[i].getOwner() ==2){//yellow piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    canvas.drawCircle(x,y,30,whitePaint);
                    canvas.drawCircle(x,y,25,yellowPaint);
                }
                if(state.pieces[i].getOwner() ==3){//blue piece
                    x = (float) state.pieces[i].getStartXPos() * box;
                    y = (float) state.pieces[i].getStartYPos() * box;
                    canvas.drawCircle(x,y,30,whitePaint);
                    canvas.drawCircle(x,y,25,bluePaint);
                }
            }
        }

    }

}
