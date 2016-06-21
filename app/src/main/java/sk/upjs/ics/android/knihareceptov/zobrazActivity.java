package sk.upjs.ics.android.knihareceptov;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

public class zobrazActivity extends AppCompatActivity {
    private TextView nazovRTextView;
    private TextView kategorieRTextView;
    private TextView ingrediencieRTextView;
    private  TextView postupRTextView;
    private Long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zobraz);

        nazovRTextView= (TextView) findViewById(R.id.nazovReceptuTextView);
        kategorieRTextView= (TextView) findViewById(R.id.kategoriaReceptuTextView);
        ingrediencieRTextView= (TextView) findViewById(R.id.ingrediencieReceptuTextView);
        postupRTextView= (TextView) findViewById(R.id.postupReceptuTextView);


        Intent intent= getIntent();
        if(intent.hasExtra("ID")){
           id= (Long) intent.getSerializableExtra("ID");
            String where =Recepty.Recept._ID + " = "+ id.toString();
            AsyncQueryHandler handler= new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    try {

                        if ((cursor != null) && cursor.moveToFirst()) {
                            //while(!cursor.isAfterLast()){
                           //  if(cursor.getString(0).equals(id.toString())){
                              String nazov = cursor.getString(1);
                              nazovRTextView.setText(nazov);
                              String kategoria = cursor.getString(2);
                              kategorieRTextView.setText(kategoria);
                              String ingrediencie = cursor.getString(3);
                              ingrediencieRTextView.setText(ingrediencie);
                              String postup = cursor.getString(4);
                               postupRTextView.setText(postup);
                           //   }
                            //    cursor.moveToNext();
                            //}



                        }
                    }finally {
                        cursor.close();

                    }
                }

            };
            Uri uri = ContentUris.withAppendedId(Recepty.Recept.CONTENT_URI, id);
            handler.startQuery(0,null,uri,null,where,null,null);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zobraz_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuEdit){
            Intent intent= new Intent(this, pridatActivity.class);
            intent.putExtra("ID",id);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
