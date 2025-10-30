package com.example.staircounter;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor stepDetector;
    private TextView stepCountText;
    private TextView goalText;
    private TextView percentageText;
    private TextView motivationalText;
    private TextView statusMessage;
    private ProgressBar progressBar;
    private ImageView stepIcon;
    private MaterialButton resetButton;

    private int stepCount = 0;
    private static final int DAILY_GOAL = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        stepCountText = findViewById(R.id.stepCount);
        goalText = findViewById(R.id.goalText);
        percentageText = findViewById(R.id.percentageText);
        motivationalText = findViewById(R.id.motivationalText);
        statusMessage = findViewById(R.id.statusMessage);
        progressBar = findViewById(R.id.progressBar);
        stepIcon = findViewById(R.id.stepIcon);
        resetButton = findViewById(R.id.resetButton);

        // Set up sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepDetector != null) {
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            showError("Step Detector not available on this device");
        }

        // Set up reset button
        resetButton.setOnClickListener(v -> resetCounter());

        // Initialize display
        updateUI();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;
            updateUI();
            animateStepIcon();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private void updateUI() {
        // Update step count
        stepCountText.setText(String.valueOf(stepCount));

        // Update goal text
        goalText.setText(String.format("Goal: %,d / %,d steps", stepCount, DAILY_GOAL));

        // Calculate and update percentage
        int percentage = (int) ((stepCount / (float) DAILY_GOAL) * 100);
        percentage = Math.min(percentage, 100); // Cap at 100%
        percentageText.setText(percentage + "%");

        // Update progress bar
        progressBar.setProgress(percentage);

        // Update motivational message
        updateMotivationalMessage(percentage);
    }

    private void updateMotivationalMessage(int percentage) {
        String message;
        if (percentage == 0) {
            message = "Start walking to track your steps!";
        } else if (percentage < 25) {
            message = "Great start! Keep it up!";
        } else if (percentage < 50) {
            message = "You're doing amazing! 25% there!";
        } else if (percentage < 75) {
            message = "Halfway there! Don't stop now!";
        } else if (percentage < 100) {
            message = "Almost at your goal! You got this!";
        } else {
            message = "Goal achieved! You're a star!";
        }
        motivationalText.setText(message);
    }

    private void animateStepIcon() {
        // Scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(stepIcon, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(stepIcon, "scaleY", 1.0f, 1.2f, 1.0f);
        scaleX.setDuration(300);
        scaleY.setDuration(300);
        scaleX.setInterpolator(new BounceInterpolator());
        scaleY.setInterpolator(new BounceInterpolator());
        scaleX.start();
        scaleY.start();
    }

    private void resetCounter() {
        stepCount = 0;
        updateUI();

        // Animate the reset
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(stepCountText, "alpha", 1.0f, 0.0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(stepCountText, "alpha", 0.0f, 1.0f);
        fadeOut.setDuration(150);
        fadeIn.setDuration(150);
        fadeOut.start();
        fadeOut.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                fadeIn.start();
            }
        });
    }

    private void showError(String error) {
        statusMessage.setText(error);
        statusMessage.setVisibility(View.VISIBLE);
        stepCountText.setText("0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepDetector != null) {
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
