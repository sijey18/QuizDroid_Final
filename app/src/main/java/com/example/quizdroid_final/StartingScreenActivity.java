package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartingScreenActivity extends AppCompatActivity {
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menue);

        Button buttonStartQuiz = findViewById(R.id.startbtn);
        Button buttonHighscore = findViewById(R.id.highscorebtn);


        buttonHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingScreenActivity.this, HighscoreActivity.class);
                startActivity(intent);
            }


        });
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
   /* @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            new AlertDialog.Builder(this)
                    .setTitle("Do you  want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            setResult(RESULT_OK, new Intent().putExtra("Exit", true));
                            finish();
                        }
                    }).create().show();

        }else  {

            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }*/

   /* @Override
    protected void onStop() {
        super.onStop();
        Log.i("BUGBUG","onStop() in StartingScreenActivity");
        finish();

    }*/


}