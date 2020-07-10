package com.example.quizdroid_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountdown;
    private TextView textViewCorrect, textViewWrong;

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestions;
    private boolean answerd;

    private Handler handler = new Handler();
    private ColorStateList buttonLabelColor;

    private int correctAns = 0, wrongAns = 0;

    int score = 0;

    private int totalSizeofQuiz = 0;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeleftinMillis;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        setupUI();
        fetchDB();
    }
    private void setupUI() {
        textViewQuestions = findViewById(R.id.txtQuestionContainer);

        //textViewCorrect = findViewById(R.id.txtCorrect);
        //textViewWrong = findViewById(R.id.txtWrong);
        textViewCountdown = findViewById(R.id.txtViewTimer);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewScore = findViewById(R.id.txtScore);

        buttonConfirmNext = findViewById(R.id.button);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);


    }
    private void fetchDB(){

        QuizDBHelper dbHelper = new QuizDBHelper(this);
        questionList = dbHelper.getAllQuestions();

        startQuiz();
    }

    private void startQuiz() {

        questionTotalCount = questionList.size();
        Collections.shuffle(questionList);

        showQuestions();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.radio_button1:

                        rb1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;

                    case R.id.radio_button2:

                        rb2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

                        rb1.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;

                    case R.id.radio_button3:
                        rb3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                        rb2.setTextColor(Color.BLACK);
                        rb1.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;
                    case R.id.radio_button4:
                        rb4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb1.setTextColor(Color.BLACK);

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        break;
                }
            }
        });

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!answerd){
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        quizOperations();
                    }else {

                        Toast.makeText(QuizActivity.this, "Bitte Wähle eine Antwort aus", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void quizOperations() {

        answerd = true;
        RadioButton rbselected= findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr,rbselected);
    }
    private void checkSolution(int answerNr, RadioButton rbselected) {

        switch (currentQuestions.getAnswerNr()){

            case 1:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb1.setBackground(ContextCompat.getDrawable(this,R.drawable.spielseite_richtig_btn));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct" + String.valueOf(correctAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);


                }else {
                    changetoIncorrectColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);

                }
                break;
            case 2:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb2.setBackground(ContextCompat.getDrawable(this,R.drawable.spielseite_richtig_btn));
                    rb2.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct" + String.valueOf(correctAns));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);
                }else {
                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }

                break;
            case 3:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb3.setBackground(ContextCompat.getDrawable(this,R.drawable.spielseite_richtig_btn));
                    rb3.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct" + String.valueOf(correctAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);
                }else {
                    changetoIncorrectColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }

                break;

            case 4:
                if (currentQuestions.getAnswerNr() == answerNr){

                    rb4.setBackground(ContextCompat.getDrawable(this,R.drawable.spielseite_richtig_btn));
                    rb4.setTextColor(Color.WHITE);
                    correctAns++;
                    textViewCorrect.setText("Correct" + String.valueOf(correctAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },1000);
                }else {
                    changetoIncorrectColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    },500);
                }

                break;
        }
        if (questionCounter == questionTotalCount){
            buttonConfirmNext.setText("Bestätige und Beende");
        }
    }
    void changetoIncorrectColor(RadioButton rbselected) {

        rbselected.setBackground(ContextCompat.getDrawable(this,R.drawable.spielseite_richtig_btn));
        rbselected.setTextColor(Color.BLACK);


    }
    private void showQuestions() {

        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.buttons_background));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        if (questionCounter < questionTotalCount) {

            currentQuestions = questionList.get(questionCounter);
            textViewQuestions.setText(currentQuestions.getQuestion());
            rb1.setText(currentQuestions.getOption1());
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());

            questionCounter++;
            answerd = false;

            buttonConfirmNext.setText("bestätige");

            textViewQuestionCount.setText("Frage: " + questionCounter + " " + "von" + " " + questionTotalCount);


        } else {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);

                }
            }, 500);

        }
    }
}