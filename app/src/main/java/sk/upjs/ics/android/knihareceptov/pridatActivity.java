package sk.upjs.ics.android.knihareceptov;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class pridatActivity extends AppCompatActivity {
    private EditText nazovEditText;
    private EditText kategoriaEditText;
    private EditText ingrediencieEditText;
    private EditText postupEditText;
    private Long id;
    private boolean inzertovat=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridat);
        nazovEditText = (EditText) findViewById(R.id.NazovEditText);
        kategoriaEditText= (EditText) findViewById(R.id.kategoriEditText);
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
                            //while(!cursor.isAfterLast()){
                            //  if(cursor.getString(0).equals(id.toString())){
                            String nazov = cursor.getString(1);
                            nazovEditText.setText(nazov);
                            String kategoria = cursor.getString(2);
                            kategoriaEditText.setText(kategoria);
                            String ingrediencie = cursor.getString(3);
                            ingrediencieEditText.setText(ingrediencie);
                            String postup = cursor.getString(4);
                            postupEditText.setText(postup);
                            //   }
                            //    cursor.moveToNext();
                            //}


                        }
                    } finally {
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
        contentValues.put(Recepty.Recept.KATEGORIA, kategoriaEditText.getText().toString());
        contentValues.put(Recepty.Recept.INGREDIENCIE, ingrediencieEditText.getText().toString());
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
