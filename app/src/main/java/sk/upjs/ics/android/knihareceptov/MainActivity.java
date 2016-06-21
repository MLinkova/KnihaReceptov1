package sk.upjs.ics.android.knihareceptov;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    private GridView receptGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receptGridView=(GridView) findViewById(R.id.receptGridView);

        receptGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AsyncQueryHandler handler = new AsyncQueryHandler(getContentResolver()) {
                    @Override
                    protected void onDeleteComplete(int token, Object cookie, int result) {

                    }
                };
                Uri uri = ContentUris.withAppendedId(Recepty.Recept.CONTENT_URI, id);
                handler.startDelete(0, null, uri, null, null);
                return true;
            }
        });
        receptGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(MainActivity.this,zobrazActivity.class);
                intent.putExtra("ID",id);


                startActivity(intent);

            }
        });

        String[] from = {Recepty.Recept.NAZOV };
        int[] to = {android.R.id.text1};
        adapter= new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_1, null, from, to,0);

        receptGridView.setAdapter(adapter);

        getLoaderManager().initLoader(0, Bundle.EMPTY, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(Recepty.Recept.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor kurzor) {
        adapter.swapCursor(kurzor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuPridat){
            Intent intent= new Intent(this, pridatActivity.class);
            startActivity(intent);
            return true;

        }
        if(item.getItemId() == R.id.menuHladat){

            final EditText rozhodniEditTtext = new EditText(this);

            DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   String vyber=rozhodniEditTtext.getText().toString();
                    if ("nazov".equals(vyber)) {
                        Intent intent= new Intent(MainActivity.this, hladatNazovActivity.class);
                        startActivity(intent);
                    }
                    if ("kategoria".equals(vyber)) {
                        Intent intent= new Intent(MainActivity.this, hladatKategoriaActivity.class);
                        startActivity(intent);
                    }
                    if(!("nazov".equals(vyber))&& !("kategoria".equals(vyber))){
                        Toast.makeText(MainActivity.this, "Napiste len slovo: nazov alebo kategoria", Toast.LENGTH_LONG).show();
                    }
                }
            };


            new AlertDialog.Builder(this)
                    .setTitle("Hľadať").
                    setMessage("Napiste či chcete hľadať podľa názvu alebo kategórie.")
                    .setPositiveButton("OK", listener)
                    .setView(rozhodniEditTtext)
                    .show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
