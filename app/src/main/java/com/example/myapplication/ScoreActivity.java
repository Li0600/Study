package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoreActivity extends AppCompatActivity {

    TextView score;
    int s1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            score = findViewById(R.id.score1);
            return insets;
        });
    }

        public void onClick1(View btn){
            if(btn.getId()==R.id.btn1){
                s1++;
            }
            else if(btn.getId()==R.id.btn2){
                s1+=2;
            }
            else if(btn.getId()==R.id.btn3){
                s1+=3;
            }
            score.setText(String.valueOf(s1));

        }

        public void onClick_RESET(View v){
             s1=0;
             score.setText(String.valueOf(s1));
        }

}