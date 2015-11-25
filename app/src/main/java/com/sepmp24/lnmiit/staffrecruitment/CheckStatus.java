package com.sepmp24.lnmiit.staffrecruitment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Nikunj on 11/25/2015.
 */
public class CheckStatus extends AppCompatActivity {

    TextView name;
    TextView vacancy_id;
    TextView application_id;
    TextView status;
    SharedPreferences splogin;
    String candID,vacancyID,candName;

    private static final String TAG_VACANCY_ID = "vacancy_id";
    private static final String TAG_CANDIDATE_ID = "cand_id";
    private static final String TAG_CANDIDATE_NAME = "cand_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_status_layout);

        Bundle b = getIntent().getExtras();
        splogin = getSharedPreferences("login", 0);
        candID = splogin.getString(TAG_CANDIDATE_ID, "");
        candName = splogin.getString(TAG_CANDIDATE_NAME, "");
        vacancyID = b.getString(TAG_VACANCY_ID);

        name = (TextView)findViewById(R.id.name);
        vacancy_id = (TextView)findViewById(R.id.vacancy_id);
        application_id = (TextView)findViewById(R.id.app_id);






    }
}
