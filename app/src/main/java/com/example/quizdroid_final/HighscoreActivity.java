package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscorelist);
        Button buttonBackMenue = findViewById(R.id.startmenuebtn);
        ListView listview = (ListView) findViewById(R.id.score_liste);
        myDB = new DatabaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();

        if (data.getCount() == 0){
            Toast.makeText(HighscoreActivity.this, "Leere Liste",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()){
                theList.add(data.getString(1));//Colum nummer 1 Col 2
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listview.setAdapter(listAdapter);
            }
        }



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
