package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyTask implements  Runnable {
    private static final String TAG = "Rate";

    Handler handler;

    public MyTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        Log.i(TAG, "run:子线程run()......");

        //获取网络数据
        // 声明URL对象变量，初始化为null
        URL url = null;
        String html = " ";
        Bundle retbundle = new Bundle();
        ArrayList<String> list=new ArrayList<String>();
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
            Element table = tables.get(0);//找索引为0的表格，就是第一个表格
            Elements rows = table.getElementsByTag("tr"); // 从table中获取所有<tr>标签元素（表格中的所有单元格）
            for (Element row : rows) {

                try {
                    Log.i(TAG, "run:row=" + row);
                    Elements tds = row.getElementsByTag("td");
                    Log.i(TAG, "run: 当前行 td 数量=" + tds.size());

                    // 检查列数是否足够（至少 5 列）
                    if (tds.size() < 5) {

                        Log.w(TAG, "run: 跳过列数不足的行: " + row.text());
                        continue;
                    }
                    Element td1 = tds.first();
                    Element td2 = tds.get(4);
                    String str1 = td1.text().trim();//将获取的内容去掉空格，并转成字符串
                    String str2 = td2.text().trim();
                    Log.i(TAG, "run:td1=" + str1 + "->" + str2);
                    html += (str1 + "=>" + str2 + "\n");
                    list.add(str1+"=>"+str2);
                    //提取三个汇率即可
                    if (str1.equals("欧元")) {
                        retbundle.putFloat("euro", Float.parseFloat(str2));
                    } else if (str1.equals("美元")) {
                        retbundle.putFloat("dollar", Float.parseFloat(str2));
                    } else if (str1.equals("韩国元")) {
                        retbundle.putFloat("won", Float.parseFloat(str2));
//
                    }
                } catch (Exception e) {
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

        Log.i(TAG, "run:bundle=" + retbundle);
        //发送消息
        retbundle.putStringArrayList("mylist",list);
        Message msg = handler.obtainMessage(5);  // 获取消息对象，what值为5
        msg.obj = retbundle;           // 附加数据到消息
        handler.sendMessage(msg);                // 发送消息到主线程队列
    }

}
