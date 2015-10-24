package com.sepmp24.lnmiit.staffrecruitment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Nikunj on 22-10-2015.
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting content view of splash screen


        //requesting full screen


        setContentView(R.layout.activity_splash_screen);

        //creating thread for 3 sec
        Thread firstThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreenActivity.this,FirstActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        //starting thread
        firstThread.start();
    }
}
