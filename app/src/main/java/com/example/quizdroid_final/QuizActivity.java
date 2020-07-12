package com.example.quizdroid_final;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS = 30000;
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


    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestions;
    private boolean answerd;

    private Handler handler = new Handler();

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    int score = 0;



    private int totalSizeofQuiz = 0;
    private long backPressedTime;
    private ColorStateList textColorDefaultCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        setupUI();
        fetchDB();
        textColorDefaultCd = textViewCountdown.getTextColors();



    }

    private void setupUI() {
        textViewQuestions = findViewById(R.id.txtQuestionContainer);

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

    private void fetchDB() {

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

                switch (checkedId) {

                    case R.id.radio_button1:

                        rb1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);

                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.spielseite_bestaetigen_btn_1));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));

                        break;

                    case R.id.radio_button2:

                        rb2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

                        rb1.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);

                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.spielseite_bestaetigen_btn_1));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));

                        break;

                    case R.id.radio_button3:
                        rb3.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        rb2.setTextColor(Color.BLACK);
                        rb1.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);

                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.spielseite_bestaetigen_btn_1));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));

                        break;
                    case R.id.radio_button4:
                        rb4.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb1.setTextColor(Color.BLACK);

                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.spielseite_bestaetigen_btn_1));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));

                        break;
                }
            }
        });

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!answerd) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        quizOperations();
                    } else {
                        Toast.makeText(QuizActivity.this, "Bitte Wähle eine Antwort aus", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void quizOperations() { //Quiz Logik prüfe die Antworten
        answerd = true;
        countDownTimer.cancel();
        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;
        checkSolution(answerNr, rbselected);


    }

    private void checkSolution(int answerNr, RadioButton rbselected) {
        switch (currentQuestions.getAnswerNr()) {
            case 1:
                if (currentQuestions.getAnswerNr() == answerNr) {
                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    rb1.setTextColor(Color.WHITE);
                    score += 10;
                    textViewScore.setText("Score: " + score);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 1000);
                } else {
                    changetoIncorrectColor(rbselected);
                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 500);
                }
                break;
            case 2:
                if (currentQuestions.getAnswerNr() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    rb2.setTextColor(Color.WHITE);
                    score += 10;
                    textViewScore.setText("Score: " + score);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 1000);
                } else {
                    changetoIncorrectColor(rbselected);
                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 500);
                }

                break;
            case 3:
                if (currentQuestions.getAnswerNr() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    rb3.setTextColor(Color.WHITE);
                    score += 10;
                    textViewScore.setText("Score: " + score);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 1000);
                } else {
                    changetoIncorrectColor(rbselected);
                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 500);
                }
                break;

            case 4:
                if (currentQuestions.getAnswerNr() == answerNr) {
                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    rb4.setTextColor(Color.WHITE);
                    score += 10;
                    textViewScore.setText("Score: " + score);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 1000);
                } else {
                    changetoIncorrectColor(rbselected);
                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.spielseite_richtig_btn));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showQuestions();
                        }
                    }, 500);
                }
                break;
        }
        if (questionCounter == questionTotalCount) {
            buttonConfirmNext.setText("Bestätige und Beende");
        }
    }

    void changetoIncorrectColor(RadioButton rbselected) {

        if (rbselected != null)
            rbselected.setTextColor(Color.BLACK);

    }

    private void showQuestions() { //Zeige die nächsten Fragen

        rbGroup.clearCheck();
        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rechteck_2));
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
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();


        } else {
            //totalSizeofQuiz = questionList.size();

            Toast.makeText(this, "Quiz Finshed", Toast.LENGTH_SHORT).show();

            /*rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonConfirmNext.setClickable(false);*/

            //finishQuiz();
           handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishQuiz();
                }
            }, 2000);
        }

    }

    private void startCountDown() {
        /*if (countDownTimer != null)
            countDownTimer.cancel(); */
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                //quizOperations();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        //Log.e("TAG", "Minutes::" + minutes + "Seconds::" + seconds);
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountdown.setText(timeFormatted);
        if (timeLeftInMillis < 10000) { //10 sek
            textViewCountdown.setTextColor(Color.RED);
        } else {
            textViewCountdown.setTextColor(textColorDefaultCd);
        }
        if (timeLeftInMillis == 0) {


            Toast.makeText(this, "Zeit abgelaufen!", Toast.LENGTH_SHORT).show();


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(getApplicationContext(),QuizActivity.class);
                    startActivity(intent);
                }
            }, 2000);


        }
    }


   /* @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("BUGBUG", "onRestart() in QuizActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("BUGBUG", "onStop() in QuizActivity");
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("BUGBUG", "onPause() in QuizActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("BUGBUG", "onResume() in QuizActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("BUGBUG", "onStart() in QuizActivity");
    } */

    @Override
    protected void onPause() {
        super.onPause();

        if (countDownTimer !=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }


    }


    private void finishQuiz() {
        //Toast.makeText(this, "Quiz finished", Toast.LENGTH_SHORT);
        Intent resultData = new Intent(QuizActivity.this,ResultActivity.class);
        resultData.putExtra("UserScore",score);
        resultData.putExtra("TotalQuestion",questionTotalCount);
        startActivity(resultData);
        finish(); //closing quiz activity
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Zurück um Spiel zu beenden", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


}
