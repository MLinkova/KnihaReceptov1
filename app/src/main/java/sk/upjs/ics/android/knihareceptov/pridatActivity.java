package sk.upjs.ics.android.knihareceptov;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.Locale;
/*
* pomocne zdrojove kody :
* http://ics.upjs.sk/~novotnyr/android/vysuvny-panel-navigation-drawer.html
* http://stackoverflow.com/questions/19442378/navigation-drawer-to-switch-activities-instead-of-fragments*/
public class pridatActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private EditText nazovEditText;
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

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

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



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.openDrawer,
                R.string.closeDrawer
        );

        drawerLayout.setDrawerListener(drawerToggle);

        ListView navigationListView = (ListView) findViewById(R.id.navigationListView);
        navigationListView.setOnItemClickListener(this);

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
                            int kategoria = Integer.parseInt(cursor.getString(2));

                                if (kategoria==Konstanty.KATEGORIA_PREDJEDLA) {
                                    predjedlaCheckBox.setChecked(true);

                                }
                                if (kategoria==Konstanty.KATEGORIA_POLIEVKY) {
                                    polievkyCheckBox.setChecked(true);

                                }
                                if (kategoria==Konstanty.KATEGORIA_MASITE_JEDLA) {
                                    masiteCheckBox.setChecked(true);

                                }
                                if (kategoria==Konstanty.KATEGORIA_BEZMASITE_JEDLA) {
                                    bezmasiteCheckBox.setChecked(true);

                                }
                                if (kategoria==Konstanty.KATEGORIA_SALATY) {
                                    salatyCheckBox.setChecked(true);

                                }
                                if (kategoria==Konstanty.KATEGORIA_DEZERTY) {
                                    dezertyCheckBox.setChecked(true);

                                }
                                if (kategoria==Konstanty.KATEGORIA_NAPOJE) {
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
            if (predjedlaCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA, Konstanty.KATEGORIA_PREDJEDLA);

            }
            if (polievkyCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA, Konstanty.KATEGORIA_POLIEVKY);

            }
            if (masiteCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA, Konstanty.KATEGORIA_MASITE_JEDLA);

            }
            if (bezmasiteCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA, Konstanty.KATEGORIA_BEZMASITE_JEDLA);

            }
            if (salatyCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA, Konstanty.KATEGORIA_SALATY);

            }
            if (dezertyCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA, Konstanty.KATEGORIA_DEZERTY);

            }
            if (napojeCheckBox.isChecked()) {
                contentValues.put(Recepty.Recept.KATEGORIA,Konstanty.KATEGORIA_NAPOJE);

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
            handler.startUpdate(0, null, uri, contentValues, null, null);
            Intent intent= new Intent(this,UvodActivity.class);
            startActivity(intent);

        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationBar navigationBar = new navigationBar();
        navigationBar.selectItem(position,this);
        drawerLayout.closeDrawers();
    }

}
