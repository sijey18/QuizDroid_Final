package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {
    private static final String TAG = "HighscoreActivity";


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
