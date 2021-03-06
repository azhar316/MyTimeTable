package com.github.azhar316.mytimetable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.azhar316.mytimetable.database.TaskEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final static String TAG = TaskAdapter.class.getSimpleName();

    private final ItemClickHandler mClickHandler;

    private List<TaskEntry> mTaskEntries;

    public TaskAdapter(ItemClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    interface ItemClickHandler {
        void onClick(int itemPosition);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder
            implements RecyclerView.OnClickListener {

        public TextView mStartTimeTextView;
        public TextView mLabelTextView;
        public TextView mEndTimeTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mStartTimeTextView = itemView.findViewById(R.id.tv_start_time);
            mLabelTextView = itemView.findViewById(R.id.tv_task_label);
            mEndTimeTextView = itemView.findViewById(R.id.tv_end_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.task_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskEntry task = mTaskEntries.get(position);
        int color = task.getColor();
        String startTime = task.getStartTime().toString();
        String endTime = task.getEndTime().toString();
        String taskLabel = task.getTaskLabel();
        holder.mStartTimeTextView.setText(startTime);
        holder.mLabelTextView.setText(taskLabel);
        holder.mEndTimeTextView.setText(endTime);
        holder.itemView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) return 0;
        return mTaskEntries.size();
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

}
