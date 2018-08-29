package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class updateStatusActivity extends AppCompatActivity {

    EditText editAdress, editPhone, editEmail;
    ArrayList<String> userInformation;
    DbConnecteur myDb = new DbConnecteur();
    Button saveButton;
    String IDUSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        editAdress = findViewById(R.id.editAdress);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);


        IDUSER = getIntent().getStringExtra("IDUSER");

        userInformation = myDb.getUserInformation(IDUSER);

        editAdress.setText(userInformation.get(0));
        editPhone.setText(userInformation.get(1));
        editEmail.setText((userInformation.get(2)));


        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInformation.set(0, editAdress.getText().toString());
                userInformation.set(1, editPhone.getText().toString());
                userInformation.set(2, editEmail.getText().toString());


                myDb.UpdateUserInfo(userInformation, IDUSER);
                MyBooksActivity(IDUSER);
            }
        });



    }

    private void MyBooksActivity(String IDUSER) {
        Intent intent = new Intent(this, MyBooksActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }
}
