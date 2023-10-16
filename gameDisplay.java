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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Game Display Class to display the third activity of the app
public class gameDisplay extends AppCompatActivity {

    //---------------------------------------------------------------------------//
    //                                  VARIABLE                                 //
    //---------------------------------------------------------------------------//
    //Variable to store the tic tac toe board
    private TicTacToeBoard ticTacToeBoard;


    //---------------------------------------------------------------------------//
    //                                   METHODS                                 //
    //---------------------------------------------------------------------------//
    //On create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_display);

        //Variables to store the play again and home buttons and players turn text view
        Button playAgainBTN = findViewById(R.id.btnPlayAgain);
        Button homeBTN = findViewById(R.id.btnHome);
        TextView playerTurn = findViewById(R.id.playerTurnDisplay);

        //Make play again and home buttons invisible until there is a winner
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        //Creates new string array to store player names entered in the players activity
        String[] playersNames = getIntent().getStringArrayExtra("Player_Names");

        //If statement to display first players name letting them know its their turn
        if(playersNames != null){
            playerTurn.setText(playersNames[0] + "'s Turn!");
        }

        //Sets variable by using find view by to locate the xml id
        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        //Calls set up game method and sends parameters gathered from xml id's
        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playersNames);
    }


    //Click method for play again button. When clicked, starts a new game for the user
    public void playAgainButtonClick(View v){
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }


    //Click method for home button. When clicked, send user to the first activity
    public void homeButtonClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}