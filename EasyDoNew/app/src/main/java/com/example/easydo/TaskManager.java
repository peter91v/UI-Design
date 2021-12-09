package com.example.easydo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.easydo.dao.CounterHelper;
import com.example.easydo.dao.Task;
import com.example.easydo.dao.TaskContract;

import java.util.ArrayList;
import java.util.Collections;

public class TaskManager {

    private final ArrayList<Task> todoList = new ArrayList<>();
    private final ArrayList<Task> doneList = new ArrayList<>();
    private final SQLiteDatabase readDB;
    private final SQLiteDatabase writeDB;
    private static CounterHelper idCounter = CounterHelper.getInstance();


    public TaskManager(SQLiteDatabase readDB, SQLiteDatabase writeDB) throws Exception{
        this.readDB = readDB;
        this.writeDB = writeDB;
        Cursor taskList = getAllTasks();
        if(taskList.moveToFirst()){
            do {
                int id = taskList.getInt(taskList.getColumnIndex(TaskContract.TaskEntry._ID));
                String title = taskList.getString(taskList.getColumnIndex(TaskContract.TaskEntry.COLUMN_TITLE));
                String deadline = taskList.getString(taskList.getColumnIndex(TaskContract.TaskEntry.COLUMN_DEADLINE));
                int priority = taskList.getInt(taskList.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY));
                String location = taskList.getString(taskList.getColumnIndex(TaskContract.TaskEntry.COLUMN_LOCATION));
                String description = taskList.getString(taskList.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION));
                int done = taskList.getInt(taskList.getColumnIndex(TaskContract.TaskEntry.COLUMN_DONE));

                Task task = new Task.TaskBuilder(title).createTask();
                task.setId(id);

                if(!deadline.isEmpty())
                    task.setDeadline(deadline, "dd.MM.yyyyHH:mm");
                if(!location.isEmpty())
                    task.setLocation(location);
                if(!description.isEmpty())
                    task.setDescription(description);
                if(priority != 0)
                    task.setPriority((short) priority);
                task.setDone(done != 0);

                if(task.isDone())
                    doneList.add(task);
                else
                    todoList.add(task);
            }while(taskList.moveToNext());

            if(taskList.moveToLast())
                idCounter = CounterHelper.getInstance(taskList.getInt(taskList.getInt(taskList.getColumnIndex(TaskContract.TaskEntry._ID))));

            sortTasks(false);
            sortTasks(true);
        }
    }


    private Cursor getAllTasks() {
        return readDB.query(TaskContract.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TaskContract.TaskEntry._ID + " DESC"
        );
    }

    public ArrayList<Task> getTodoList() {
        return todoList;
    }

    public ArrayList<Task> getDoneList() {
        return doneList;
    }

    public Task getTask(int index, boolean onTodoList)
    {
        if(onTodoList)
            return todoList.get(index);
        else
            return doneList.get(index);
    }

    public void addTask(Task todo, boolean onTodoList){

        int taskId = todo.getId();
        boolean update = false;

        if(onTodoList){
            todoList.add(todo);
            sortTasks(true);
            for(Task task : todoList){
                if(task.getId() == taskId){
                    update = true;
                    break;
                }
            }
        }
        else{
            doneList.add(todo);
            sortTasks(false);
            for(Task task : doneList){
                if(task.getId() == taskId){
                    update = true;
                    break;
                }
            }
        }

        ContentValues newTaskVals = new ContentValues();

        int done = todo.isDone() ? 1 : 0;
        newTaskVals.put(TaskContract.TaskEntry._ID, taskId);
        newTaskVals.put(TaskContract.TaskEntry.COLUMN_TITLE, todo.getTitle());
        newTaskVals.put(TaskContract.TaskEntry.COLUMN_DEADLINE, todo.getDeadline("dd.MM.yyyyHH:mm"));
        newTaskVals.put(TaskContract.TaskEntry.COLUMN_LOCATION, todo.getLocation());
        newTaskVals.put(TaskContract.TaskEntry.COLUMN_PRIORITY, todo.getPriority());
        newTaskVals.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, todo.getDescription());
        newTaskVals.put(TaskContract.TaskEntry.COLUMN_DONE, done);

        if(update)
            writeDB.update(TaskContract.TaskEntry.TABLE_NAME, newTaskVals, TaskContract.TaskEntry._ID + "=" + taskId, null);
        else
            writeDB.insert(TaskContract.TaskEntry.TABLE_NAME, null, newTaskVals);
    }

    public void deleteTask(int id, boolean onTodoList) {
        int deleteId;

        if(onTodoList){
            deleteId = todoList.get(id).getId();
            todoList.remove(id);
        }
        else{
            deleteId = doneList.get(id).getId();
            doneList.remove(id);
        }

        writeDB.delete(TaskContract.TaskEntry.TABLE_NAME,
                TaskContract.TaskEntry._ID + "=" + deleteId, null);

    }

    public void sortTasks(boolean onTodoList){
        if(onTodoList)
            Collections.sort(todoList);
        else
            Collections.sort(doneList);
    }
}
