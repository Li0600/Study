package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityRate extends AppCompatActivity {
    TextView show;
    private float a=0.5f;
    private float b=0.7f;
    private float c=0.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        show=findViewById(R.id.rmb_show);
    }
    public void onClick(View btn) {
        EditText input = findViewById(R.id.rmb);
        String inpStr = input.getText().toString();
        try {
            float rmb = Float.parseFloat((inpStr));
            float result = 0.0f;
            if (btn.getId() == R.id.btn_dollar) {
                result = rmb * a;
            } else if (btn.getId() == R.id.btn_euro) {
                result = rmb * b;
            } else if (btn.getId() == R.id.btn_won) {
                result = rmb * c;
            }
            show.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            show.setText("请输入正确数据");
        }









        
        
        



        

    }
}