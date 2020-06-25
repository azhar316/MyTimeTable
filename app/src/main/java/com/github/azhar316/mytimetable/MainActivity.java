package com.github.azhar316.mytimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTaskAdapter.setTaskData(dummyTaskData);
    }

    @Override
    public void onClick(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}