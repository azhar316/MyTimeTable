package com.github.azhar316.mytimetable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final static String TAG = TaskAdapter.class.getSimpleName();

    private final ItemClickHandler mClickHandler;

    private String[] mTaskData;

    public TaskAdapter(ItemClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    interface ItemClickHandler {
        void onClick(String data);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder
            implements RecyclerView.OnClickListener {

        private TextView mTaskDataTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTaskDataTextView = itemView.findViewById(R.id.tv_task_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String taskData = mTaskData[adapterPosition];
            mClickHandler.onClick(taskData);
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
        Log.d(TAG, "onBindView: " + position + " " + mTaskData[position]);
        holder.mTaskDataTextView.setText(mTaskData[position]);
    }

    @Override
    public int getItemCount() {
        if (mTaskData == null) return 0;
        Log.d(TAG, "getItemCount: " + mTaskData.length);
        return mTaskData.length;
    }

    public void setTaskData(String[] taskData) {
        mTaskData = taskData;
        Log.d(TAG, "Inside setTaskData method");
        notifyDataSetChanged();
    }

}
