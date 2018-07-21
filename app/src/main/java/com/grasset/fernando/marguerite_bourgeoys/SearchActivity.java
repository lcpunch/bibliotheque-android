package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SearchActivity extends AppCompatActivity {

    RadioButton radioButtonRareBooks,radioButtonMyBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);


        radioButtonRareBooks = findViewById(R.id.radioButtonRareBooks);
        radioButtonRareBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RareBooksActivity();
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

    private void RareBooksActivity() {
        Intent intent = new Intent(this, RareBooksActivity.class);
        startActivity(intent);
    }

    private void MyBooksActivity() {
        Intent intent = new Intent(this, MyBooksActivity.class);
        startActivity(intent);
    }
}