package com.github.azhar316.mytimetable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    public static final String TAG = TaskFragment.class.getSimpleName();

    public static final String DAY_ID = "TaskFragment.dayId";

    private int mDayId;
    private AppDatabase mDb;

    private List<TaskEntry> mTaskEntries;

    public static TaskFragment newInstance(int dayId) {
        Bundle args = new Bundle();
        args.putInt(DAY_ID, dayId);
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(args);
        return taskFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "inside onCreateView");
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args == null) return;

        mDb = AppDatabase.getInstance(getActivity());
        mDayId = args.getInt(DAY_ID);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
    }

    @Override
    public void onClick(int itemPosition) {
        Intent intentToStartEditActivity = new Intent(getContext(), EditTaskActivity.class);
        int taskId = mTaskEntries.get(itemPosition).getId();
        intentToStartEditActivity.putExtra(EditTaskActivity.EXTRA_TASK_ID, taskId);
        startActivity(intentToStartEditActivity);
    }
}
