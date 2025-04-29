package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity2 extends AppCompatActivity {


    private static final String TAG = "RateListActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate_list2);
        ListView listView=findViewById(R.id.mylistview);//获取控件

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //创建列表
        List<String> list1=new ArrayList<String>();
        for(int i=1;i<100;i++) {//循环向list1中添加了99个条目("item1"到"item99")
            list1.add("item" + i);
        }

        //接受子线程发过来的消息
        Handler handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    Bundle bundle=(Bundle) msg.obj;
                    ArrayList<String> retlist=bundle.getStringArrayList("mylist");

                    //重新生成适配器
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity2.this, android.R.layout.simple_list_item_1, retlist);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };


        //构造适配器
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        //将适配器设置给ListActivity
        //这样列表就能显示数据了
        listView.setAdapter(adapter);

        //开启线程，获得数据
        Thread t=new Thread(new MyTask(handler));
        t.start();
        Log.i(TAG,"onCreat:启动线程");//在日志中进行显示

    }



}