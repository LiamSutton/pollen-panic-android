package com.ls.pollenpanic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// main entry point for application and used to host fragments
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}