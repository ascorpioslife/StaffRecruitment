package com.sepmp24.lnmiit.staffrecruitment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private EditText etpostalAddress;
    private EditText etdob;
    private EditText etpassword;
    private EditText etconfPassword;
    Calendar myCalendar = Calendar.getInstance();

    //creating variables for widgets
    private String name,email,mobileNo,fatherName,postalAddress,dob,password,confPassword;
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

                else {
                    Toast.makeText(CreateAccountActivity.this, "Everything is OK |200", Toast.LENGTH_SHORT).show();
                    //now send request to server
                }
            }
        });


        etdob.setOnClickListener(new View.OnClickListener(){
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
        etpostalAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etpostalAddress.getText().toString().length() <= 0)
                    etpostalAddress.setError("Enter Postal Address");
                else
                    etpostalAddress.setError(null);
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

        String myFormat = "dd/MM/yy"; //In which you need put here
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
        etpostalAddress = (EditText)findViewById(R.id.et_create_account_postaddress);
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
        postalAddress = etpostalAddress.getText().toString();
        password = etpassword.getText().toString();
        confPassword = etconfPassword.getText().toString();
        dob = etdob.getText().toString();

    }
    public boolean anyOneEmpty()
    {
        if(name.isEmpty()| email.isEmpty()|mobileNo.isEmpty()|fatherName.isEmpty()|postalAddress.isEmpty()|password.isEmpty()|confPassword.isEmpty())
            return true;
        else
            return false;
    }
}
