package sk.upjs.ics.android.knihareceptov;

import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
/*
* pomocne zdrojove kody :
* http://ics.upjs.sk/~novotnyr/android/vysuvny-panel-navigation-drawer.html
* http://stackoverflow.com/questions/19442378/navigation-drawer-to-switch-activities-instead-of-fragments*/
public class zobraz2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    private TextView nazovRTextView;
    private TextView kategorieRTextView;
    private TextView ingrediencieRTextView;
    private  TextView postupRTextView;
    private Long id;
    private KategoriaDao kategoriaDao=KategoriaDao.INSTANCE;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zobraz);

        nazovRTextView= (TextView) findViewById(R.id.nazovReceptuTextView);
        kategorieRTextView= (TextView) findViewById(R.id.kategoriaReceptuTextView);
        ingrediencieRTextView= (TextView) findViewById(R.id.ingrediencieReceptuTextView);
        postupRTextView= (TextView) findViewById(R.id.postupReceptuTextView);


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
        if(intent.hasExtra("ID")){
           id= (Long) intent.getSerializableExtra("ID");
            String where =Recepty.Recept._ID + " = "+ id.toString();
            AsyncQueryHandler handler= new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, final Cursor cursor) {
                    try {

                        if ((cursor != null) && cursor.moveToFirst()) {

                              String nazov = cursor.getString(1);
                              nazovRTextView.setText(nazov);
                              String kategoria = kategoriaDao.getKategoria(Integer.parseInt(cursor.getString(2))).toString() ;

                              if(kategoria!=null)
                              kategorieRTextView.setText(kategoria);

                              String ingrediencie = cursor.getString(3);
                              ingrediencieRTextView.setText(ingrediencie);
                              String postup = cursor.getString(4);
                              postupRTextView.setText(postup);
                        }
                    }finally {
                        if(cursor!=null)
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
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(item.getItemId() == R.id.menuEdit){
            Intent intent= new Intent(this, pridatActivity.class);
            intent.putExtra("ID",id);
            startActivity(intent);
            return true;

        }


        return super.onOptionsItemSelected(item);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationBar navigationBar = new navigationBar();
        navigationBar.selectItem(position,this);
        drawerLayout.closeDrawers();
    }

}
