package com.github.azhar316.mytimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
        implements TaskAdapter.ItemClickHandler {

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;

    private String[] dummyTaskData = {"English", "Math", "Cooking"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_task);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mTaskAdapter = new TaskAdapter(this);
        mRecyclerView.setAdapter(mTaskAdapter);

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToStartEditActivity = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intentToStartEditActivity);
            }
        });

        mTaskAdapter.setTaskData(dummyTaskData);
    }

    @Override
    public void onClick(String data) {
        Intent intentToStartEditActivity = new Intent(this, EditActivity.class);
        intentToStartEditActivity.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(intentToStartEditActivity);
    }
}