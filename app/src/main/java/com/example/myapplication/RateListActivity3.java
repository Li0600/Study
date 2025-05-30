package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateListActivity3 extends AppCompatActivity {
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    private ListView listView;
    private List<RateItem> rateItemList;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate_list3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    // 适配数据到 ListView
                    List<Map<String, String>> data = new ArrayList<>();
                    for (RateItem item : rateItemList) {
                        Map<String, String> map = new HashMap<>();
                        map.put("name", item.getCurName());
                        map.put("rate", item.getCurRate());
                        data.add(map);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(RateListActivity3.this, data,
                            android.R.layout.simple_list_item_1,
                            new String[]{"name", "rate"},
                            new int[]{android.R.id.text1, android.R.id.text2});
                    listView.setAdapter(adapter);
                }
                return false;
            }
        });
        // 读取 SharedPreferences 中日期记录
        android.content.SharedPreferences sp = getSharedPreferences("myrate", MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        // 开启线程处理数据获取逻辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                String curDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                if (curDateStr.equals(logDate)) {
                    // 日期相同，从数据库加载数据
                    DBManager dbManager = new DBManager(RateListActivity3.this);
                    rateItemList = dbManager.listAll();
                } else {
                    // 日期不同，从网络获取最新数据
                    rateItemList = new ArrayList<>();
                    try {
                        Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
                        Elements elements = doc.select("table[border=1] tr");
                        for (int i = 1; i < elements.size(); i++) {
                            Element element = elements.get(i);
                            Elements tds = element.select("td");
                            String curName = tds.get(0).text();
                            String curRate = tds.get(1).text();
                            RateItem item = new RateItem(curName, curRate);
                            rateItemList.add(item);
                        }
                        // 存入数据库
                        DBManager dbManager = new DBManager(RateListActivity3.this);
                        dbManager.deleteAll();
                        dbManager.addAll(rateItemList);
                        // 更新日期到 SharedPreferences
                        android.content.SharedPreferences.Editor editor = sp.edit();
                        editor.putString(DATE_SP_KEY, curDateStr);
                        editor.commit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 发送消息通知主线程更新 UI
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }
}