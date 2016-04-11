package com.example.user.projectthird.ui;


import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.projectthird.R;

import java.util.Stack;

public class TaskInfoDisplayer implements Runnable{
    String LOG_TAG = "ActivitiesLaunch";
    private final MainApplication mApp;
    private final TextView mTaskIdField;
    private final LinearLayout mTaskView;

    public TaskInfoDisplayer(MainActivity mainActivity) {
        mApp = (MainApplication) mainActivity.getApplication();
        mTaskIdField = (TextView) mainActivity.findViewById(R.id.task_id_field);
        mTaskView = (LinearLayout) mainActivity.findViewById(R.id.task_view);
        mTaskView.removeAllViews();
    }

    @Override
    public void run(){
        Log.v(LOG_TAG, "===============================");
        showCurrentTaskId();
        showCurrentTaskActivites();
        Log.v(LOG_TAG, "===============================");
    }

    private void showCurrentTaskId() {
        int taskId = mApp.getCurrentTaskId();
        String taskMessage = "Activities in current task (id: " + taskId + ")";
        mTaskIdField.setText("Task id: " + taskId);
        Log.v(LOG_TAG, taskMessage);
    }

    private void showCurrentTaskActivites() {
        Stack<MainActivity> task = mApp.getCurrentTask();
        for (int location = task.size() - 1; location >= 0; location--) {
            MainActivity activity = task.get(location);
            showActivityDetails(activity);
        }
    }

    private void showActivityDetails(MainActivity activity) {
        String activityName = activity.getClass().getSimpleName();
        Log.v(LOG_TAG, activityName);
        ImageView activityRepresentation = getActivityRepresentation(activity);
        mTaskView.addView(activityRepresentation);
    }

    private ImageView getActivityRepresentation(MainActivity activity) {
        ImageView image = new ImageView(activity);
        int color = activity.getBackgroundColour();
        image.setBackgroundResource(color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
        params.setMargins(2, 2, 2, 2);
        image.setLayoutParams(params);
        return image;
    }
}
