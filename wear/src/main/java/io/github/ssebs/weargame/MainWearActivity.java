package io.github.ssebs.weargame;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainWearActivity extends WearableActivity {

    private BoxInsetLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);

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
