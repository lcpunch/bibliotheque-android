package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignupActivity extends AppCompatActivity {

    Button signupButton;
    DbConnecteur myDb = new DbConnecteur();
    String SURNAME,USERNAME, ADDRESS, EMAIL,PHONE, IDUSER, MD5PASSWORD;
    String IDUSERTYPE = "client";
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        radioGroup = (RadioGroup) findViewById(R.id.IDENTITYCARD);


        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.SURNAME);
                SURNAME = editText.getText().toString();
                editText = (EditText)findViewById(R.id.USERNAME);
                USERNAME = editText.getText().toString();
                editText = (EditText)findViewById(R.id.IDUSER);
                IDUSER = editText.getText().toString();
                editText = (EditText)findViewById(R.id.MD5PASSWORD);
                MD5PASSWORD = editText.getText().toString();
                editText = (EditText)findViewById(R.id.ADDRESS);
                ADDRESS = editText.getText().toString();
                editText = (EditText)findViewById(R.id.PHONE);
                PHONE = editText.getText().toString();
                editText = (EditText)findViewById(R.id.EMAIL);
                EMAIL = editText.getText().toString();

                myDb.addUser(SURNAME,USERNAME, IDUSERTYPE, IDUSER,MD5PASSWORD, ADDRESS,PHONE, EMAIL);


                //ValidationActivity();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb= (RadioButton) findViewById(checkedId);
                IDUSER = rb.getText().toString();
            }
        });
    }

    private void ValidationActivity() {
        Intent intent = new Intent(this, ValidationActivity.class);
        startActivity(intent);
    }
}


