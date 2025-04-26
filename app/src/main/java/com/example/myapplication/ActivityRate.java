package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityRate extends AppCompatActivity implements  Runnable {
    TextView show;
    private static final String TAG="Rate";
    private float dollarRate=0.5f;
    private float euroRate=0.7f;
    private float wonRate=0.8f;

    private Handler handler;

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

        handler=new Handler(){
            public void handleMessage(@Nullable Message msg){
                super.handleMessage(msg);
            }
        };

        //启动线程
        Thread t=new Thread();
        t.start();
    }
    public void onClick(View btn) {
        EditText input = findViewById(R.id.rmb);
        String inpStr = input.getText().toString();
        try {
            float rmb = Float.parseFloat((inpStr));
            float result = 0.0f;
            if (btn.getId() == R.id.btn_dollar) {
                result = rmb * dollarRate;
            } else if (btn.getId() == R.id.btn_euro) {
                result = rmb * euroRate;
            } else if (btn.getId() == R.id.btn_won) {
                result = rmb * wonRate;
            }
            show.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            show.setText("请输入正确数据");

        }

    }

    public void clickOpen(View btn){
        //打开新的窗口
        Intent config=new Intent(this,ConfigActivity.class);
        //传递参数
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);

        Log.i(TAG, "clickOpen:dollarRate="+dollarRate);
        Log.i(TAG, "clickOpen:euroRate="+euroRate);
        Log.i(TAG, "clickOpen:wonRate="+wonRate);

        startActivityForResult(config,3);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode==3 && resultCode==6){
            Bundle bdl=data.getExtras();
            dollarRate=bdl.getFloat("key_dollar2");

            euroRate=bdl.getFloat("key_euro2");
            wonRate=bdl.getFloat("key_won2");
            Log.i(TAG, "onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG, "onActivityResult:euroRate="+euroRate);
            Log.i(TAG, "onActivityResult:wonRate="+wonRate);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void run(){
         Log.i(TAG,"run:");
         try{
             Thread.sleep(5000);
         }catch (InterruptedException e){
             throw new RuntimeException(e);
         }
        Message msg=handler.obtainMessage(8,"swufe");
         handler.sendMessage(msg);

    }
}