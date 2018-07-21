package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class RareBooksActivity extends AppCompatActivity {

    RadioButton radioButtonSearch,radioButtonMyBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv__livre__rare);

        radioButtonSearch = findViewById(R.id.radioButtonSearch);
        radioButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity();
            }
        });

        radioButtonMyBooks = findViewById(R.id.radioButtonMyBooks);
        radioButtonMyBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBooksActivity();
            }
        });
    }

    private void SearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    private void MyBooksActivity() {
        Intent intent = new Intent(this, MyBooksActivity.class);
        startActivity(intent);
    }
}