package com.example.easydo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easydo.dao.Task;

import java.util.ArrayList;

public class TaskRecycler extends Fragment
{
    private static final String TAG = "TaskRecyclerFragment";
    private final ArrayList<Task> tasks;

    TaskRecycler(ArrayList<Task> taskList) {
        tasks = taskList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Initializing the RecyclerView");

        View view = inflater.inflate(R.layout.fragment_task_recycler, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(view.getContext(), tasks);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}
