package com.example.easydo.dao;

import android.provider.BaseColumns;

public class TaskContract
{
    private TaskContract(){}

    public static class TaskEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "taskList";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DEADLINE = "deadline";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DONE = "done";
    }

}
