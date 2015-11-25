package com.sepmp24.lnmiit.staffrecruitment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
 * Created by Nikunj on 11/25/2015.
 */
public class ApplicationFormActivity extends AppCompatActivity {

    private String candID;
    private String vacancyID;
    SharedPreferences splogin;

    private static final String TAG_VACANCY_ID = "vacancy_id";
    private static final String TAG_CANDIDATE_ID = "cand_id";
    private static final String TAG_CANDIDATE_NAME = "cand_name";
    private static final String TAG_CANDIDATE_EMAIL = "cand_email";
    private static final String TAG_CANDIDATE_MOBILENO = "cand_mobile";
    private static final String TAG_CANDIDATE_FNAME = "cand_fname";
    private static final String TAG_CANDIDATE_DOB = "cand_dob";
    private static final String TAG_CANDIDATE_POSTALADD = "cand_postadd";
    private static final String TAG_CANDIDATE_EDU = "cand_eduqual";
    private static final String TAG_CANDIDATE_EXTRACURR = "cand_extrcurr";
    private static final String TAG_APP_FORM_WORKEXP = "work_exp";
    private static final String TAG_APP_FORM_ACHV = "achv";
    private static final String TAG_APP_FORM_ID = "application_id";


    EditText name;
    EditText email;
    EditText mobileNo;
    EditText fname;
    EditText dob;
    EditText postalAddress;
    EditText edu1;
    EditText edu2;
    EditText edu3;
    EditText edu4;
    EditText excurr1;
    EditText excurr2;
    EditText workexp1;
    EditText workexp2;
    EditText ach1;
    EditText ach2;

    String nameStr;
    String emailStr;
    String mobileNoStr;
    String fnameStr;
    String dobStr;
    String postalAddressStr;
    String edu1Str;
    String edu2Str;
    String edu3Str;
    String edu4Str;
    String excurr1Str;
    String excurr2Str;
    String workexp1Str;
    String workexp2Str;
    String ach1Str;
    String ach2Str;
    Button submit;
    String education;
    String extrCurrAct;
    ProgressDialog prgDialog;

    int resultCode=0;
    int applicationID=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_form_layout);
        Bundle b = getIntent().getExtras();
        vacancyID = b.getString(TAG_VACANCY_ID);
        splogin = getSharedPreferences("login", 0);
        candID = splogin.getString(TAG_CANDIDATE_ID, "");
        System.out.println("Cand id:  " + candID + "and Vacancy id: " + vacancyID);
        setContentView(R.layout.application_form_layout);
        InitViews();

        fillDataFromDatabase();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anyOneIsEmpty())
                    Snackbar.make(findViewById(android.R.id.content), "Please fill all the fields", Snackbar.LENGTH_SHORT).show();
                else
                {
                    new AlertDialog.Builder(ApplicationFormActivity.this)
                            .setTitle("Confirmation")
                            .setMessage("Do you really want to Submit this form?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    SubmitApplicationForm();
                                }})
                            .setNegativeButton("No", null).show();
                }

            }
        });






    }
    private void InitViews()
    {
        name = (EditText)findViewById(R.id.et_apply_name);
        email = (EditText)findViewById(R.id.et_apply_email);
        mobileNo = (EditText)findViewById(R.id.et_apply_mobile);
        fname = (EditText)findViewById(R.id.et_apply_fname);
        dob = (EditText)findViewById(R.id.et_apply_dob);
        postalAddress = (EditText)findViewById(R.id.et_apply_postaladd);
        edu1 = (EditText)findViewById(R.id.et_apply_eduqual1);
        edu2 = (EditText)findViewById(R.id.et_apply_eduqua2);
        edu3 = (EditText)findViewById(R.id.et_apply_eduqual3);
        edu4 = (EditText)findViewById(R.id.et_apply_eduqual4);
        excurr1 = (EditText)findViewById(R.id.et_apply_extracurr1);
        excurr2 = (EditText)findViewById(R.id.et_apply_extracurr2);
        workexp1 = (EditText)findViewById(R.id.et_apply_workexp1);
        workexp2 = (EditText)findViewById(R.id.et_apply_workexp2);
        ach1 = (EditText)findViewById(R.id.et_apply_achievement1);
        ach2 = (EditText)findViewById(R.id.et_apply_achievement2);
        submit = (Button) findViewById(R.id.b_submitform);



    }

    private void fillDataFromDatabase()
    {
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Loading Details Please wait...");
        prgDialog.setCancelable(false);
        prgDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", candID);
        String url = "http://172.22.47.97/sepmp24/getcand_details.php";
        client.post(url, params, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                // called before request is started
                Log.d("fillingCandDetails", "Starting");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                //System.out.println(response.toString());
                try {

                    System.out.println(response.toString());
                    resultCode = response.getInt("success");
                    nameStr = response.getString(TAG_CANDIDATE_NAME);
                    emailStr = response.getString(TAG_CANDIDATE_EMAIL);
                    mobileNoStr = response.getString(TAG_CANDIDATE_MOBILENO);
                    fnameStr = response.getString(TAG_CANDIDATE_FNAME);
                    dobStr = response.getString(TAG_CANDIDATE_DOB);
                    postalAddressStr = response.getString(TAG_CANDIDATE_POSTALADD);
                    education = response.getString(TAG_CANDIDATE_EDU);
                    extrCurrAct = response.getString(TAG_CANDIDATE_EXTRACURR);

                } catch (JSONException ex) {
                    Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                } finally {
                    if (resultCode == 1) {

                        Snackbar.make(findViewById(android.R.id.content), "Information Retrieved", Snackbar.LENGTH_SHORT).show();
                        SetEditTexts();
                    } else
                        Snackbar.make(findViewById(android.R.id.content),"Something went wrong with server" , Snackbar.LENGTH_SHORT).show();
                    prgDialog.dismiss();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Snackbar.make(findViewById(android.R.id.content), "Something went wrong :4XX", Snackbar.LENGTH_SHORT).show();
                prgDialog.dismiss();
            }
        });

    }

    private void SetEditTexts()
    {
        name.setText(nameStr);
        email.setText(emailStr);
        mobileNo.setText(mobileNoStr);
        fname.setText(fnameStr);
        dob.setText(dobStr);
        if(postalAddressStr!=null & !postalAddressStr.equals("null") & !postalAddressStr.equals(""))
        postalAddress.setText(postalAddressStr);
        if(education!=null & !education.equals("null") & !education.equals(""))
        {
            String [] edus = education.split(";");
            if(edus.length>=1)
            edu1.setText(edus[0]);
            if(edus.length>=2)
            edu2.setText(edus[1]);
            if(edus.length>=3)
            edu3.setText(edus[2]);
            if(edus.length>=4)
            edu4.setText(edus[3]);

        }
        if(extrCurrAct!=null & !extrCurrAct.equals("null") & !extrCurrAct.equals(""))
        {
            String [] extracurrs = extrCurrAct.split(";");
            if(extracurrs.length>=1)
            excurr1.setText(extracurrs[0]);
            if(extracurrs.length>=2)
            excurr2.setText(extracurrs[1]);
        }


    }

    private void SubmitApplicationForm()
    {
            if(!anyOneIsEmpty())
            {
                prgDialog = new ProgressDialog(this);
                prgDialog.setMessage("Submitting form Please wait...");
                prgDialog.setCancelable(true);
                prgDialog.show();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("id", candID);
                params.put(TAG_VACANCY_ID,vacancyID);
                params.put(TAG_CANDIDATE_NAME,name.getText().toString());
                params.put(TAG_CANDIDATE_EMAIL,email.getText().toString());
                params.put(TAG_CANDIDATE_MOBILENO, mobileNo.getText().toString());
                params.put(TAG_CANDIDATE_FNAME,name.getText().toString());
                params.put(TAG_CANDIDATE_DOB,dob.getText().toString());
                params.put(TAG_CANDIDATE_POSTALADD,postalAddress.getText().toString());
                params.put(TAG_CANDIDATE_EDU,edu1.getText().toString()+";"+edu2.getText().toString()+";"+edu3.getText().toString()+";"+
                        edu4.getText().toString());
                params.put(TAG_CANDIDATE_EXTRACURR,excurr1.getText().toString()+";"+excurr2.getText().toString());
                params.put(TAG_APP_FORM_WORKEXP,workexp1.getText().toString()+";"+workexp2.getText().toString());
                params.put(TAG_APP_FORM_ACHV,ach1.getText().toString()+";"+ach2.getText().toString());

                String url = "http://172.22.47.97/sepmp24/submit_form.php";
                client.post(url, params, new JsonHttpResponseHandler() {


                    @Override
                    public void onStart() {
                        // called before request is started
                        Log.d("SubmittingAppform", "Starting");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                        // Handle resulting parsed JSON response here
                        //System.out.println(response.toString());
                        try {

                            System.out.println(response.toString());
                            resultCode = response.getInt("success");
                            applicationID = response.getInt(TAG_APP_FORM_ID);



                        } catch (JSONException ex) {
                            Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                        } finally {
                            if (resultCode == 1) {
                                //show dialog here
                                Snackbar.make(findViewById(android.R.id.content), "Application Submitted Successfully!", Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(ApplicationFormActivity.this, AllVacancies.class);
                                startActivity(intent);
                                finish();
                            } else
                                Snackbar.make(findViewById(android.R.id.content),"Something went wrong" , Snackbar.LENGTH_SHORT).show();
                            prgDialog.dismiss();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                        prgDialog.dismiss();
                    }
                });
            }
        else
            {
                Snackbar.make(findViewById(android.R.id.content), "Please fill all the required fields", Snackbar.LENGTH_LONG).show();
            }
    }
    private boolean anyOneIsEmpty()
    {
        if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty()||mobileNo.getText().toString().isEmpty()
                || fname.getText().toString().isEmpty()||dob.getText().toString().isEmpty() || postalAddress.getText().toString().isEmpty()
                || edu1.getText().toString().isEmpty()||edu2.getText().toString().isEmpty()||edu3.getText().toString().isEmpty())
            return true;
        else return false;

    }
}
