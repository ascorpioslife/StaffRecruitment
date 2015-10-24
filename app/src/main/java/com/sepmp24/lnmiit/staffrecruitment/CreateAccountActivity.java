package com.sepmp24.lnmiit.staffrecruitment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                if(anyOneEmpty())
                    Toast.makeText(CreateAccountActivity.this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
                else if(!email.contains("@"))
                    Toast.makeText(CreateAccountActivity.this,"Please Fill Valid Email Address",Toast.LENGTH_SHORT).show();
                else if(mobileNo.length()!=10)
                    Toast.makeText(CreateAccountActivity.this,"Please Fill Valid Mobile No.",Toast.LENGTH_SHORT).show();
                else if(!password.equals(confPassword))
                    Toast.makeText(CreateAccountActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(CreateAccountActivity.this,"Everything is OK |200",Toast.LENGTH_SHORT).show();
                    //now send request to server
                }
            }
        });
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

    }
    public boolean anyOneEmpty()
    {
        if(name.isEmpty()| email.isEmpty()|mobileNo.isEmpty()|fatherName.isEmpty()|postalAddress.isEmpty()|password.isEmpty()|confPassword.isEmpty())
            return true;
        else
            return false;
    }
}
