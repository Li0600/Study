package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    // 声明表名字符串变量
    private String TBNAME;

    // 构造函数，初始化数据库帮助类和表名
    public DBManager(Context context) {
        dbHelper = new DBHelper(context);  // 创建DBHelper实例
        TBNAME = DBHelper.TB_NAME;         // 获取表名常量
    }

    // 添加单个汇率项到数据库
    public void add(RateItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 获取可写数据库
        ContentValues values = new ContentValues();          // 创建数据容器
        values.put("curname", item.getCurName());           // 添加货币名称
        values.put("currate", item.getCurRate());           // 添加汇率值
        db.insert(TBNAME, null, values);                    // 执行插入操作
        db.close();                                         // 关闭数据库连接
    }

    // 批量添加汇率项到数据库
    public void addAll(List<RateItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 获取可写数据库
        for (RateItem item : list) {                        // 遍历列表
            ContentValues values = new ContentValues();      // 为每个项创建数据容器
            values.put("curname", item.getCurName());
            values.put("currate", item.getCurRate());
            db.insert(TBNAME, null, values);               // 逐条插入
        }
        db.close();                                         // 关闭数据库连接
    }

    // 删除所有汇率记录
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 获取可写数据库
        db.delete(TBNAME, null, null);                     // 无条件删除所有记录
        db.close();                                         // 关闭数据库连接
    }

    // 根据ID删除单个汇率项
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 获取可写数据库
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});  // 条件删除
        db.close();                                         // 关闭数据库连接
    }

    // 更新汇率项
    public void update(RateItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 获取可写数据库
        ContentValues values = new ContentValues();         // 创建数据容器
        values.put("curname", item.getCurName());          // 更新货币名称
        values.put("currate", item.getCurRate());          // 更新汇率值
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});  // 条件更新
        db.close();                                        // 关闭数据库连接
    }

    // 查询所有汇率项
    @SuppressLint("Range")
    public List<RateItem> listAll() {
        List<RateItem> rateList = null;                   // 初始化结果列表
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // 获取可读数据库
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);  // 无条件查询

        if (cursor != null) {
            rateList = new ArrayList<RateItem>();          // 创建列表实例
            while (cursor.moveToNext()) {                 // 遍历结果集
                RateItem item = new RateItem();          // 创建数据项
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                rateList.add(item);                      // 添加到列表
            }
            cursor.close();                              // 关闭游标
        }
        db.close();                                     // 关闭数据库连接
        return rateList;                                // 返回结果列表
    }

    // 根据ID查询单个汇率项
    @SuppressLint("Range")
    public RateItem findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // 获取可读数据库
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)},
                null, null, null);         // 条件查询

        RateItem rateItem = null;                          // 初始化结果对象
        if (cursor != null && cursor.moveToFirst()) {      // 检查结果有效性
            rateItem = new RateItem();                    // 创建数据项
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();                               // 关闭游标
        }
        db.close();                                      // 关闭数据库连接
        return rateItem;                                  // 返回结果对象
    }
}
