package com.example.myapplication;

public class RateItem {
    private int id;




    private String curName;
    private String curRate;

    // 无参构造函数
    public RateItem() {
        super(); // 调用父类Object的构造函数
        curName = ""; // 初始化货币名称为空字符串
        curRate = ""; // 初始化汇率值为空字符串
    }

    // 带参数的构造函数，接收货币名称和汇率值
    public RateItem(String curName, String curRate) {
        super(); // 调用父类Object的构造函数
        this.curName = curName; // 设置货币名称
        this.curRate = curRate; // 设置汇率值
    }

    // 获取id的方法
    public int getId() {
        return id; // 返回当前对象的id值
    }

    // 设置id的方法
    public void setId(int id) {
        this.id = id; // 设置当前对象的id值
    }

    // 获取货币名称的方法
    public String getCurName() {
        return curName; // 返回当前对象的货币名称
    }

    // 设置货币名称的方法
    public void setCurName(String curName) {
        this.curName = curName; // 设置当前对象的货币名称
    }

    // 获取汇率值的方法
    public String getCurRate() {
        return curRate; // 返回当前对象的汇率值
    }

    // 设置汇率值的方法
    public void setCurRate(String curRate) {
        this.curRate = curRate; // 设置当前对象的汇率值

    }}
