package com.example.user.projectthird.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.user.projectthird.R;


public abstract class MainActivity extends AppCompatActivity {

    private TextView mLifecycle;
    private StringBuilder mLifecycleFlow = new StringBuilder();
    private Handler mHandler = new Handler();
    private MainApplication mApp;
    private static final String LOG_TAG = "ActivitiesLaunch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logMethodName();
        setContentView(R.layout.activity_main);
        setupView();
        mApp = (MainApplication) getApplication();
        mApp.pushToStack(this);
    }

    private void logMethodName() {
        String methodName = getMethodName();
        Log.v(LOG_TAG, getLaunchMode() + ": " + methodName);
        mLifecycleFlow.append(methodName).append("\n");
        if (mLifecycle != null) {
            mLifecycle.setText(mLifecycleFlow.toString());
        }
    }

    private String getMethodName() {
        Thread current = Thread.currentThread();
        StackTraceElement trace = current.getStackTrace()[4];
        return trace.getMethodName();
    }

    private void setupView() {
        View activityLayout = findViewById(R.id.main_layout);
        activityLayout.setBackgroundResource(getBackgroundColour());
        TextView header = (TextView) findViewById(R.id.header);
        String launchMode = getLaunchMode();
        header.setText(launchMode);
        mLifecycle = (TextView) findViewById(R.id.lifecycle_content);
        mLifecycle.setMovementMethod(new ScrollingMovementMethod());
    }

    private String getLaunchMode() {
        return "[" + hashCode() + "] " + getClass().getSimpleName();
    }

    public void generalOnClick(View v) {
        startActivity(getNextIntent(v));
    }

    private Intent getNextIntent(View v) {
        Class<? extends MainActivity> nextActivity = null;
        switch (v.getId()) {
            case R.id.btn_standard:
                nextActivity = StandardActivity.class;
                break;
            case R.id.btn_singletop:
                nextActivity = SingleTopActivity.class;
                break;
            case R.id.btn_singletask:
                nextActivity = SingleTaskActivity.class;
                break;
            case R.id.btn_singleInstance:
                nextActivity = SingleInstanceActivity.class;
                break;
        }
        return new Intent(this, nextActivity);
    }

    private void checkIfReorderToFront() {
        Intent intent = getIntent();
        if (shouldReorderToFront(intent)){
            mApp.removeFromStack(this);
            mApp.pushToStack(this);
        }
    }

    private boolean shouldReorderToFront(Intent intent) {
        int flags = intent.getFlags();
        return (flags & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) > 0;
    }

    @Override
    protected void onResume(){
        super.onResume();
        logMethodName();
        checkIfReorderToFront();
        Runnable taskInfoDisplayer = new TaskInfoDisplayer(this);
        mHandler.postDelayed(taskInfoDisplayer, 500);
    }

    @Override
    protected void onStart(){
        super.onStart();
        logMethodName();
    }

    @Override
    protected void onPause(){
        super.onPause();
        logMethodName();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        logMethodName();
    }

    @Override
    protected void onStop(){
        super.onStop();
        logMethodName();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        logMethodName();
        mApp.removeFromStack(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        logMethodName();
    }

    @Override
    public void onContentChanged(){
        logMethodName();
        super.onContentChanged();
    }

    @Override
    protected void onNewIntent(Intent newIntent){
        super.onNewIntent(newIntent);
        logMethodName();
        setIntent(newIntent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        logMethodName();
    }

    @Override
    protected void onPostResume(){
        super.onPostResume();
        logMethodName();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        logMethodName();
    }

    public abstract int getBackgroundColour();
}
