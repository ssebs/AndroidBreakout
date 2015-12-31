package io.github.ssebs.weargame;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainWearActivity extends WearableActivity {

    private BoxInsetLayout mContainerView;
    private GLSurfaceView mGLView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);

        Toast.makeText(MainWearActivity.this, "Press your home button to quit", Toast.LENGTH_SHORT).show();

        setAmbientEnabled();
        mGLView = new MyGLSurfaceView(this);
//        setContentView(mGLView);


        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mContainerView.addView(mGLView);

        //Intent intent = new Intent(MainWearActivity.this, OGLActivity.class);
        // startActivity(intent);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            mContainerView.setBackground(null);
        }
    }

    public void tellTime(View v)
    {
        String time = Calendar.getInstance().getTime().toString();
        Toast.makeText(MainWearActivity.this, time, Toast.LENGTH_SHORT).show();
    }


} // end class
