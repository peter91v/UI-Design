package com.example.easydo;

import com.example.easydo.dao.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {

    private final List<Task> todoList = new ArrayList<>();
    private final List<Task> doneList = new ArrayList<>();

    public TaskManager() {}

    public List<Task> getTodoList() {
        return todoList;
    }

    public List<Task> getDoneList() {
        return doneList;
    }

    public Task getTask(int index, boolean onTodoList)
    {
        if(onTodoList)
            return todoList.get(index);
        else
            return doneList.get(index);
    }

    public void addTask(Task todo){
        todoList.add(todo);
        sortTasks(true);
    }

    public void deleteTask(int id, boolean onTodoList) {
        if(onTodoList)
            todoList.remove(id);
        else
            doneList.remove(id);
    }

    public void editTask(Task todo, boolean onTodoList){
        if(onTodoList){
            todoList.remove(todo);
            todoList.add(todo);
            sortTasks(true);
        }
        else{
            doneList.remove(todo);
            doneList.add(todo);
            sortTasks(false);
        }
    }

    public void sortTasks(boolean onTodoList){
        if(onTodoList)
            Collections.sort(todoList);
        else
            Collections.sort(doneList);
    }
}
