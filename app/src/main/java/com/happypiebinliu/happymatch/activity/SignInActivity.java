package com.happypiebinliu.happymatch.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.happypiebinliu.happymatch.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No title
        //requestWindowFeature(Window.FEATURE_NO_TITLE); if extends Activity you can use it
        // if you extends AppCompatActivity, can use ActionBar.In the Activity,it is titleBar.
        this.getSupportActionBar().hide();
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_in);
    }
}
