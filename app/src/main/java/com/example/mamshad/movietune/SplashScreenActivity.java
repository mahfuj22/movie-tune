package com.example.mamshad.movietune;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        // METHOD 1

        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(2 * 1000);

                    // After 5 seconds redirect to another intent
                    Intent intent = null;
                    intent = new Intent(getBaseContext(), HomeActivity.class);

                    startActivity(intent);

                    //Remove activity
                    finish();

                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        };

        // start thread
        background.start();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
