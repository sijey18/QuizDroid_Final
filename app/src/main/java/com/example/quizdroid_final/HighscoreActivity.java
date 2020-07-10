package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscorelist);
        Button buttonBackMenue = findViewById(R.id.startmenuebtn);
        buttonBackMenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMenue();
            }
        });
    }
    private void backMenue() {
        Intent intent = new Intent(HighscoreActivity.this, StartingScreenActivity.class);
        startActivity(intent);
    }
}
