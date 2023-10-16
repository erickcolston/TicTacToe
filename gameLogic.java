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

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Class gameLogic to fill the game board with all zeros
public class gameLogic {

    //------------------------------------------------------------------//
    //                          VARIABLES                               //
    //------------------------------------------------------------------//
    //Array to store game board rows and columns
    private int[][] gameBoard;
    //Array to store player names (filled with default names)
    private String[] playerNames = {"Player 1", "Player 2"};
    //1st element stores row, 2nd element stores col, 3rd element stores line type
    private int[] winType = {-1,-1,-1};
    //Variable to store play again / home buttons, and player turn text view
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    //Set player variable to 1
    private int player = 1;


    //------------------------------------------------------------------//
    //                          GAME LOGIC CONSTRUCTOR                  //
    //------------------------------------------------------------------//
    //Constructs game board object
    gameLogic(){
        gameBoard = new int[3][3];
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                gameBoard[row][col] = 0;
            }
        }
    }


    //------------------------------------------------------------------//
    //                          METHODS                                 //
    //------------------------------------------------------------------//
    //Method to check if the cell clicked is available for the player to mark
    //Sets the current players name whos turn it is in player display text view
    public boolean updateGameBoard(int row, int col){

        //If statement to check if cell is empty
        if(gameBoard[row - 1][col - 1] == 0){
            gameBoard[row - 1][col - 1] = player;

            //If cell is empty, displays the next players name to let them know its their turn
            if(player == 1){
                playerTurn.setText((playerNames[1] + "'s Turn!"));
            }
            else{
                playerTurn.setText((playerNames[0] + "'s Turn!"));
            }

            return true;    //returns true if cell was empty and updated
        }
        else{
            return false;   //returns false if cell already has a mark in it
        }
    }


    //Boolean method to check to see if a player won the game
    public boolean winnerCheck() {

        boolean isWinner = false;

        //Checks the rows for a winner (horizontal check)
        for (int row = 0; row < 3; row++) {

            //If each row has same mark in a certain column and its not empty
            if (gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][0] == gameBoard[row][2]
                    && gameBoard[row][0] != 0) {

                //Set win type to row of winning marks, start line at column 0, and set horizontal line as 1
                winType = new int[] {row, 0, 1};

                isWinner = true;    //Returns true if winner won horizontally
            }
        }

        //Checks the columns for a winner (vertical check)
        for (int col = 0; col < 3; col++) {

            //If each column has same mark in a certain row and its not empty
            if (gameBoard[0][col] == gameBoard[1][col] && gameBoard[2][col] == gameBoard[0][col]
                    && gameBoard[0][col] != 0) {

                //Set win type to column of winning marks, start line at row 0, and set vertical line as 2
                winType = new int[] {0, col, 2};

                isWinner = true;    //Returns is winner boolean as true
            }
        }

        //Checks the diagonal top left to bottom right for a winner
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2]
                && gameBoard[0][0] != 0) {

            //Set win type to diagonal of winning marks, start line at row 0, column 0, and set diagonal line as 3
            winType = new int[] {0, 2, 3};

            isWinner = true;    //Returns is winner boolean as true
        }

        //Checks the diagonal from bottom left to top right for a winner
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2]
                && gameBoard[2][0] != 0) {

            //Set win type to diagonal of winning marks, start line at row 3, column 0, and set diagonal line as 4
            winType = new int[] {2, 2, 4};

            isWinner = true;    //Returns is winner boolean as true
        }

        //Check to see if board is filled
        int boardFilled = 0;

        //Nested for loop to check if each cell is equal to 0 (Cell has a zero if it is empty)
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                if(gameBoard[row][col] != 0){
                    boardFilled += 1;
                }
            }
        }

        //If boolean isWinner is true, makes play again / home buttons visible and displays who won
        if(isWinner){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player - 1]) + " Won!!!");
            return true;
        }

        //If board is filled, make play again and home buttons visible and display tie game message
        else if(boardFilled == 9){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText("Cat's Game... Try Again??");
            winType = new int[]{-1, -1, 5};
            return true;
        }

        else{
            return false;   //Returns false if all checks fail !!!
        }
    }


    //Method to reset the game and clear the game board
    public void resetGame(){

        //Nested for loop to reset each cell to 0 to indicate the cell is empty
        for(int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col] = 0;
            }
        }

        //Sets payer 1 to go first again, make play again / home buttons disappear and displays who's turn it is
        player = 1;
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);
        playerTurn.setText((playerNames[0] + "'s Turn!"));
    }


    //----------------------------------------------------------------------//
    //                      GETTERS AND SETTERS                             //
    //----------------------------------------------------------------------//
    //Method to set play again button
    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    //Method to set home button
    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    //Method to set player turn text view
    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    //Method to get game board with 2D array
    public int[][] getGameBoard() {
        return gameBoard;
    }

    //Method to get which player is up
    public int getPlayer() {
        return player;
    }

    //Method to set the player
    public void setPlayer(int player) {
        this.player = player;
    }

    //Method to set the player names
    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    //Method to get win type
    public int[] getWinType() {
        return winType;
    }
}
