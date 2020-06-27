package com.github.azhar316.mytimetable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskFragment extends Fragment
        implements TaskAdapter.ItemClickHandler {

    private Context mContext;

    private String[] mDummyTaskData = {"English", "Math", "Cooking"};

    public TaskFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_task);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        TaskAdapter adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setTaskData(mDummyTaskData);

        return rootView;
    }

    @Override
    public void onClick(String data) {
        Intent intentToStartEditActivity = new Intent(mContext, EditActivity.class);
        intentToStartEditActivity.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(intentToStartEditActivity);
    }
}
