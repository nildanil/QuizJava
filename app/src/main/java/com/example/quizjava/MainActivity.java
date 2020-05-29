package com.example.quizjava;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import question.Question;



public class MainActivity extends AppCompatActivity {
    Random random = new Random();
    Button bt1, bt2, bt3, bt4; //Кнопки выбора ответа
    TextView questionWidget, countdown; //Текст вопроса
    ProgressBar progressBar;
    int total = 0, correct = 0, wrong = 0;
    int seconds = 31;
    int numOfQuestions = 7;
    DatabaseReference reference; //Нужно для работы с базой данных Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById((R.id.progressBar));
        questionWidget = (TextView) findViewById(R.id.questionText);//Инициализация всехт виджетов
        countdown = (TextView) findViewById(R.id.countdown);
        bt2 = findViewById(R.id.btn2);
        bt3 = findViewById(R.id.btn3);
        bt1 = findViewById(R.id.btn1);
        bt4 = findViewById(R.id.btn4);


        CountDownTimer counterDown = new CountDownTimer(seconds * 1000 + 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                seconds = seconds - 1;
                countdown.setText(String.format("%02d", seconds));
                progressBar.setProgress(seconds);


            }

            @Override
            public void onFinish() {
                Intent intent  = new Intent(MainActivity.this,FinalActivity.class);
                intent.putExtra("total", String.valueOf(total--));
                intent.putExtra("correct", String.valueOf(correct));
                intent.putExtra("incorrect", String.valueOf(wrong));
                intent.putExtra("final", "Время вышло!");
                startActivity(intent);

            }
        };

        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,counterDown); // Функция обновления вопросов (основная)


    }


    private void updateQuestion(final Button bt1, final Button bt2, final Button bt3, final Button bt4, final TextView questionWidget,final CountDownTimer count) {
        count.start();
        total++;//следующий вопрос

        if (total > numOfQuestions) {//6 -  количество вопросов в базе данных на данный момент
            Intent intent  = new Intent(MainActivity.this,FinalActivity.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("correct", String.valueOf(correct));
            intent.putExtra("incorrect", String.valueOf(wrong));
            intent.putExtra("final", "Поздравляем!");
            startActivity(intent);
            count.cancel();


            //Здесь можно придумать вывод результатов на экран, либо переход в другое активити
        } else{




            reference = FirebaseDatabase.getInstance().getReference().child("Quest").child(String.valueOf(total)); //Quest это адрес узла в вопросами в Realtime database
            reference.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Question question = dataSnapshot.getValue(Question.class); // "соединяем класс question и базу данных


                    questionWidget.setText(question.getQuestion());  //Вывод всего на экран
                    bt1.setText(question.getOption1());
                    bt2.setText(question.getOption2());
                    bt3.setText(question.getOption3());
                    bt4.setText(question.getOption4());


                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { // Нажимаем кнопку 1
                            count.cancel();

                            if (bt1.getText().toString().equals(question.getAnswer())) {// если текст на кнопке совпал с ответом из бызы данных
                                bt1.setBackgroundColor(Color.GREEN);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            correct++;
                                                            bt1.setBackgroundColor(Color.parseColor("#90EE90"));
                                                            seconds = 30;
                                                            updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);




                                                        }
                                                    }
                                        , 1500);// задержка между переходами
                            } else//Проходимся по всем вопросам,  подсвечиваем неправильный ответ красным, и подсвечиваем правильный ответ зеленым
                            {
                                wrong++;
                                bt1.setBackgroundColor(Color.RED);


                                if (bt2.getText().toString().equals(question.getAnswer())) {
                                    bt2.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt3.getText().toString().equals(question.getAnswer())) {
                                    bt3.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt4.getText().toString().equals(question.getAnswer())) {
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
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);
                                        seconds = 30;




                                    }
                                }, 1500);


                            }
                        }
                    });


                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { // Та же схема для всех кнопок
                            count.cancel();




                            if (bt2.getText().toString().equals(question.getAnswer())) {

                                bt2.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt2.setBackgroundColor(Color.parseColor("#90EE90"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);
                                        seconds = 30;




                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                bt2.setBackgroundColor(Color.RED);


                                if (bt1.getText().toString().equals(question.getAnswer())) {
                                    bt1.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt3.getText().toString().equals(question.getAnswer())) {
                                    bt3.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt4.getText().toString().equals(question.getAnswer())) {
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
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);
                                        seconds = 30;




                                    }
                                }, 1500);


                            }
                        }
                    });
                    bt3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            count.cancel();


                            if (bt3.getText().toString().equals(question.getAnswer())) {

                                bt3.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt3.setBackgroundColor(Color.parseColor("#90EE90"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);
                                        seconds = 30;




                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                bt3.setBackgroundColor(Color.RED);

                                if (bt2.getText().toString().equals(question.getAnswer())) {
                                    bt2.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt1.getText().toString().equals(question.getAnswer())) {
                                    bt1.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt4.getText().toString().equals(question.getAnswer())) {
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
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);
                                        seconds = 30;



                                    }
                                }, 1500);


                            }
                        }
                    });
                    bt4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            count.cancel();


                            if (bt4.getText().toString().equals(question.getAnswer())) {
                                Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                                bt3.setBackgroundColor(Color.GREEN);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        bt4.setBackgroundColor(Color.parseColor("#90EE90"));
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget,count);
                                        seconds = 30;


                                    }
                                }, 1500);
                            } else {
                                wrong++;
                                bt4.setBackgroundColor(Color.RED);

                                if (bt2.getText().toString().equals(question.getAnswer())) {
                                    bt2.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt1.getText().toString().equals(question.getAnswer())) {
                                    bt1.setBackgroundColor(Color.parseColor("#00FF00"));
                                } else if (bt3.getText().toString().equals(question.getAnswer())) {
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
                                        updateQuestion(bt1, bt2, bt3, bt4, questionWidget, count);
                                        seconds = 30;


                                    }
                                }, 1500);


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
