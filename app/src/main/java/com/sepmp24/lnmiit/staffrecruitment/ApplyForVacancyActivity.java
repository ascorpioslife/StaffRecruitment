package com.sepmp24.lnmiit.staffrecruitment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sepmp24.lnmiit.staffrecruitment.Model.Vacancy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Nikunj on 11/24/2015.
 */
public class ApplyForVacancyActivity extends AppCompatActivity {


    // all required variables

    private static final String TAG_VACANCY_ID = "vacancy_id";
    private static final String TAG_VACANCY_TITLE = "vacancy_title";
    private static final String TAG_VACANCY_DEP_NO = "vacancy_dep_no";
    private static final String TAG_VACANCY_ACT_DATE = "vacancy_act_date";
    private static final String TAG_VACANCY_DEADLINE = "vacancy_deadline";
    private static final String TAG_VACANCY_SEATS = "vacancy_seats";
    private static final String TAG_VACANCY_STATUS = "vacancy_status";
    private static final String TAG_VACANCY_DES = "vacancy_des";
    private static final String TAG_CANDIDATE_ID = "cand_id";
    TextView title;
    TextView dep;
    TextView act_date;
    TextView app_deadline;
    TextView no_of_seats;
    TextView status;
    TextView des;
    Button apply;
    Vacancy newVacancy;
    String vacancy_id;
    String vacancy_title;
    String vacancy_depno;
    String vacancy_act_date;
    String vacancy_deadline;
    String vacancy_seats;
    String vacancy_status;
    String vacancy_des;
    boolean alreadyApplied=false;

    private String candID;
    SharedPreferences splogin;
    int resultCode=0;
    String resultMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_for_vacancy_layout);
        Bundle b = getIntent().getExtras();
        vacancy_id = b.getString(TAG_VACANCY_ID);
        vacancy_title = b.getString(TAG_VACANCY_TITLE);
        vacancy_depno = b.getString(TAG_VACANCY_DEP_NO);
        vacancy_act_date = b.getString(TAG_VACANCY_ACT_DATE);
        vacancy_deadline = b.getString(TAG_VACANCY_DEADLINE);
        vacancy_seats = b.getString(TAG_VACANCY_SEATS);
        vacancy_status = b.getString(TAG_VACANCY_STATUS);
        vacancy_des = b.getString(TAG_VACANCY_DES);

        splogin = getSharedPreferences("login", 0);
        candID = splogin.getString(TAG_CANDIDATE_ID, "");


        InitViews();

        CheckAlreadyAppliedOrNot();



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!alreadyApplied)
                {
                    Intent intent = new Intent(ApplyForVacancyActivity.this, ApplicationFormActivity.class);
                    intent.putExtra(TAG_VACANCY_ID, vacancy_id);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(ApplyForVacancyActivity.this, CheckStatus.class);
                    intent.putExtra(TAG_VACANCY_ID, vacancy_id);
                    startActivity(intent);
                }

            }
        });


    }

    private void InitViews() {
        title = (TextView) findViewById(R.id.p_title);
        dep = (TextView) findViewById(R.id.dep);
        act_date = (TextView) findViewById(R.id.act_date);
        app_deadline = (TextView) findViewById(R.id.app_deadline);
        no_of_seats = (TextView) findViewById(R.id.no_of_seats);
        status = (TextView) findViewById(R.id.status);

        apply = (Button) findViewById(R.id.b_apply);
        des = (TextView) findViewById(R.id.des);
        title.setText(vacancy_title);
        act_date.setText(vacancy_act_date);
        app_deadline.setText(vacancy_deadline);
        no_of_seats.setText(vacancy_seats);
        status.setText(vacancy_status);
        des.setText(vacancy_des);
        int depNo = Integer.parseInt(vacancy_depno);
        switch (depNo){

            case 1:
                dep.setText("IT DEPT");
                break;
            case 2:
                dep.setText("CSE DEPT");
                break;
            case 3:
                dep.setText("ECE DEPT");
                break;
            case 4:
                dep.setText("HSS DEPT");
                break;
            case 5:
                dep.setText("LIBRARY");
                break;
        }
    }

    void CheckAlreadyAppliedOrNot()
    {

        String url = "http://172.22.47.97/sepmp24/already_applied.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(TAG_VACANCY_ID, vacancy_id);
        params.put(TAG_CANDIDATE_ID, candID);

        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d("alreadyapplied", "Starting");

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


                    if(resultCode==1)
                    {
                        if(resultMsg.equals("true"))
                            alreadyApplied=true;

                    }
                    if(alreadyApplied)
                        apply.setText("Check Status");


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();
            }
        });
    }



}