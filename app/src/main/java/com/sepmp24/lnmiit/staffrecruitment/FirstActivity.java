package com.sepmp24.lnmiit.staffrecruitment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nikunj on 24-10-2015.
 */
public class FirstActivity extends AppCompatActivity {
    //this activity is the first activity of the app

    private Button login;
    private Button creatAcc;
    private EditText etEmail;
    private EditText etPassword;
    private String email;
    private String password;
    ProgressDialog prgDialog;
    private int resultCode=0;
    private String resultMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity_layout);
        initializeViews();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take action when user presses login button
                getDataFromViews();
                checkLogin();


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
        etEmail = (EditText) findViewById(R.id.et_login_email);
        etPassword = (EditText)findViewById(R.id.et_login_password);
    }
    public void getDataFromViews()
    {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

    }

    public void checkLogin()
    {
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Logging in Please wait...");
        prgDialog.setCancelable(false);
        boolean loginStatus = false;
        String url = "http://172.22.47.97/sepmp24/candidate_login.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);

        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d("LoginRequest", "Starting");
                prgDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    resultCode = response.getInt("success");
                    resultMsg = response.getString("message");

                } catch (JSONException ex) {
                    Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();

                } finally {
                    Snackbar.make(findViewById(android.R.id.content), resultMsg + " " + resultCode, Snackbar.LENGTH_SHORT).show();
                    prgDialog.dismiss();
                    if(resultCode==1)
                    {
                        //login successful
                        Intent intent = new Intent(FirstActivity.this, AllVacancies.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        ; //not successful

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                prgDialog.dismiss();
            }
        });


        return ;



    }
}
