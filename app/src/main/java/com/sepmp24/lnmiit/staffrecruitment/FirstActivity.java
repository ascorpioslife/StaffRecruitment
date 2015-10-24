package com.sepmp24.lnmiit.staffrecruitment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Nikunj on 24-10-2015.
 */
public class FirstActivity extends AppCompatActivity {
    //this activity is the first activity of the app

    private Button login;
    private Button creatAcc;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity_layout);
        initializeViews();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take action when user presses login button
            }
        });

        creatAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take action when presses create account button
                Intent intent = new Intent(FirstActivity.this,CreateAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initializeViews()
    {
        login = (Button) findViewById(R.id.b_login);
        creatAcc = (Button)findViewById(R.id.b_create_account);
        email = (EditText) findViewById(R.id.et_login_email);
        password = (EditText)findViewById(R.id.et_login_password);
    }
}
