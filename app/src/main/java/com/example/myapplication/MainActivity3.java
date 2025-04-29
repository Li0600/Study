package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    private CheckBox plain;
    private CheckBox serif;
    private CheckBox bold;
    private CheckBox italic;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        show=findViewById(R.id.text_show);
        plain=findViewById(R.id.plain);
        serif=findViewById(R.id.serif);
        bold=findViewById(R.id.bold);
        italic=findViewById(R.id.italic);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
     public void onClick_check(View v){

        String s="";
        if(plain.isChecked()){
            s=s+","+plain.getText().toString();
        }
        if(serif.isChecked()){
            s=s+","+serif.getText().toString();
        }
        if(bold.isChecked()){
            s=s+","+bold.getText().toString();
        }
        if(italic.isChecked()){
            s=s+","+italic.getText().toString();
        }
        s="Checked:"+s;
        show.setText(s);
    }
}