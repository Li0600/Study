package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Rate2Activity extends AppCompatActivity {
    private static final String TAG = "Rate2Activity";
    private EditText moneyText;
    private TextView nameText;
    private String titleStr;
    private String detailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate2);

        nameText = findViewById(R.id.name);
        moneyText = findViewById(R.id.money);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void onClick_rmb(View bnt) {
        Log.i(TAG, "onClick_rmb:");
        //重新获得输入值
        String titleStr = moneyText.getText().toString();
        String detailStr = nameText.getText().toString();
        float detail = Float.parseFloat(detailStr);

        //让信息在日志中显示出来
        Log.i(TAG, "onClick_rmb:titleStr=" + titleStr);
        Log.i(TAG, "onClick_rmb:detailStr=" + detailStr);

        EditText m = findViewById(R.id.money);
        TextView n = findViewById(R.id.name);
        String mStr = m.getText().toString();
        try {
            float monry = Float.parseFloat((mStr));
            float rmb = monry * detail;

            nameText.setText(detailStr);
            moneyText.setText(String.valueOf(rmb));

        }catch (NumberFormatException e){
            moneyText.setText("请输入正确数据");
        }
    }
}