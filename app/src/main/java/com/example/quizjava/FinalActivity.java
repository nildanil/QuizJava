package com.example.quizjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {
    TextView total, Result, correct, incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        total = (TextView)findViewById(R.id.total);
        Result = (TextView)findViewById(R.id.result);
        correct = (TextView)findViewById(R.id.correct);
        incorrect =(TextView)findViewById(R.id.incorrect);

        Intent intent = getIntent();

        String numOfQuestions = intent.getStringExtra("total");
        String numOfCorrect = intent.getStringExtra("correct");
        String numOfIncorrect = intent.getStringExtra("incorrect");
        String Finish = intent.getStringExtra("final");

        total.setText("Всего вопросов - " + numOfQuestions);
        correct.setText("Правильно отвеченных  - " + numOfCorrect);
        incorrect.setText("Неправильно отвеченных  - " + numOfIncorrect);
        Result.setText(Finish);


    }
}
