package com.example.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity {
    //ListActivity默认会提供一个全屏列表，所以可以不需要设置布局

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //创建了一个ArrayList来存储字符串数据
                List<String> list1 = new ArrayList<String>();
        //循环向list1中添加了99个条目("item1"到"item99")
               for(int i=1;i<100;i++) {
                   list1.add("item" + i);
               }

        //创建了一个字符串数组，包含四个元素
        //准备数据
        String[] list_data = {"one","two","three","four","正在获取数据"};
        //创建了一个ArrayAdapter适配器
        //this: 当前Activity的上下文
        //android.R.layout.simple_list_item_1: Android内置的简单列表项布局
        //list_data: 要显示的数据源
        //构造适配器
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_data);
        //将适配器设置给ListActivity
        //这样列表就能显示数据了
        //绑定
        setListAdapter(adapter);


        handler = new Handler(Looper.getMainLooper()) {

            //处理接收的信息
            public void handleMessage(@NonNull Message msg) {

                List<String> getlist=(List<String>) msg.obj;
                ListAdapter adapter2=new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,getlist);
                setListAdapter(adapter2);
            }

        };

        //启动线程获取网络数据
        Thread t=new Thread(()->{
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

            //从网页区获取信息
            List<String> list=new ArrayList<>();
            list.add("美元1==>123.45");
            list.add("美元2==>123.45");
            list.add("美元3==>123.45");
            list.add("美元4==>123.45");

            //发送消息
            Message msg=handler.obtainMessage(5);
            msg.obj = list; // 添加这行，将数据列表放入Message
            handler.sendMessage(msg);



        });
        t.start();


    }
}