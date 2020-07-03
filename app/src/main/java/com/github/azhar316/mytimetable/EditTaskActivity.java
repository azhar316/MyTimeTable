package com.github.azhar316.mytimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.azhar316.mytimetable.database.AppDatabase;
import com.github.azhar316.mytimetable.database.TaskEntry;
import com.github.azhar316.mytimetable.viewmodels.EditTaskViewModel;
import com.github.azhar316.mytimetable.viewmodels.EditTaskViewModelFactory;

import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executor;


public class EditTaskActivity extends AppCompatActivity {

    public static final String TAG = EditTaskActivity.class.getSimpleName();

    public static final String TASK_DAY_ID = "EditTaskActivity.taskDayId";

    private static final int DEFAULT_DAY_ID = 0;

    public static final String EXTRA_TASK_ID = "EditTaskActivity.extraTaskId";

    public static final String INSTANCE_TASK_ID = "EditTaskActivity.instanceTaskId";

    private static final int DEFAULT_TASK_ID = -1;

    private EditText mTaskLabelEditText;
    private View mTaskColorDisplayView;
    private EditText mTaskDescriptionEditText;
    private EditText mStartTimeEditText;
    private EditText mEndTimeEditText;
    private Button mSaveButton;
    private Button mDeleteButton;

    private AppDatabase mDb;

    private TaskEntry mTask;

    private int mTaskId = DEFAULT_TASK_ID;

    private int mDayId = DEFAULT_DAY_ID;
    private Time mStartTime;
    private Time mEndTime;

    private final int DEFAULT_COLOR_DISPLAY_BACKGROUND_COLOR_ID = -1;

    private int mColorDisplayColorId = DEFAULT_COLOR_DISPLAY_BACKGROUND_COLOR_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intentThatStartedActivity = getIntent();

        if (savedInstanceState != null) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        if (intentThatStartedActivity != null && intentThatStartedActivity.hasExtra(TASK_DAY_ID)) {
            mDayId = intentThatStartedActivity.getIntExtra(TASK_DAY_ID, DEFAULT_DAY_ID);
        }

        if (intentThatStartedActivity != null && intentThatStartedActivity.hasExtra(EXTRA_TASK_ID)) {
            if (mTaskId == DEFAULT_TASK_ID) {
                mTaskId = intentThatStartedActivity.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                Log.d(TAG, "mTaskId: " + mTaskId);
                setUpViewModel();
            }
        } else {
            mTaskColorDisplayView.setBackgroundColor(getColor());
            mStartTime = new Time(9, 0, 0);
            mStartTimeEditText.setText(mStartTime.toString());
            mEndTime = new Time(10, 0, 0);
            mEndTimeEditText.setText(mEndTime.toString());
        }
    }

    private void initViews() {
        mTaskLabelEditText = (EditText) findViewById(R.id.et_task_label);
        mTaskColorDisplayView = (View) findViewById(R.id.color_display_view);
        mTaskDescriptionEditText = (EditText) findViewById(R.id.et_task_description);

        mStartTimeEditText = (EditText) findViewById(R.id.et_task_start_time);
        mStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePicker = new TimePickerFragment(mStartTimeEditText);
                timePicker.show(getSupportFragmentManager(), "From");
            }
        });

        mEndTimeEditText = (EditText) findViewById(R.id.et_task_end_time);
        mEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePicker = new TimePickerFragment(mEndTimeEditText);
                timePicker.show(getSupportFragmentManager(), "To");
            }
        });

        mSaveButton = (Button) findViewById(R.id.btn_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        mDeleteButton = (Button) findViewById(R.id.btn_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        Executor diskExecutor = AppExecutors.getInstance().diskIo();
        String label = mTaskLabelEditText.getText().toString();
        if (label.trim().equals("") || label.isEmpty()) {
            mTaskLabelEditText.setError("Label is required");
            return;
        }
        String description = mTaskDescriptionEditText.getText().toString();
        mStartTime = getTime(mStartTimeEditText);
        mEndTime = getTime(mEndTimeEditText);
        int color = getColor();
        Date date = new Date();
        final TaskEntry task = new TaskEntry(label, description, color, mDayId,
                mStartTime, mEndTime, date);
        diskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    mDb.taskDao().insertTask(task);
                } else {
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();
            }
        });
    }

    private void onDeleteButtonClicked() {
        if (mTaskId != DEFAULT_TASK_ID) {
            Executor diskExecutor = AppExecutors.getInstance().diskIo();
            diskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mDb.taskDao().deleteTask(mTask);
                }
            });
        }
        finish();
    }

    private void setUpViewModel() {
        EditTaskViewModelFactory factory = new EditTaskViewModelFactory(mDb, mTaskId);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, factory);
        EditTaskViewModel editTaskViewModel = viewModelProvider.get(EditTaskViewModel.class);
        final LiveData<TaskEntry> task = editTaskViewModel.getTask();
        task.observe(this, new Observer<TaskEntry>() {
            @Override
            public void onChanged(TaskEntry taskEntry) {
                task.removeObserver(this);
                populateUi(taskEntry);
            }
        });
    }

    private void populateUi(TaskEntry task) {
        if (task == null)
            return;
        mTask = task;
        mDayId = task.getDay();

        String taskLabel = task.getTaskLabel();
        mTaskLabelEditText.setText(taskLabel);
        int color = task.getColor();
        mColorDisplayColorId = color;
        mTaskColorDisplayView.setBackgroundColor(color);
        String taskDescription = task.getTaskDescription();
        mTaskDescriptionEditText.setText(taskDescription);
        mStartTime = task.getStartTime();
        mStartTimeEditText.setText(mStartTime.toString());
        mEndTime = task.getEndTime();
        mEndTimeEditText.setText(mEndTime.toString());
    }

    private int getColor() {
        if (mColorDisplayColorId != DEFAULT_COLOR_DISPLAY_BACKGROUND_COLOR_ID)
            return mColorDisplayColorId;
        mColorDisplayColorId = getRandomColor();
        return mColorDisplayColorId;
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256),
                rand.nextInt(256));
    }

    private Time getTime(EditText editText) {
        // Format - hh:mm:ss
        String time = editText.getText().toString();
        return Time.valueOf(time);
    }
}