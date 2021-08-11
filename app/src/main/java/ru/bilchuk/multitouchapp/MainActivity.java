package ru.bilchuk.multitouchapp;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.pm.PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH;
import static android.content.pm.PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT;
import static android.content.pm.PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MultiTouch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        logMultiTouchCapabilities();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {
        FingersTestView fingersTestView = findViewById(R.id.draw_view);
        TextView logTextView = findViewById(R.id.txt_log);

        fingersTestView.setOnTouchListener((v, event) -> {
            int pointerCount = event.getPointerCount();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pointerCount; i++) {
                sb.append("Index: ").append(i);
                sb.append(", ID: ").append(event.getPointerId(i));
                sb.append(", X: ").append(event.getX(i));
                sb.append(", Y: ").append(event.getY(i));
                sb.append("\r\n");
            }

            logTextView.setText(sb.toString());

            if (event.getAction() == MotionEvent.ACTION_UP) {
                logTextView.setText("");
            }

            return false;
        });
    }

    private void logMultiTouchCapabilities() {
        PackageManager packageManager = getPackageManager();

        if (packageManager.hasSystemFeature(FEATURE_TOUCHSCREEN_MULTITOUCH)) {
            Log.i(TAG, "Basic two-finger gesture detection is supported");
        }

        if (packageManager.hasSystemFeature(FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
            Log.i(TAG, "2 or more simultaneous independent pointers tracking is supported");
        }

        if (packageManager.hasSystemFeature(FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)) {
            Log.i(TAG, "5 or more simultaneous independent pointers tracking is supported");
        }
    }
}


