package com.example.quizjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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


import question.Question; //Класс, в котором есть методы получения ответов, вопросов и вариантов ответа



public class MainActivity extends AppCompatActivity {
    Button bt1,bt2,bt3,bt4; //Кнопки выбора ответа
    TextView questionWidget; //Текст вопроса
    int total = 0, correct = 0, wrong = 0;
    DatabaseReference reference; //Нужно для работы с базой данных Firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionWidget = (TextView)findViewById(R.id.questionText);//Инициализация всехт виджетов
        bt2 = findViewById(R.id.btn2);
        bt3 = findViewById(R.id.btn3);
        bt1 = findViewById(R.id.btn1);
        bt4 = findViewById(R.id.btn4);
        updateQuestion(bt1,bt2,bt3,bt4, questionWidget); // Функция обновления вопросов (основная)

    }

    private void updateQuestion(final Button bt1, final Button bt2, final Button bt3, final Button bt4,final TextView questionWidget){
        total++;//следующий вопрос
        if(total > 4){//4 -  количество вопросов в базе данных на данный момент

          //Здесь можно придумать вывод результатов на экран, либо переход в другое активити
        }
        else {
            reference = FirebaseDatabase.getInstance().getReference().child("Quest").child(String.valueOf(total)); //Quest это адрес узла в вопросами в Realtime database
            reference.addValueEventListener(new ValueEventListener(){


                @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Question question = dataSnapshot.getValue(Question.class); // "соединяем класс question и базу данных


                    questionWidget.setText(question.getQuestion());  //Вывод всего на экран
                            bt1.setText(question.getOption1());
                            bt2.setText( question.getOption2());
                            bt3.setText( question.getOption3());
                            bt4.setText(question.getOption4());


                            bt1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { // Нажимаем кнопку 1


                                    if(bt1.getText().toString().equals(question.getAnswer())){// если текст на кнопке совпал с ответом из бызы данных
                                        bt1.setBackgroundColor(Color.GREEN);
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                bt1.setBackgroundColor(Color.parseColor("#90EE90"));
                                                updateQuestion(bt1,bt2,bt3,bt4, questionWidget);

                                            }
                                        }
                                        ,1500);// задержка между переходами
                                    }
                                    else//Проходимся по всем вопросам,  подсвечиваем неправильный ответ красным, и подсвечиваем правильный ответ зеленым
                                    {
                                        wrong++;
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
                                                bt1.setBackgroundColor(Color.parseColor("#90EE90"));
                                                bt2.setBackgroundColor(Color.parseColor("#90EE90"));
                                                bt3.setBackgroundColor(Color.parseColor("#90EE90"));
                                                bt4.setBackgroundColor(Color.parseColor("#90EE90"));
                                                updateQuestion(bt1,bt2,bt3,bt4, questionWidget);
                                            }
                                        },1500);



                                    }
                                }
                            });


                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { // Та же схема для всех кнопок


                            if(bt2.getText().toString().equals(question.getAnswer())){

                                bt2.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt2.setBackgroundColor(Color.parseColor("#90EE90"));
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
                                        bt1.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt2.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt3.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt4.setBackgroundColor(Color.parseColor("#90EE90"));
                                        updateQuestion(bt1,bt2,bt3,bt4, questionWidget);

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
                                        bt3.setBackgroundColor(Color.parseColor("#90EE90"));
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
                                        bt1.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt2.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt3.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt4.setBackgroundColor(Color.parseColor("#90EE90"));
                                        updateQuestion(bt1,bt2,bt3,bt4, questionWidget);
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
                                        bt4.setBackgroundColor(Color.parseColor("#90EE90"));
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
                                        bt1.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt2.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt3.setBackgroundColor(Color.parseColor("#90EE90"));
                                        bt4.setBackgroundColor(Color.parseColor("#90EE90"));
                                        updateQuestion(bt1,bt2,bt3,bt4, questionWidget);
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
