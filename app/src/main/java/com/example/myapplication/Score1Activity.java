package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Score1Activity extends AppCompatActivity {

    TextView score_a;
    TextView score_b;
    int s1=0;
    int s2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        score_a = findViewById(R.id.teama_score);
        score_b = findViewById(R.id.teamb_score);
    }
    public void onClick_a(View btn){
        if(btn.getId()==R.id.teama_btn1){
            s1++;
        }
        else if(btn.getId()==R.id.teama_btn2){
            s1+=2;
        }
        else if(btn.getId()==R.id.teama_btn3){
            s1+=3;
        }

        score_a.setText(String.valueOf(s1));

    }

    public void onClick_b(View btn){
        if(btn.getId()==R.id.teamb_btn1){
            s2+=1;
        }
        else if(btn.getId()==R.id.teamb_btn2){
            s2+=2;
        }
        else if(btn.getId()==R.id.teamb_btn3){
            s2+=3;
        }

        score_b.setText(String.valueOf(s2));
    }


    public void onClick_RESET1(View v){
        s1=0;
        s2=0;
        score_a.setText(String.valueOf(s1));
        score_b.setText(String.valueOf(s2));
    }


    //保存方向改变时的数据

    //将数据放入outState中
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("teama_score",s1);
        outState.putInt("teamb_score",s2);

    }

    //提取数据并进行显示
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        s1=savedInstanceState.getInt("teama_score");
        s2=savedInstanceState.getInt("teamb_score");
        score_a.setText(String.valueOf(s1));
        score_b.setText(String.valueOf(s2));
    }
}