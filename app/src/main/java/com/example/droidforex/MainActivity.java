package com.example.droidforex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.progress.progressview.ProgressView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] colorList = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

        ProgressView lProgressView = findViewById(R.id.progressView1);
        lProgressView.applyGradient(colorList);

        ProgressView rProgressView = findViewById(R.id.progressView2);
        rProgressView.applyGradient(colorList);

    }
}
