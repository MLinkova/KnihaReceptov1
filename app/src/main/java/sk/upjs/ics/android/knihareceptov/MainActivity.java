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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
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
                        Toast.makeText(MainActivity.this, R.string.delete_recipe, Toast.LENGTH_LONG).show();

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
                Intent intent= new Intent(MainActivity.this,zobraz2Activity.class);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            final RadioGroup radioGroup= new RadioGroup(this);
            final RadioButton nazovRadioButton = new RadioButton(this);
            nazovRadioButton.setText(R.string.nazov);
            final RadioButton kategoriaRadioButton = new RadioButton(this);
            kategoriaRadioButton.setText(R.string.kategoria);
            final RadioButton ingrediencieRadioButton = new RadioButton(this);
            ingrediencieRadioButton.setText(R.string.ingrediencie);
            final LinearLayout layout= new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            radioGroup.addView(nazovRadioButton);
            radioGroup.addView(kategoriaRadioButton);
            radioGroup.addView(ingrediencieRadioButton);
            layout.addView(radioGroup);



            DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (nazovRadioButton.isChecked()) {
                        Intent intent= new Intent(MainActivity.this, hladatActivity.class);
                        intent.putExtra("Nazov","nazov");
                        startActivity(intent);
                    }
                    if (kategoriaRadioButton.isChecked()) {
                        Intent intent= new Intent(MainActivity.this, hladatKategoriaActivity.class);
                        startActivity(intent);
                    }
                   if(ingrediencieRadioButton.isChecked()){
                       Intent intent= new Intent(MainActivity.this, hladatActivity.class);
                       intent.putExtra("Ingrediencie","ingrediencie");
                       startActivity(intent);
                   }
                }
            };


            new AlertDialog.Builder(this)
                    .setTitle(R.string.search_title).
                    setMessage(R.string.search_message)
                    .setPositiveButton(R.string.ok_button, listener)
                    .setView(layout)
                    .show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
