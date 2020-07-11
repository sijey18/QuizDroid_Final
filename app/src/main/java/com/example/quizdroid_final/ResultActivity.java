package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_result);

        int score=getIntent().getIntExtra("UserScore",0);
        int totalQuestion=getIntent().getIntExtra("TotalQuestion",0);

        TextView textView=findViewById(R.id.textView);
        textView.setText("Player score= "+score +"\n TotalQuestion= "+totalQuestion);
    }
}