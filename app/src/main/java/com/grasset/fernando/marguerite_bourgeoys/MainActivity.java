package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button newAccountButton,loginButton;

    DbConnecteur myDb = new DbConnecteur();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newAccountButton = (Button) findViewById(R.id.newAccountButton);
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity();
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.usernameChamp);
                String IDUSER = editText.getText().toString();
                editText = (EditText)findViewById(R.id.passwordChamp);
                String PASSWORD = editText.getText().toString();

                if(myDb.validateLogin(IDUSER, PASSWORD)) {
                    MyBooksActivity(IDUSER);
                }
            }
        });
    }

    private void SignupActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    private void MyBooksActivity(String IDUSER) {
        Intent intent = new Intent(this, MyBooksActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }
}