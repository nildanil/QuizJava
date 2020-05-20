package com.example.quizjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import question.Question;



public class MainActivity extends AppCompatActivity {
    Button bt1,bt2,bt3,bt4;
    TextView questionWidget;
    int total = 1, correct = 0, wrong = 0;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionWidget = (TextView)findViewById(R.id.btn1);
        bt2 = findViewById(R.id.btn2);
        bt3 = findViewById(R.id.btn3);
        bt1 = findViewById(R.id.btn4);

        updateQuestion(bt1,bt2,bt3,bt4, questionWidget);

    }

    private void updateQuestion(final Button bt1, final Button bt2, final Button bt3, final Button bt4,final TextView questionWidget){
        total++;
        if(total > 4){

        }
        else {
            reference = FirebaseDatabase.getInstance().getReference().child("Quest").child(String.valueOf(total));
            reference.addValueEventListener(new ValueEventListener(){


                @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Question question = dataSnapshot.getValue(Question.class);

                    assert question != null;
                    questionWidget.setText(question.getQuestion());
                            bt1.setText(question.getOption1());
                            bt2.setText( question.getOption2());
                            bt3.setText( question.getOption3());
                            bt4.setText(question.getOption4());


                            bt1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    if(bt1.getText().toString().equals(question.getAnswer())){
                                        bt1.setBackgroundColor(Color.GREEN);
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                bt1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion(bt1,bt2,bt3,bt4, questionWidget);

                                            }
                                        }
                                        ,1500);
                                    }
                                    else
                                    {
                                        wrong++;
                                        bt1.setBackgroundColor(Color.RED);

                                        bt1.setBackgroundColor(Color.RED);
                                        if (bt2.getText().toString().equals(question.getAnswer())){
                                            bt2.setBackgroundColor(Color.parseColor("#00FF00"));
                                        }
                                        else if(bt3.getText().toString().equals(question.getAnswer())){
                                            bt3.setBackgroundColor(Color.parseColor("#00FF00"));
                                        }
                                        else if(bt4.getText().toString().equals(question.getAnswer())){
                                            bt4.setBackgroundColor(Color.parseColor("#00FF00"));

                                        }
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                bt1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                bt2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                bt3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                bt4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion(bt1,bt2,bt3,bt4, questionWidget);
                                            }
                                        },1500);



                                    }
                                }
                            });


                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if(bt2.getText().toString().equals(question.getAnswer())){

                                bt2.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(bt1, bt2, bt3, bt4,  questionWidget);

                                    }
                                },1500);
                            }
                            else
                            {
                                wrong++;
                                bt2.setBackgroundColor(Color.RED);


                                if (bt1.getText().toString().equals(question.getAnswer())){
                                    bt1.setBackgroundColor(Color.parseColor("#00FF00"));
                                }
                                else if(bt3.getText().toString().equals(question.getAnswer())){
                                    bt3.setBackgroundColor(Color.parseColor("#00FF00"));
                                }
                                else if(bt4.getText().toString().equals(question.getAnswer())){
                                    bt4.setBackgroundColor(Color.parseColor("#00FF00"));

                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bt1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget);
                                    }
                                },1500);



                            }
                        }
                    });
                    bt3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if(bt3.getText().toString().equals(question.getAnswer())){

                                bt3.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget);

                                    }
                                },1500);
                            }
                            else
                            {
                                wrong++;
                                bt3.setBackgroundColor(Color.RED);

                                if (bt2.getText().toString().equals(question.getAnswer())){
                                    bt2.setBackgroundColor(Color.parseColor("#00FF00"));
                                }
                                else if(bt1.getText().toString().equals(question.getAnswer())){
                                    bt1.setBackgroundColor(Color.parseColor("#00FF00"));
                                }
                                else if(bt4.getText().toString().equals(question.getAnswer())){
                                    bt4.setBackgroundColor(Color.parseColor("#00FF00"));

                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bt1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget);
                                    }
                                },1500);



                            }
                        }
                    });
                    bt4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if(bt4.getText().toString().equals(question.getAnswer())){
                                Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                                bt3.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget);

                                    }
                                },1500);
                            }
                            else
                            {
                                wrong++;
                                bt4.setBackgroundColor(Color.RED);

                                if (bt2.getText().toString().equals(question.getAnswer())){
                                    bt2.setBackgroundColor(Color.parseColor("#00FF00"));
                                }
                                else if(bt1.getText().toString().equals(question.getAnswer())){
                                    bt1.setBackgroundColor(Color.parseColor("#00FF00"));
                                }
                                else if(bt3.getText().toString().equals(question.getAnswer())){
                                    bt3.setBackgroundColor(Color.parseColor("#00FF00"));

                                }
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        bt1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        bt4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget);
                                    }
                                },1500);



                            }
                        }
                    });
                        }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
