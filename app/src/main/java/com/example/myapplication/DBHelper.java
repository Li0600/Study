package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // 定义数据库版本号常量，初始值为1
    private static final int VERSION = 1;
    // 定义数据库名称常量，数据库文件名为"myrate.db"
    private static final String DB_NAME = "myrate.db";
    // 定义表名常量，表名为"tb_rates"，并声明为公共静态常量，可供其他类直接访问
    public static final String TB_NAME = "tb_rates";

    // DBHelper的构造函数，接收上下文、数据库名称、游标工厂和数据库版本号作为参数
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        // 调用父类SQLiteOpenHelper的构造函数，初始化数据库
        super(context, name, factory, version);
    }
    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    // 重写onCreate方法，当数据库第一次创建时调用，用于创建表结构
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 当前方法体为空，需要在此处添加创建表的SQL语句
    }
    // 重写onUpgrade方法，当数据库版本升级时调用，用于更新表结构
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 当前方法体为空，需要在此处添加升级表的逻辑
    }

}
