package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class MyBooksActivity extends AppCompatActivity {

    RadioButton radioButtonRareBooks,radioButtonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_livres);

        radioButtonRareBooks = findViewById(R.id.radioButtonRareBooks);
        radioButtonRareBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RareBooksActivity();
            }
        });

        radioButtonSearch = findViewById(R.id.radioButtonSearch);
        radioButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity();
            }
        });
    }

    private void RareBooksActivity() {
        Intent intent = new Intent(this, RareBooksActivity.class);
        startActivity(intent);
    }
    private void SearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}