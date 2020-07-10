package com.example.quizdroid_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quizdroid_final.QuizContract.*;

import java.util.ArrayList;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizDroid.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //db in Klammer
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";


        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);

    }



    private void fillQuestionsTable(){

        Questions q1 = new Questions("Was ist die Hauptstadt von Australien?", "Sydney","Canberra","Melbourne","Bourne",1);
        addQuestions(q1);
        Questions q2 = new Questions("Welche Strecke muss man beim Ironman schwimmen?", "1.8km","3.8km","2.8km","4.8km",2);
        addQuestions(q2);
        Questions q3 = new Questions("Welches Elementarteilchen ist elektrisch positiv geladen?", "Anion","Neutron","Proton","Elektron",3);
        addQuestions(q3);
        Questions q4 = new Questions("Die Rotfeder ist ein(e)?", "Vogel","Schmetterling","Fisch","Spinne",3);
        addQuestions(q4);
        Questions q5 = new Questions("Welcher der folgenden Effekte wird durch Aktivität des Parasympathikus hervorgerufen?", "Verengung der Bronchien","Verminderung der Darmperistaltik","Verengung der Arteriolen","Steigerung der Herzfrequenz",1);
        addQuestions(q5);
        Questions q6 = new Questions("Wie hoch ist ein Tennisnetz?", "1.11m","0.91m","0.88m","1.03",2);
        addQuestions(q6);
        Questions q7 = new Questions("Wo liegt Paraguay?", "Afrika","Mittelamerika","Südamerika","Nordamerika",3);
        addQuestions(q7);
        Questions q8 = new Questions("Was ist keine Großstadt?", "Heidelberg","Offenbach am Main","Darmstadt","Gießen",4);
        addQuestions(q8);
        Questions q9 = new Questions("Wie nennt man die Zunft der Bergleute?", "Gewerkschaft","Knappschaft","Gilde","Hanse",2);
        addQuestions(q9);
        Questions q10 = new Questions("Konrad Adenauer war Oberbürgermeister von?", "Bonn","Hamburg","Köln","Berlin",3);
        addQuestions(q10);
        Questions q11 = new Questions("In welcher Stadt fand das Endspiel der Fußball-WM 2002 statt?", "Seoul","Yokohama","Tokio","Osaka",2);
        addQuestions(q11);
        Questions q12 = new Questions("Wer schrieb das Buch 'Das Parfum'?", "Patrick Süskind","Thomas Mann","Stefan Zweig","Freidrich Schiller",1);
        addQuestions(q12);
        Questions q13 = new Questions("Das Mixgetränk aus Limettensaft,Rum & Eis ist?", "Canetti","Caipirinha","Piranha" ,"Caligula",2);
        addQuestions(q13);
        Questions q14 = new Questions("Jemand, der triebhaft Brände legt, ist ein?", "Pygmäe","Pyromane","Pyrotechniker","Pythagoräer",2);
        addQuestions(q14);
        Questions q15 = new Questions("Was zeichnet einen Vektor aus?", "Bestimmung durch einen einzigen Zahlenwert","Stets zwei Dimensionen","Bestimmte Richtung im Raum","Stets drei Dimensionen",3);
        addQuestions(q15);

    }

    private void addQuestions(Questions questions){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION,questions.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1,questions.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2,questions.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3,questions.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4,questions.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR,questions.getAnswerNr());
        db.insert(QuestionTable.TABLE_NAME, null,cv);

    }

    public ArrayList<Questions> getAllQuestions(){

        ArrayList<Questions> questionList = new ArrayList<>();

        db = getReadableDatabase();

        String Projection[] = {

                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR
        };

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                Projection,
                null,
                null,
                null,
                null,
                null);

        if (c.moveToFirst()){

            do{

                Questions questions = new Questions();
                questions.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                questions.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                questions.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                questions.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                questions.setOption4(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION4)));
                questions.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NR)));

                questionList.add(questions);

            }while (c.moveToNext());
        }
        c.close(); //closing cursor
        return questionList;

    }
}
