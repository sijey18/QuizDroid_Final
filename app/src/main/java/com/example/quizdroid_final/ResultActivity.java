package com.example.quizdroid_final;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView txtHighScore;
    TextView txtTotalQuizQues;

    Button btHighscore;
    Button btMainMenu;
    Button btnAdd;
    EditText editText;
    DatabaseHelper myDB;

    private int highScore;
    public static final String SHARED_PREFERRENCE = "shread_prefrence";
    public static final String SHARED_PREFERRENCE_HIGH_SCORE = "shread_prefrence_high_score";

    private long backPressedTime;





    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acitivity_result);


        btMainMenu = findViewById(R.id.result_bt_mainmenu);
        btHighscore = findViewById(R.id.result_bt_playAgain);
        txtHighScore = findViewById(R.id.result_text_High_Score);
        txtTotalQuizQues = findViewById(R.id.result_total_Ques);
        btnAdd = (Button) findViewById(R.id.edit_add);
        editText = (EditText) findViewById(R.id.editText);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length() != 0) {
                    AddData(newEntry);
                    editText.setText("");
                }else {
                    toastMessage("Gib Namen ein");
                }
            }
        });


        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity.this, StartingScreenActivity.class);
                startActivity(intent);

            }
        });

        btHighscore.setOnClickListener(new View.OnClickListener() { //highscore Pfad
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ResultActivity.this, HighscoreActivity.class);
                startActivity(intent);
            }
        });


        loadHighScore();

        Intent intent = getIntent();

        int score = intent.getIntExtra("UserScore", 0);
        int totalQuestion = intent.getIntExtra("TotalQuestion", 0);


        if (score > highScore) {

            updatHighScore(score);
        }


    }



    private void updatHighScore(int newHighScore) {

        highScore = newHighScore;
        txtHighScore.setText("Neuer Highscore: " + String.valueOf(highScore));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERRENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERRENCE_HIGH_SCORE, highScore);
        editor.apply();


    }

    private void loadHighScore() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERRENCE, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERRENCE_HIGH_SCORE, 0);
        txtHighScore.setText("Deine Punkte: " + String.valueOf(highScore));

    }
    public void AddData(String newEntry) {
        boolean insertData = myDB.addData(newEntry);

        if(insertData) {
           toastMessage("Punkte gespeichert");
        }else {
            toastMessage("Schiefgelaufen");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            Intent intent = new Intent(ResultActivity.this, StartingScreenActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }
}


