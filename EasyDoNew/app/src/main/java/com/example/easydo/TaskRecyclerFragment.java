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

public class TaskRecyclerFragment extends Fragment {
    private static final String TAG = "TaskRecyclerFragment";
    private ArrayList<Task> tasks;


    public static TaskRecyclerFragment newInstance(ArrayList<Task> taskList) {
        TaskRecyclerFragment taskRecyclerFragment = new TaskRecyclerFragment();
        Bundle args = new Bundle();
        args.putSerializable("TASKLIST_KEY", taskList);
        taskRecyclerFragment.setArguments(args);

        return taskRecyclerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Initializing the RecyclerView");
        tasks = (ArrayList<Task>) getArguments().getSerializable("TASKLIST_KEY");

        View view = inflater.inflate(R.layout.fragment_task_recycler, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.taskRecycler);
        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(view.getContext(), tasks);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}
