package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class NetActivity extends AppCompatActivity implements Runnable{

    private static final String TAG="NetActivity";

    TextView show;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_net);
        show=findViewById(R.id.net_show);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        handler=new Handler(Looper.getMainLooper()) {
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {                     // 检查消息标识符
                    String str = (String) msg.obj;       // 获取消息携带的数据
                    Log.i(TAG, "handleMessage: " + str); // 打印日志
                    show.setText(str);

                }
                super.handleMessage(msg);
            }
        };

//        //启动子线程
//        Log.i(TAG,"onCreat:start Thread");
//        Thread t=new Thread(this);
//        t.start();//this.run();
//启动子线程，方法2
//        Thread t2=new Thread(new Thread(){
//            public void run(){
//
//            }
//        });
//        t2.start();
//启动子线程，方法3
//        Thread t3=new Thread(()->{
//            //run......
//        });
//        t3.start();
    }

    public void onClick2(View btn){
        Log.i(TAG,"onCreat:start Thread");
        Thread t=new Thread(this);
        t.start();//this.run();
    }

    @Override
    public void run() {
        Log.i(TAG,"run:子线程run()......");

        //获取网络数据
        // 声明URL对象变量，初始化为null
        URL url = null;
        String html=" ";
        try {
//            // 创建URL对象，指定要访问的网络地址
//            // 注意：这里缺少协议前缀(如http://)，会导致MalformedURLException
//            url = new URL("https://www.boc.cn/sourcedb/whpj/");
//            // 打开URL连接并将其转换为HttpURLConnection类型
//            // HttpURLConnection是用于发送HTTP请求的类
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//
//            // 从HTTP连接获取输入流，用于读取服务器返回的数据
//            // getInputStream()方法会实际建立网络连接
//            InputStream in = http.getInputStream();
//            // 调用自定义方法将输入流(InputStream)转换为字符串(String)
//            // 此方法需要自行实现(见下方补充说明)
//            html = inputStream2String(in);
//            // 使用Android的Log类输出获取到的HTML内容
//            // TAG应为类中定义的日志标签常量
//            Log.i(TAG, "run: html=" + html);
            // 使用Jsoup连接并获取指定URL的网页内容，返回一个Document对象
            Document doc = Jsoup.connect("https://chl.cn/huilv/?jinri").get();
            // 打印网页的标题到日志（Android的Logcat）
            Log.i(TAG, "run: " + doc.title());  // 打印网页标题


            Elements tables = doc.getElementsByTag("table"); // 从文档中获取所有<table>标签元素（网页中的所有表格）
            Element table=tables.get(0);//找索引为0的表格，就是第一个表格
            Elements rows = table.getElementsByTag("tr"); // 从table中获取所有<tr>标签元素（表格中的所有单元格）
            rows.remove(0);
            for (Element row:rows){

                try {
                    Log.i(TAG,"run:row="+row);
                    Elements tds=row.getElementsByTag("td");
                    Log.i(TAG, "run: 当前行 td 数量=" + tds.size());

                    // 检查列数是否足够（至少 5 列）
                    if (tds.size() < 5) {

                        Log.w(TAG, "run: 跳过列数不足的行: " + row.text());
                        continue;
                    }
                Element td1=tds.first();
                Element td2=tds.get(4);
                Log.i(TAG,"run:td1="+td1.text()+"->"+td2.text());
                Log.i(TAG,"run:td2="+td2.text()+"->"+td2.html());
                html+=(td1.text()+"=>"+td2.text()+"\n");
            }catch (Exception e) {
                    Log.e(TAG, "run: 解析行失败: " + row, e);
                }
            }


        } catch (MalformedURLException e) {
            // 捕获URL格式错误的异常(如缺少协议前缀)
            // printStackTrace()会打印错误堆栈到控制台
            e.printStackTrace();
        } catch (IOException e) {
            // 捕获网络IO操作相关的异常(如连接失败、流读取错误等)
            e.printStackTrace();
        }
        //发送消息
        Message msg = handler.obtainMessage(5);  // 获取消息对象，what值为5
        msg.obj = html;           // 附加数据到消息
        handler.sendMessage(msg);                // 发送消息到主线程队列
    }


    // 输入流转换工具方法
//    private String inputStream2String(InputStream inputStream) throws IOException {
//        StringBuilder out = new StringBuilder();              // 高效字符串拼接
//        Reader in = new InputStreamReader(inputStream, "utf-8"); // 按指定编码读取
//        char[] buffer = new char[1024];                       // 缓冲区
//        int rsz;
//        while ((rsz = in.read(buffer)) > 0) {                 // 循环读取
//            out.append(buffer, 0, rsz);                       // 填充到StringBuilder
//        }
//        return out.toString();                                // 返回完整字符串
//    }

}