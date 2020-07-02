package com.github.azhar316.mytimetable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.azhar316.mytimetable.database.AppDatabase;
import com.github.azhar316.mytimetable.database.TaskEntry;
import com.github.azhar316.mytimetable.viewmodels.MainViewModel;
import com.github.azhar316.mytimetable.viewmodels.MainViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskFragment extends Fragment
        implements TaskAdapter.ItemClickHandler {

    private Context mContext;

    private int mDayId;
    private AppDatabase mDb;

    private List<TaskEntry> mTaskEntries;

    public TaskFragment(AppDatabase db,  int dayId) {
        mDb = db;
        mDayId = dayId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        FloatingActionButton fab = rootView.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToStartEditActivity = new Intent(getActivity(), EditTaskActivity.class);
                intentToStartEditActivity.putExtra(EditTaskActivity.TASK_DAY_ID, mDayId);
                startActivity(intentToStartEditActivity);
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_task);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final TaskAdapter adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);

        MainViewModelFactory factory = new MainViewModelFactory(mDb, mDayId);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, factory);
        MainViewModel viewModel = viewModelProvider.get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> taskEntries) {
                mTaskEntries = taskEntries;
                adapter.setTasks(taskEntries);
            }
        });

        return rootView;
    }

    @Override
    public void onClick(int itemPosition) {
        Intent intentToStartEditActivity = new Intent(mContext, EditTaskActivity.class);
        int taskId = mTaskEntries.get(itemPosition).getId();
        intentToStartEditActivity.putExtra(EditTaskActivity.EXTRA_TASK_ID, taskId);
        startActivity(intentToStartEditActivity);
    }
}
