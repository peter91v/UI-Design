package com.example.easydo.dao;

public class CounterHelper {
    static CounterHelper instance;
    private static int counter = -1;

    private CounterHelper(int initNum) {
        counter = initNum;
    }

    static public CounterHelper getInstance() {
        if (counter == -1)
            instance = new CounterHelper(-1);
        return instance;
    }

    static public CounterHelper getInstance(int initNum) {
        if (counter == -1)
            instance = new CounterHelper(initNum);
        return instance;
    }

    public int getId() {
        return ++counter;
    }
}
