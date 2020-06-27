package com.github.azhar316.mytimetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private final String[] WEEK_DAYS_NAMES = {"MONDAY", "TUESDAY", "WEDNESDAY",
            "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToStartEditActivity = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intentToStartEditActivity);
            }
        });

//        TaskFragment taskFragment = new TaskFragment();
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .add(R.id.fragment_task_container, taskFragment)
//                .commit();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_week_days);

        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.pager_week_days);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Lifecycle lifecycle = getLifecycle();
        WeekDaysAdapter weekDaysAdapter = new WeekDaysAdapter(fragmentManager, lifecycle);

        viewPager.setAdapter(weekDaysAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(WEEK_DAYS_NAMES[position]);
            }
        });
        tabLayoutMediator.attach();
    }
}