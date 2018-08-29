package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBooksActivity extends AppCompatActivity {

    RadioButton radioButtonRareBooks,radioButtonSearch;
    DbConnecteur myDb = new DbConnecteur();
    TableLayout t1,t2;
    TableRow tr,trBasic1,trBasic2;
    ArrayList<Book> myBooks = new ArrayList<Book>();
    ArrayList<Book> myBooksRDV = new ArrayList<Book>();
    String IDUSER;
    ImageButton updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_livres);

        IDUSER = getIntent().getStringExtra("IDUSER");

        t1 = (TableLayout) findViewById(R.id.T1);
        t1.setStretchAllColumns(true);

        t2 = (TableLayout) findViewById(R.id.T2);
        t2.setStretchAllColumns(true);

        myBooks = myDb.getMyBooks(IDUSER);
        myBooksRDV = myDb.getMyLivreRareRDV(IDUSER);

        resetTableLayout();
        RefreshTableRow();

        resetTableLayoutRDV();
        RefreshTableRowRDV();

        radioButtonRareBooks = findViewById(R.id.radioButtonRareBooks);
        radioButtonRareBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RareBooksActivity(IDUSER);
            }
        });

        radioButtonSearch = findViewById(R.id.radioButtonSearch);
        radioButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity(IDUSER);
            }
        });

        updateButton = findViewById(R.id.updateUserData);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStatusActivity(IDUSER);
            }
        });

    }

    private void RareBooksActivity(String IDUSER) {
        Intent intent = new Intent(this, RareBooksActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }
    private void SearchActivity(String IDUSER) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }

    private void UpdateStatusActivity(String IDUSER) {
        Intent intent = new Intent(this, updateStatusActivity.class);
        intent.putExtra("IDUSER", IDUSER);
        startActivity(intent);
    }

    private void resetTableLayout() {
        trBasic1 = (TableRow) t1.getChildAt(0);
        t1.removeAllViews();
        t1.addView(trBasic1);
    }

    private void RefreshTableRow() {
        TextView title,auteur,dtreturn;
        for (int i = 0; i < myBooks.size(); i++) {
            tr = new TableRow(getBaseContext());
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            title = new TextView(getBaseContext());
            auteur = new TextView(getBaseContext());
            dtreturn = new TextView(getBaseContext());

            Button renouvelerButton = new Button(getBaseContext());
            renouvelerButton.setText("Renouveler");
            renouvelerButton.setMaxWidth(20);
            renouvelerButton.setId(i);
            tr.setId(i);

            title.setMaxWidth(75);
            auteur.setMaxWidth((150));
            dtreturn.setMaxWidth(25);

            title.setText(myBooks.get(i).getTitle());
            auteur.setText(myBooks.get(i).getAuteur());
            dtreturn.setText(myBooks.get(i).getDtappointment());

            renouvelerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDb.RenewBook(IDUSER,myBooks.get(v.getId()).getTitle(),myBooks.get(v.getId()).getDtappointment());
                    myBooks = myDb.getMyBooks(IDUSER);
                    resetTableLayout();
                    RefreshTableRow();
                }
            });

            tr.addView(title);
            tr.addView(auteur);
            tr.addView(dtreturn);
            tr.addView(renouvelerButton);

            t1.addView(tr);
        }
    }

    private void resetTableLayoutRDV() {
        trBasic2 = (TableRow) t2.getChildAt(0);
        t2.removeAllViews();
        t2.addView(trBasic2);
    }

    private void RefreshTableRowRDV() {
        TextView title,auteur,dtappointment;
        for (int i = 0; i < myBooksRDV.size(); i++) {
            tr = new TableRow(getBaseContext());
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            title = new TextView(getBaseContext());
            auteur = new TextView(getBaseContext());
            dtappointment = new TextView(getBaseContext());

            Button renouvelerButton = new Button(getBaseContext());
            renouvelerButton.setText("Cancel");
            renouvelerButton.setMaxWidth(20);
            renouvelerButton.setId(i);
            tr.setId(i);

            title.setMaxWidth(75);
            auteur.setMaxWidth((150));
            dtappointment.setMaxWidth(25);

            title.setText(myBooksRDV.get(i).getTitle());
            auteur.setText(myBooksRDV.get(i).getAuteur());
            dtappointment.setText(myBooksRDV.get(i).getDtappointment());

            renouvelerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDb.CancelRDV(IDUSER,myBooksRDV.get(v.getId()).getTitle(),myBooksRDV.get(v.getId()).getDtappointment());
                    myBooksRDV = myDb.getMyLivreRareRDV(IDUSER);
                    resetTableLayoutRDV();
                    RefreshTableRowRDV();
                }
            });

            tr.addView(title);
            tr.addView(auteur);
            tr.addView(dtappointment);
            tr.addView(renouvelerButton);

            t2.addView(tr);
        }
    }
}