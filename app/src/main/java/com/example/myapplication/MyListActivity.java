package com.example.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyListActivity extends ListActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private static final String TAG="MyListActivity ";
    private  SimpleAdapter listItemAdapter;
    private ArrayList<HashMap<String,String>> listItems;
    private Handler handler;
    private String titleStr;
    private String detailStr;

    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        setListAdapter(listItemAdapter);

        handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"handlerMessage:what:"+msg.what);
                if(msg.what==9){
                    listItems=(ArrayList<HashMap<String,String>>)msg.obj;
//                    listItemAdapter = new SimpleAdapter(MyListActivity.this, listItems, // ListItems 数据源
//                            R.layout.list_item, // ListItem 的 XML 布局实现
//                            new String[] { "ItemTitle", "ItemDetail" },
//                            new int[] { R.id.itemTitle, R.id.itemDetail });
//                    setListAdapter(listItemAdapter);
                    adapter = new MyAdapter(MyListActivity.this,                      // 创建自定义适配器实例
                            R.layout.list_item,          // 传入列表项布局ID
                            listItems);                  // 传入数据列表

                    setListAdapter(adapter);                                // 将适配器设置给Li

                }
                super.handleMessage(msg);
            }
        };

        getListView().setOnItemClickListener(this); // 设置ListView的项
        // 设置长按监听器
        getListView().setOnItemLongClickListener(this);
        Thread t=new Thread(()->{

            ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
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

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("ItemTitle", str1); // 币种
                        map.put("ItemDetail", str2); // 价格
                        list.add(map);

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
            Message msg = handler.obtainMessage(9,list);  // 获取消息对象，what值为5
            handler.sendMessage(msg);                // 发送消息到主线程队列
        });
        t.start();

    }
    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate: " + i); // 标题文字
            map.put("ItemDetail", "detail" + i); // 详情描述
            listItems.add(map);
        }

// 生成适配器的 Item 和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this,
                listItems, // ListItems 数据源
                R.layout.list_item, // ListItem 的 XML 布局实现
                new String[] { "ItemTitle", "ItemDetail" },
                new int[] { R.id.itemTitle, R.id.itemDetail });
    }



    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 实现点击回调方法
        // position-点击位置，id-行ID
        Object itemAtPosition = getListView().getItemAtPosition(position); // 获取点击项的数据对象
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition; // 转换为HashMap
        String titleStr = map.get("ItemTitle");                     // 从HashMap获取标题
        String detailStr = map.get("ItemDetail");                   // 从HashMap获取详情
        Log.i(TAG, "onItemClick: titlestr=" + titleStr);            // 打印标题日志
        Log.i(TAG, "onItemClick: detailstr=" + detailStr);
        Log.i(TAG,"onItemClick:position="+position);// 打印详情日志

//        //删除列表数据，对于MyAdapter适配器
//        adapter.remove(map);

        // 从数据源中删除项
//        listItems.remove(position);
//        // 需要手动通知适配器更新
//        listItemAdapter.notifyDataSetChanged();

//        TextView title = (TextView) view.findViewById(R.id.itemTitle); // 从视图中获取标题TextView
//        TextView detail = (TextView) view.findViewById(R.id.itemDetail); // 获取详情TextView
//        String title2 = String.valueOf(title.getText());            // 获取TextView中的文本
//        String detail2 = String.valueOf(detail.getText());          // 获取TextView中的文本
//        Log.i(TAG, "onItemClick: title2=" + title2);               // 打印标题文本日志
//        Log.i(TAG, "onItemClick: detail2=" + detail2);             // 打印详情文本日志
    }

    //长按处理方法
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this,"长按操作",Toast.LENGTH_LONG).show();
        return true;// 返回true表示事件已处理，不再传递
    }
//    public void clickOpen1(View btn) {
//        //打开新的窗口
//        Intent rate2= new Intent(this, RateListActivity2.class);
//        //传递参数
//        rate2.putExtra("name_key", titleStr);
//        rate2.putExtra("rate_key", detailStr);
//
//        Log.i(TAG, "clickOpen:titleStr=" + titleStr);
//        Log.i(TAG, "clickOpen:detailStr=" + detailStr);
//
//        startActivityForResult(rate2, 3);
//
//    }



    }

