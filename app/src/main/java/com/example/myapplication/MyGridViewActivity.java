package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MyGridViewActivity extends AppCompatActivity {

    private static final String TAG=" MyGridViewActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_grid_view);

        ListView mylist=findViewById(R.id.mylist2);
        ProgressBar progressBar=findViewById(R.id.progressBar);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //创建列表
        List<String> list_data=new ArrayList<String>(100);
        for(int i=1;i<100;i++) {//循环向list1中添加了99个条目("item1"到"item99")
//            list_data.add("item" + i);
        }
//        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list_data);
//        mylist.setAdapter(adapter);
//        mylist.setEmptyView(findViewById(R.id.nodata));//空列表的处理

        //接受子线程发过来的消息
        Handler handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    Bundle bundle=(Bundle) msg.obj;
                    ArrayList<String> retlist=bundle.getStringArrayList("mylist");

                    //重新生成适配器
                    ListAdapter adapter = new ArrayAdapter<String>(MyGridViewActivity.this, android.R.layout.simple_list_item_1, retlist);
                    mylist.setAdapter(adapter);
                    //显示列表
                    mylist.setVisibility(View.VISIBLE);
                    //不显示进度条
                    progressBar.setVisibility(View.GONE);
                }
                super.handleMessage(msg);
            }
        };

        Thread t=new Thread(new MyTask(handler));
        t.start();
        Log.i(TAG,"onCreate:启动线程");
    }
}