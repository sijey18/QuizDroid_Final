package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menue);

        Button buttonStartQuiz = findViewById(R.id.startbtn);
        Button buttonHighscore = findViewById(R.id.highscorebtn);

        buttonHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                higscoreList();
            }


        });
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    private void higscoreList() {
        Intent intent = new Intent(StartingScreenActivity.this, HighscoreActivity.class);
        startActivity(intent);
    }
}