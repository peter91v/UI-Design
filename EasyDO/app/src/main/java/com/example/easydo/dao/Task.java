package com.example.easydo.dao;
import android.location.Location;


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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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
        return this.getDeadline().compareTo(o.getDeadline());
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

        public TaskBuilder withDeadline(Date deadline) {
            this.deadline = deadline;
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

        public Task createTask()
        {
            return new Task(this);
        }
    }
}
