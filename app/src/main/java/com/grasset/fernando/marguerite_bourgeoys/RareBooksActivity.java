package com.grasset.fernando.marguerite_bourgeoys;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RareBooksActivity extends AppCompatActivity {

    RadioButton radioButtonSearch,radioButtonMyBooks,radioButtonPeriode;
    DbConnecteur myDb = new DbConnecteur();
    ArrayList<Book> myBooks = new ArrayList<Book>();
    RadioGroup searchType,periodeDate;
    ImageButton searchButton;
    Button rdvButton;
    TextView title,auteur,year;
    EditText rechercheBar;
    TableLayout t1;
    TableRow tr,trBasic;
    DatePicker datePicker;
    String IDUSER;
    CharSequence periodeOfDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv__livre__rare);

        IDUSER = getIntent().getStringExtra("IDUSER");

        datePicker = (DatePicker) findViewById(R.id.dpDate);
        periodeDate = (RadioGroup) findViewById(R.id.periodeDate);

        datePicker.setMinDate(System.currentTimeMillis() - 1000);

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

                myBooks = myDb.getBooksInformationsRDV(dataSearcher, (String) rb.getText());

                resetTableLayout();
                RefreshTableRow();
            }
        });

        radioButtonSearch = findViewById(R.id.radioButtonSearch);
        radioButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity(IDUSER);
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

    private void SearchActivity(String IDUSER) {
        Intent intent = new Intent(this, SearchActivity.class);
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

            rdvButton = new Button(getBaseContext());
            rdvButton.setText("RDV");
            rdvButton.setMaxWidth(20);
            rdvButton.setId(i);
            tr.setId(i);
            final int index = i;

            title.setMaxWidth(75);
            auteur.setMaxWidth((150));
            year.setMaxWidth(25);

            title.setText(myBooks.get(i).getTitle());
            auteur.setText(myBooks.get(i).getAuteur());
            year.setText(myBooks.get(i).getYear());

            rdvButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton rb = (RadioButton) findViewById(periodeDate.getCheckedRadioButtonId());
                    System.out.println(IDUSER + myBooks.get(v.getId()).getTitle() + datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear() + rb.getText());
                    myDb.RDVtoUser(IDUSER, myBooks.get(v.getId()).getTitle(),datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth(), rb.getText().equals("AM")?1:2);
                }
            });

            tr.addView(title);
            tr.addView(auteur);
            tr.addView(year);
            tr.addView(rdvButton);

            t1.addView(tr);
        }
    }
}