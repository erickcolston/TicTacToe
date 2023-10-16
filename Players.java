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
import android.widget.EditText;

//Class players to get the players names and store them as strings
public class Players extends AppCompatActivity {

    //----------------------------------------------------------------------//
    //                          VARIABLES                                   //
    //----------------------------------------------------------------------//
    // Edit text variables to store player 1's and player 2's name
    private EditText player1;
    private EditText player2;


    //----------------------------------------------------------------------//
    //                          METHODS                                     //
    //----------------------------------------------------------------------//
    //On create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        //Sets player 1 and player 2 to text entered by user
        player1 = findViewById(R.id.editTextTextPersonName);
        player2 = findViewById(R.id.editTextTextPersonName2);
    }


    //Click method that gets user's name and stores as a string when submit button is clicked
    public void submitButtonClick(View v){

        //Gets text from player1 & 2 variables and stores them as strings in new variable
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();

        //Creates a new intent and starts the game display activity along with sending
        //  over the player's names that were submitted.
        Intent intent = new Intent(this, gameDisplay.class);
        intent.putExtra("Player_Names", new String[] {player1Name, player2Name});
        startActivity(intent);
    }
}