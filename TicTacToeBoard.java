/*
 * Erick Colston
 * Sept 22, 2022
 * Mobile Computing Tic Tac Toe
 * Create a Tic Tac Toe app. Create an activity that allows the user to hit play and starts a new
 *   activity. In the new activity get the players names and have them submit them to start the game.
 *   When the users submit their names, a new activity is started. This is the activity the users will
 *   play the game in. It will inform the players whos turn it is, allow them to click in a cell to
 *   mark it with an X or O (depending on whos turn), will inform the players if the game was a tie
 *   or who won the game, and will draw a line through the winning marks. After the game is over, it
 *   will display 2 buttons for the users to play again or return to the home screeen.
 */

package com.example.ecolstontictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

//Class TicTacToeBoard to create and control the game board
public class TicTacToeBoard extends View {

    //----------------------------------------------------------------------//
    //                          VARIABLES                                   //
    //----------------------------------------------------------------------//
    private final int boardColor;           //variable for board color
    private final int XColor;               //variable for the color of the X's
    private final int OColor;               //variable for the color of the O's
    private final int winningLineColor;     //variable for the color of the winning line
    private boolean winningLine = false;    //boolean variable for winning line that's set to false
    private final Paint paint = new Paint();//Create new paint object to draw game board
    private final gameLogic game;           //variable to store game to call game logic class
    private int cellSize = getWidth()/3;    //Define variable for cell size of game board


    //----------------------------------------------------------------------//
    //                          CONSTRUCTOR                                 //
    //----------------------------------------------------------------------//
    //Constructor to create the game board to display
    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //create new game
        game = new gameLogic();

        //Array to store the color attributes created in xml colors
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        //Try block to assign the colors to the assigned variables
        try{
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor,0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor,0);

        }finally {
            a.recycle();
        }
    }


    //----------------------------------------------------------------------//
    //                          METHODS                                     //
    //----------------------------------------------------------------------//
    //Method that overrides the onMeasure method
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        //Creates int variable dimension to extract the user's screen dimensions
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());

        //Calculate what the cell size is going to be
        cellSize = dimension/3;

        //Sets dimension variables so the dimensions line up on the screen correctly
        setMeasuredDimension(dimension, dimension);
    }


    //Method to override the onDraw method
    @Override
    protected void onDraw(Canvas canvas){

        paint.setStyle(Paint.Style.STROKE); //Adds stroke to what is being drawn
        paint.setAntiAlias(true); //Helps smooth out the edges for what is being drawn

        drawGameBoard(canvas);  //Calls method to draw the game board
        drawMarkers(canvas);    //Calls method to draw the markers

        //Draw the winning line over the winning markers
        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }


    //Method to scan through the board and determine if an X or O needs to be placed after click
    private void drawMarkers(Canvas canvas){

        //Nested for loop to scan rows and columns to draw markers
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){

                //If cell is not equal to 0 (zero means cell is empty)
                if(game.getGameBoard()[row][col] != 0){

                   //If cell is not empty and player one clicked it, an X is drawn
                   if(game.getGameBoard()[row][col] == 1){
                       drawX(canvas, row, col);
                   }
                   //Else, player 2 clicked it so an O is drawn
                   else{
                       drawO(canvas, row, col);
                   }
                }
            }
        }
    }


    //On touch event to get the cell that is clicked
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();     //Gets x value of users click
        float y = event.getY();     //Gets y value of users click

        //Convert the users click to a row and a column
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            //If not a winning line
            if(!winningLine) {

                //Updates board
                if (game.updateGameBoard(row, col)) {
                    invalidate();

                    //Checks if someone won yet
                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    //If and else statement to update the players turn
                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
            }

            invalidate();   //Redraws game board

            return true;    //Returns true if someone won or tied
        }
        return false;       //Returns false
    }


    //Method to draw the lines to make the rows and columns of the game
    private void drawGameBoard(Canvas canvas){

        paint.setColor(boardColor); //The color of the lines to create columns and rows
        paint.setStrokeWidth(16);   //The size of the lines

        //Draw the lines to create the columns of the game board
        for(int col = 1; col < 3; col++){
            canvas.drawLine(cellSize*col, 0,cellSize*col, canvas.getWidth(), paint);
        }

        //Draw the lines to create the rows of the game board
        for(int row = 1; row < 3; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(), cellSize*row, paint);
        }
    }


    //Method to draw an X in the box player 1 clicks on
    private void drawX(Canvas canvas, int row, int col){

        //Set paint color of X to black
        paint.setColor(XColor);

        //Draw the first line for X going from top right corner to bottom left corner of cell
        //Multiply cell size by .2 to get a padding so line isn't touching border of game line
        canvas.drawLine((float)((col+1)*cellSize - cellSize*.2),
                        (float) (row*cellSize + cellSize*.2),
                        (float)(col*cellSize + cellSize*.2),
                        (float)((row+1)*cellSize - cellSize*.2),
                        paint);

        //Draw the second line for X going from top left corner to bottom right corner of cell
        //Multiply cell size by .2 to get a padding so line isn't touching border of game line
        canvas.drawLine((float)(col*cellSize + cellSize*.2),
                        (float)(row*cellSize + cellSize*.2),
                        (float)((col+1)*cellSize - cellSize*.2),
                        (float)((row+1)*cellSize - cellSize*.2),
                        paint);
    }


    //Method to draw an O in the box player 2 clicks on
    private void drawO(Canvas canvas, int row, int col){

        //Set paint color of O to red
        paint.setColor(OColor);

        //Draw an oval and multiply it by .2 to get a padding so it isn't touching border of game line
        canvas.drawOval((float)(col*cellSize + cellSize*.2),
                        (float)(row*cellSize + cellSize*.2),
                        (float)((col*cellSize + cellSize) - cellSize*.2),
                        (float)((row*cellSize + cellSize) - cellSize*.2),
                        paint);
    }


    //Method to draw a horizontal line through the winning marks
    private void drawHorizontalLine(Canvas canvas, int row, int col){

        //Draw a line through the cells of the winning marks
        canvas.drawLine(col,
                    row*cellSize + (float)cellSize/2,
                    cellSize*3,
                    row*cellSize + (float)cellSize/2,
                    paint);
    }


    //Method to draw a vertical line through the winning marks
    private void drawVerticalLine(Canvas canvas, int row, int col){

        //Draw a vertical line through the cells of the winning marks
        canvas.drawLine(col*cellSize + (float)cellSize/2,
                    row,
                    col*cellSize + (float)cellSize/2,
                    cellSize*3,
                    paint);
    }


    //Method to draw winning line from top right cell to bottom left cell
    private void drawDiagonalPosLine(Canvas canvas){

        //Draw a diagonal line starting at bottom left cell and ending at top right cell
        canvas.drawLine(0, cellSize*3, cellSize*3, 0, paint);
    }


    //Method to draw winning line from top left cell to bottom right cell
    private void drawDiagonalNegLine(Canvas canvas){

        //Draw a diagonal line starting at top left cell and ending at bottom right cell
        canvas.drawLine(0, 0, cellSize*3, cellSize*3, paint);
    }


    //Method to extract winning line variable and figures out which type of line to draw
    private void drawWinningLine(Canvas canvas){

        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:     //If winning marks are in a row
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:     //If winning marks are in a column
                drawVerticalLine(canvas, row, col);
                break;
            case 3:     //If winning marks are diagonal from top left to bottom right
                drawDiagonalNegLine(canvas);
                break;
            case 4:     //If winning marks are diagonal from bottom left to top right
                drawDiagonalPosLine(canvas);
                break;
            case 5:     //If game is a tie
                break;
        }
    }


    //Method to set up the game with players names and which players turn it is
    //This method also give the gameLogic class access to these views to be able to change them
    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names){

        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }


    //Method to that calls the reset game function to reset the game
    public void resetGame(){

        //Resets game
        game.resetGame();
        //Sets winning line boolean back to false to allow cells to be clicked and marked again
        winningLine = false;
    }
}

