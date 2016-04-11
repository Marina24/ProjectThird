package com.example.user.projectthird.ui;


import android.app.ActivityManager;
import android.app.Application;
import android.widget.ListView;


import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class MainApplication extends Application{

    private HashMap<Integer, Stack<MainActivity>> mTasks;

    private ActivityManager mManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mTasks = new HashMap<Integer, Stack<MainActivity>>();
    }

    public void pushToStack(MainActivity activity){
        int currentTaskId = getCurrentTaskId();
        if(!mTasks.containsKey(currentTaskId)){
            mTasks.put(currentTaskId, new Stack<MainActivity>());
        }
        Stack<MainActivity> stack = mTasks.get(currentTaskId);
        stack.add(activity);
    }

    public int getCurrentTaskId() {
        List<ActivityManager.RunningTaskInfo> runningTasks = mManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo runningTask = runningTasks.get(0);
        return runningTask.id;
    }

    public void removeFromStack(MainActivity activity){
        Stack<MainActivity> stack = mTasks.get(getCurrentTaskId());
        if(stack != null){
            stack.remove(activity);
        }
    }

    public Stack<MainActivity> getCurrentTask(){
        return mTasks.get(getCurrentTaskId());
    }
}
