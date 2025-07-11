package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG="Rate";
    private EditText dollarText;
    private EditText euroText;
    private EditText wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);

        dollarText=findViewById(R.id.bnt_d);
        euroText=findViewById(R.id.bnt_e);
        wonText=findViewById(R.id.bnt_w);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        Intent intent=getIntent();
        float dollar=intent.getFloatExtra("dollar_rate_key",0.3f);
        float euro=intent.getFloatExtra("euro_rate_key",0.1f);
        float won=intent.getFloatExtra("won_rate_key",0.2f);

        Log.i(TAG,"onCreate:dollar="+dollar);
        Log.i(TAG,"onCreate:euro="+euro);
        Log.i(TAG,"onCreate:won="+won);

        // 将汇率值设置到EditText
        dollarText.setText(String.valueOf(dollar));
        euroText.setText(String.valueOf(euro));
        wonText.setText(String.valueOf(won));

        // 日志记录
        Log.i(TAG, "onCreate:dollar=" + dollar);
        Log.i(TAG, "onCreate:euro=" + euro);
        Log.i(TAG, "onCreate:won=" + won);

    }
    public void save(View bnt){
        Log.i(TAG,"save:");
        //重新获得输入值
        String dollarStr=dollarText.getText().toString();
        String euroStr=euroText.getText().toString();
        String wonStr=wonText.getText().toString();

        //让信息在日志中显示出来
        Log.i(TAG,"save:dollarStr="+dollarStr);
        Log.i(TAG,"save:euroStr="+euroStr);
        Log.i(TAG,"save:wonStr="+wonStr);

        //返回数据调用页面

        //带回数据
        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        //把新的数据放到bundle中
        bundle.putFloat("key_dollar2",Float.parseFloat(dollarStr));
        bundle.putFloat("key_euro2",Float.parseFloat(euroStr));
        bundle.putFloat("key_won2",Float.parseFloat(wonStr));
        //把bundle放到intent对象中
        intent.putExtras(bundle);
        //设置返回结果
        setResult(6,intent);
        //结束当前的activity并返回
        finish();
    }




}