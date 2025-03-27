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

public class BML_Activity extends AppCompatActivity {
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bml);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        show=findViewById(R.id.bml_show);
    }
    public void onClick_bml(View bnt){
        EditText h = findViewById(R.id.height);
        EditText w = findViewById(R.id.weight);
        String wStr = w.getText().toString();
        String hStr = h.getText().toString();
        try {
            float height=Float.parseFloat((hStr));
            float weight=Float.parseFloat((wStr));
            float bml=height/(height*height);
            if (bml>30.0){
                String s="肥胖";
            }
            else if (bml>25.0){
                String s="正常";
            }
            else if (bml>18.5){
                String s="超重";
            }
            else if (bml<18.5){
                String s="偏瘦";
            }

            show.setText(String.valueOf(bml));
        }catch (NumberFormatException e){
            show.setText("请输入正确数据");
        }
    }



}