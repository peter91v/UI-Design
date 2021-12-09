package com.example.easydo.dao;

public class CounterHelper {
    static CounterHelper instance = new CounterHelper(0);
    private static int counter = -1;
    private CounterHelper(int initNum){
        counter = initNum;
    }

    static public CounterHelper getInstance(){
        return instance;
    }

    static public CounterHelper getInstance(int initNum){
        instance = new CounterHelper(initNum);
        return instance;
    }

    public int getId(){
        return ++counter;
    }
}
