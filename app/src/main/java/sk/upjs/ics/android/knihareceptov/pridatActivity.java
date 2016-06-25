package sk.upjs.ics.android.knihareceptov;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class pridatActivity extends AppCompatActivity {
    private EditText nazovEditText;
   // private EditText kategoriaEditText;
    private RadioButton predjedlaCheckBox;
    private RadioButton polievkyCheckBox;
    private RadioButton masiteCheckBox;
    private RadioButton bezmasiteCheckBox;
    private RadioButton salatyCheckBox;
    private RadioButton dezertyCheckBox;
    private RadioButton napojeCheckBox;

    private EditText ingrediencieEditText;
    private EditText postupEditText;
    private Long id;
    private boolean inzertovat=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridat);
        nazovEditText = (EditText) findViewById(R.id.NazovEditText);


        predjedlaCheckBox = (RadioButton) findViewById(R.id.PredjedlaRadioButton);
        polievkyCheckBox = (RadioButton) findViewById(R.id.PolievkyRadioButton);
        masiteCheckBox = (RadioButton) findViewById(R.id.MasiteRadioButton);
        bezmasiteCheckBox = (RadioButton) findViewById(R.id.bexmasiteRadioButton);
        salatyCheckBox = (RadioButton) findViewById(R.id.salatyRadioButton);
        dezertyCheckBox = (RadioButton) findViewById(R.id.DezertyRadioButton);
        napojeCheckBox = (RadioButton) findViewById(R.id.NapojeRadioButton);

        ingrediencieEditText= (EditText) findViewById(R.id.ingrediencieEditText);
        postupEditText= (EditText) findViewById(R.id.postupEditText);
        Intent intent= getIntent();
        if(intent.hasExtra("ID")) {
            inzertovat=false;
            id = (Long) intent.getSerializableExtra("ID");
            String where = Recepty.Recept._ID + " = " + id.toString();
            AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    try {

                        if ((cursor != null) && cursor.moveToFirst()) {

                            String nazov = cursor.getString(1);
                            nazovEditText.setText(nazov);
                            String kategoria = cursor.getString(2);

                            if ("Predjedlá".equals(kategoria)) {
                                predjedlaCheckBox.setChecked(true);

                            }
                            if ("Polievky".equals(kategoria)) {
                                polievkyCheckBox.setChecked(true);

                            }
                            if ("Mäsité jedlá".equals(kategoria)) {
                                masiteCheckBox.setChecked(true);

                            }
                            if ("Bezmäsité jedlá".equals(kategoria)) {
                                bezmasiteCheckBox.setChecked(true);

                            }
                            if ("Šaláty".equals(kategoria)) {
                                salatyCheckBox.setChecked(true);

                            }
                            if ("Dezerty".equals(kategoria)) {
                                dezertyCheckBox.setChecked(true);

                            }
                            if ("Nápoje".equals(kategoria)) {
                                napojeCheckBox.setChecked(true);

                            }


                            String ingrediencie = cursor.getString(3);
                            ingrediencieEditText.setText(ingrediencie);
                            String postup = cursor.getString(4);
                            postupEditText.setText(postup);


                        }
                    } finally {
                        if(cursor!=null)
                        cursor.close();

                    }
                }

            };
            Uri uri = ContentUris.withAppendedId(Recepty.Recept.CONTENT_URI, id);
            handler.startQuery(0, null, uri, null, where, null, null);
        }
        }

    @Override
    public void onBackPressed() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recepty.Recept.NAZOV, nazovEditText.getText().toString());
        if ( predjedlaCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA, "Predjedlá");

        }
        if ( polievkyCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA, "Polievky");

        }
        if (masiteCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA,"Mäsité jedlá");

        }
        if ( bezmasiteCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA, "Bezmäsité jedlá");

        }
        if (salatyCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA,"Šaláty");

        }
        if (dezertyCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA, "Dezerty");

        }
        if ( napojeCheckBox.isChecked()) {
            contentValues.put(Recepty.Recept.KATEGORIA, "Nápoje");

        }

        contentValues.put(Recepty.Recept.INGREDIENCIE,  ingrediencieEditText.getText().toString());
        contentValues.put(Recepty.Recept.POSTUP, postupEditText.getText().toString());
        if(inzertovat) {

            AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onInsertComplete(int token, Object cookie, Uri uri) {
                    super.onInsertComplete(token, cookie, uri);
                }
            };
            handler.startInsert(0, null, Recepty.Recept.CONTENT_URI, contentValues);
            super.onBackPressed();
        }else{
            AsyncQueryHandler handler=new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onUpdateComplete(int token, Object cookie, int result) {
                    super.onUpdateComplete(token, cookie, result);
                }
            };
            Uri uri = ContentUris.withAppendedId(Recepty.Recept.CONTENT_URI, id);
            handler.startUpdate(0,null,uri,contentValues,null,null);
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);

        }

    }
}
