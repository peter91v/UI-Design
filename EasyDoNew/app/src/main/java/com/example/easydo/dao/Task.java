package com.example.easydo.dao;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Comparable<Task>
{
    private String title = "";
    private Date deadline = null;
    private String location = "";
    private String description = "";
    private boolean done = false;

    private Task(TaskBuilder builder) {
        title = builder.title;
        deadline = builder.deadline;



        location = builder.location;
        description = builder.description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**Format characters (dd = day characters) (MM = month characters) (yyyy = year characters)*/
    public String getDeadline(String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(deadline);
    }

    /**Format characters (dd = day characters) (MM = month characters) (yyyy = year characters)
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

    @Override
    public int compareTo(Task o) {
        return this.getDeadline("dd.MM.yyyy").compareTo(o.getDeadline("dd.MM.yyyy"));
    }

    public static class TaskBuilder {
        private String title = "";
        private Date deadline = null;
        private String location = "";
        private String description = "";
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

        public TaskBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public TaskBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Task createTask()
        {
            return new Task(this);
        }
    }
}
