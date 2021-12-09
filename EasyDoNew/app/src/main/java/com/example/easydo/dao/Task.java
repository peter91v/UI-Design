package com.example.easydo.dao;

import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Comparable<Task>
{

    private int id;
    private String title = "";
    private Date deadline = null;
    private String location = "";
    private String description = "";
    private Short priority = 0;
    private boolean done = false;

    private Task(TaskBuilder builder) {
        title = builder.title;
        deadline = builder.deadline;

        location = builder.location;
        description = builder.description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**Format characters (dd = day characters) (MM = month characters) (yyyy = year characters) (HH = hour chars) (mm = minute chars)*/
    public String getDeadline(String dateFormat) {
        if (deadline == null)
            return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(deadline);
    }

    /**Format characters (dd = day characters) (MM = month characters) (yyyy = year characters) (HH = hour chars) (mm = minute chars)
     * @apiNote if date format could not be parsed nothing will be set*/
    public void setDeadline(String deadline, String dateFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            this.deadline = format.parse(deadline);
        }
        catch (ParseException pe) {
            Log.d("Task.java", "withDeadline: ParseException");
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Task o) {
        if(this.priority.compareTo(o.priority) != 0)
            return this.priority.compareTo(o.priority) * -1; //higher priority should be shown first

        if(this.deadline == null)
            return 1;
        else if(o.deadline == null)
            return -1;
        else
            return this.deadline.compareTo(o.deadline);
    }

    public static class TaskBuilder {
        private int id = CounterHelper.getInstance().getId();
        private String title = "";
        private Date deadline = null;
        private String location = "";
        private String description = "";
        private short priority = 0;
        private boolean done = false;

        public TaskBuilder(String title) {
            this.title = title;
        }

        /**Format characters (dd = day characters) (MM = month characters) (yyyy = year characters)
         * @return  null if date format could not be parsed*/
        public TaskBuilder withDeadline(String deadline, String dateFormat){
            try {
                SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                this.deadline = format.parse(deadline);
                return this;
            }
            catch (ParseException pe) {
                Log.d("Task.java", "withDeadline: ParseException");
            }
            return null;
        }

        public TaskBuilder withId(int id){
            this.id = id;
            return this;
        }

        public TaskBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public TaskBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder withPriority(short priority) {
            this.priority = priority;
            return this;
        }

        public Task createTask()
        {
            return new Task(this);
        }
    }
}
