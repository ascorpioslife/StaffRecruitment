package com.sepmp24.lnmiit.staffrecruitment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Nikunj on 22-10-2015.
 */
public class SplashScreenActivity extends AppCompatActivity {
    View.OnClickListener mOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting content view of splash screen





        setContentView(R.layout.activity_splash_screen);

        //creating thread for 3 sec
        Thread firstThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    if(isNetworkAvailable())
                        nextActivity();
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content),"Network not available" ,Snackbar.LENGTH_INDEFINITE )
                                .setAction("Retry", mOnClickListener)
                                .setActionTextColor(Color.RED).show();
                    }

                }
            }
        };
        //starting thread
        firstThread.start();

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable())
                    nextActivity();
                else
                {
                    Snackbar.make(findViewById(android.R.id.content),"Network not available" ,Snackbar.LENGTH_INDEFINITE )
                            .setAction("Retry", mOnClickListener)
                            .setActionTextColor(Color.RED).show();
                }
            }
        };
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void nextActivity()
    {
        Intent intent = new Intent(SplashScreenActivity.this,FirstActivity.class);
        startActivity(intent);
        finish();
    }


}
