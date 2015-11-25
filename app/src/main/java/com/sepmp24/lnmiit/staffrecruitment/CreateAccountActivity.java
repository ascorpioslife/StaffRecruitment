package com.sepmp24.lnmiit.staffrecruitment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Nikunj on 24-10-2015.
 */
public class CreateAccountActivity extends AppCompatActivity {
    //creating widgets
    private Button register;
    private EditText etname;
    private EditText etemail;
    private EditText etmobileNo;
    private EditText etfatherName;
    private EditText etdob;
    private EditText etpassword;
    private EditText etconfPassword;
    ProgressDialog prgDialog;
    Calendar myCalendar = Calendar.getInstance();
    SharedPreferences splogin;
    String cand_id;
    //creating variables for widgets
    private String name,email,mobileNo,fatherName,dob,password,confPassword;
    private static final String TAG_CANDIDATE_ID = "cand_id";
    private int resultCode;
    private String message,resultMsg;
    int ageError=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_layout);
        initializeViews();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take action after user presses Register Button
                getDataFromViews();

                if (anyOneEmpty())
                    Snackbar.make(v.getRootView(), "Please Fill all the fields", Snackbar.LENGTH_SHORT).show();
                else if (!email.contains("@"))
                    Snackbar.make(v.getRootView(), "Please Fill Valid Email Address", Snackbar.LENGTH_SHORT).show();
                else if (mobileNo.length() != 10)
                    Snackbar.make(v.getRootView(), "Please Fill Valid Mobile No.", Snackbar.LENGTH_SHORT).show();
                else if (!password.equals(confPassword))
                    Snackbar.make(v.getRootView(), "Password does not match", Snackbar.LENGTH_SHORT).show();
                else if(ageError==-1)
                    Snackbar.make(v.getRootView(), "Invalid Date", Snackbar.LENGTH_LONG).show();
                else if(ageError==1)
                    Snackbar.make(v.getRootView(), "Your Age should be at least 21", Snackbar.LENGTH_LONG).show();


                else {
                    //now send request to server
                    createAccount();

                }

            }
        });


        etdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateAccountActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etname.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (etname.getText().toString().length() <= 0)
                    etname.setError("Enter First Name");
                else
                    etname.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etemail.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etemail.getText().toString().length() <= 0)
                    etemail.setError("Enter Email");
                else
                    etemail.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etmobileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etmobileNo.getText().toString().length() <= 0)
                    etmobileNo.setError("Enter Moblie No.");
                else
                    etmobileNo.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etdob.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {


                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                Date today = new Date();
                String todayString = df.format(today);

                try {
                    today = df.parse(todayString);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                Date userDate=null;
                try {
                    userDate  = df.parse(etdob.getText().toString());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println(todayString);
                System.out.println(etdob.getText().toString());
                if(getDiffYears(userDate,today)<0)
                    ageError=-1;
                else if(getDiffYears(userDate,today)<21)
                {   System.out.println(getDiffYears(userDate,today));
                   ageError = 1;
                    etdob.setError("Invalid Date");
                }

            }
        });
        etfatherName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etfatherName.getText().toString().length() <= 0)
                    etfatherName.setError("Enter Father's Name");
                else
                    etfatherName.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });


        etpassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etpassword.getText().toString().length() < 6)
                    etpassword.setError("Enter at least 6 Char long Password");
                else
                    etpassword.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                password = etpassword.getText().toString();

            }
        });
        etconfPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!etconfPassword.getText().toString().equals(password))
                    etconfPassword.setError("Password does not match");
                else
                    etconfPassword.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }


    };


    private void updateLabel() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etdob.setText(sdf.format(myCalendar.getTime()));
    }
    public void initializeViews()
    {

        register = (Button)findViewById(R.id.b_register);
        etname = (EditText)findViewById(R.id.et_create_account_name);
        etemail = (EditText) findViewById(R.id.et_create_account_email);
        etmobileNo = (EditText)findViewById(R.id.et_create_account_mobile);
        etfatherName = (EditText)findViewById(R.id.et_create_account_father);
        etdob = (EditText)findViewById(R.id.et_create_account_dob);
        etpassword = (EditText)findViewById(R.id.et_create_account_pwd);
        etconfPassword = (EditText)findViewById(R.id.et_create_account_conf_pwd);
    }

    public void getDataFromViews()
    {
        name = etname.getText().toString();
        email = etemail.getText().toString();
        mobileNo = etmobileNo.getText().toString();
        fatherName = etfatherName.getText().toString();
        password = etpassword.getText().toString();
        confPassword = etconfPassword.getText().toString();
        dob = etdob.getText().toString();

    }
    public boolean anyOneEmpty()
    {
        if(name.isEmpty()| email.isEmpty()|mobileNo.isEmpty()|fatherName.isEmpty()|password.isEmpty()|confPassword.isEmpty())
            return true;
        else
            return false;
    }

    public void createAccount()
    {

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Creating account Please wait...");
        prgDialog.setCancelable(false);
        prgDialog.show();
        String url = "http://172.22.47.97/sepmp24/createaccount.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("email", email);
        params.put("mobile",mobileNo);
        params.put("fathername",fatherName);
        params.put("password", password);
        params.put("dob", dob);
        System.out.println(dob);

        client.post(url, params, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                // called before request is started
                Log.d("CreateAccountRequest", "Starting");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                //System.out.println(response.toString());
                try {
                    resultCode = response.getInt("success");
                    resultMsg = response.getString("message");
                    cand_id = response.getString(TAG_CANDIDATE_ID);
                } catch (JSONException ex) {
                    Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                } finally {
                    if (resultCode == 1) {
                        Snackbar.make(findViewById(android.R.id.content), resultMsg, Snackbar.LENGTH_SHORT).show();
                        splogin = getSharedPreferences("login",0);
                        SharedPreferences.Editor edit = splogin.edit();
                        edit.putString(TAG_CANDIDATE_ID, cand_id);
                        edit.commit();
                        Intent intent = new Intent(CreateAccountActivity.this, AllVacancies.class);
                        startActivity(intent);
                        finish();

                    } else
                        Snackbar.make(findViewById(android.R.id.content), resultMsg, Snackbar.LENGTH_SHORT).show();
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
        return;
    }
}
