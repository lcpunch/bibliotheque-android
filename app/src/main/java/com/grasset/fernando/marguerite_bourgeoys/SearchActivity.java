package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    DbConnecteur myDb = new DbConnecteur();
    ArrayList<Book> myBooks = new ArrayList<Book>();
    RadioButton radioButtonRareBooks,radioButtonMyBooks;
    RadioGroup searchType;
    ImageButton searchButton;
    Button reserverButton;
    TextView title,auteur,year;
    EditText rechercheBar;
    TableLayout t1;
    TableRow tr,trBasic;
    String IDUSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);

        IDUSER = getIntent().getStringExtra("IDUSER");

        t1 = (TableLayout) findViewById(R.id.T1);
        t1.setStretchAllColumns(true);

        searchType = (RadioGroup) findViewById(R.id.searchType);

        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioButton rb = (RadioButton) findViewById(searchType.getCheckedRadioButtonId());

                String dataSearcher;
                rechercheBar = (EditText) findViewById(R.id.rechercheBar);
                dataSearcher = rechercheBar.getText().toString();

                myBooks = myDb.getBooksInformationsRechercher(dataSearcher, (String) rb.getText());

                resetTableLayout();
                RefreshTableRow();
            }
        });


        radioButtonRareBooks = findViewById(R.id.radioButtonRareBooks);
        radioButtonRareBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RareBooksActivity(IDUSER);
            }
        });

        radioButtonMyBooks = findViewById(R.id.myPassport);
        radioButtonMyBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBooksActivity(IDUSER);
            }
        });
    }

    private void RareBooksActivity(String IDUSER) {
        Intent intent = new Intent(this, RareBooksActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }

    private void MyBooksActivity(String IDUSER) {
        Intent intent = new Intent(this, MyBooksActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }

    private void resetTableLayout() {
        trBasic = (TableRow) t1.getChildAt(0);
        t1.removeAllViews();
        t1.addView(trBasic);
    }

    private void RefreshTableRow() {
        for (int i = 0; i < myBooks.size(); i++) {

            tr = new TableRow(getBaseContext());
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            title = new TextView(getBaseContext());
            auteur = new TextView(getBaseContext());
            year = new TextView(getBaseContext());

            reserverButton = new Button(getBaseContext());
            reserverButton.setText("Confirmer");
            reserverButton.setMaxWidth(20);
            reserverButton.setId(i);
            tr.setId(i);
            final int index = i;

            title.setMaxWidth(75);
            auteur.setMaxWidth((150));
            year.setMaxWidth(25);

            title.setText(myBooks.get(i).getTitle());
            auteur.setText(myBooks.get(i).getAuteur());
            year.setText(myBooks.get(i).getYear());

            reserverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(myBooks.get(v.getId()).getTitle());
                    myDb.addBookToUser(IDUSER, myBooks.get(v.getId()).getTitle());
                }
            });

            tr.addView(title);
            tr.addView(auteur);
            tr.addView(year);
            tr.addView(reserverButton);

            t1.addView(tr);
        }
    }
}