package sk.upjs.ics.android.knihareceptov;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
/*
* pomocne zdrojove kody :
* http://ics.upjs.sk/~novotnyr/android/vysuvny-panel-navigation-drawer.html
* http://stackoverflow.com/questions/19442378/navigation-drawer-to-switch-activities-instead-of-fragments*/

public class zobrazVysledkyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static ListView listView = null;
    public static List<String> nazvy = null;
    public static List<Long> indexy = null;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zobraz_vysledky);
        listView= (ListView) findViewById(R.id.receptListView);


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

        Intent intent=getIntent();
        if(intent.hasExtra("Nazov")){
            String nazov= (String) intent.getSerializableExtra("Nazov");
            String where=Recepty.Recept.NAZOV + " like "+ "\'%"+ nazov+"%\'";
            AsyncQueryHandler handler= new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    try{nazvy=new ArrayList<>();
                        indexy= new ArrayList<>();
                        if((cursor!=null)&& cursor.moveToFirst()){
                            while(!cursor.isAfterLast()){
                                indexy.add(Long.parseLong(cursor.getString(0)));
                                nazvy.add(cursor.getString(1));
                                cursor.moveToNext();
                            }
                            ArrayAdapter<String> adapter= new ArrayAdapter<String>(zobrazVysledkyActivity.this,R.layout.custom_textview,nazvy);
                            listView.setAdapter(adapter);


                        }
                    }finally{
                        if(cursor!=null){
                            cursor.close();
                        }
                    }

                }
            };
            handler.startQuery(0, null, Recepty.Recept.CONTENT_URI, null, where, null, null);



        }
        if(intent.hasExtra("Kategoria")){
            String kategoria= (String) intent.getSerializableExtra("Kategoria");
            String where= Recepty.Recept.KATEGORIA + " like " + "\'"+ kategoria + "\'";
            AsyncQueryHandler handler= new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    try{nazvy=new ArrayList<>();
                        indexy= new ArrayList<>();
                        if((cursor!=null)&& cursor.moveToFirst()){
                            while(!cursor.isAfterLast()){
                                indexy.add(Long.parseLong(cursor.getString(0)));
                                nazvy.add(cursor.getString(1));
                                cursor.moveToNext();
                            }
                            ArrayAdapter<String> adapter= new ArrayAdapter<String>(zobrazVysledkyActivity.this,R.layout.custom_textview,nazvy);
                            listView.setAdapter(adapter);


                        }
                    }finally{
                        if(cursor!=null){
                            cursor.close();
                        }
                    }

                }
            };
            handler.startQuery(0, null, Recepty.Recept.CONTENT_URI, null, where, null, null);
        }
        if(intent.hasExtra("Ingrediencie")){
            String ingrediencie= (String) intent.getSerializableExtra("Ingrediencie");
            String[] poleIngrediencii=ingrediencie.split(",");
            StringBuilder sb=new StringBuilder();
            for (int i=0;i<poleIngrediencii.length;i++){
                sb.append(poleIngrediencii[i]+"%");
            }
            String where= Recepty.Recept.INGREDIENCIE + " like " + "\'%"+ sb.toString() +  "\'";
            AsyncQueryHandler handler= new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                    try{nazvy=new ArrayList<>();
                        indexy= new ArrayList<>();
                        if((cursor!=null)&& cursor.moveToFirst()){
                            while(!cursor.isAfterLast()){
                                indexy.add(Long.parseLong(cursor.getString(0)));
                                nazvy.add(cursor.getString(1));
                                cursor.moveToNext();
                            }
                            ArrayAdapter<String> adapter= new ArrayAdapter<String>(zobrazVysledkyActivity.this,R.layout.custom_textview,nazvy);
                            listView.setAdapter(adapter);


                        }
                    }finally{
                        if(cursor!=null){
                            cursor.close();
                        }
                    }

                }
            };
            handler.startQuery(0, null, Recepty.Recept.CONTENT_URI, null, where, null, null);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(zobrazVysledkyActivity.this, zobraz2Activity.class);
                Long index = indexy.get(position);
                intent.putExtra("ID", index);
                startActivity(intent);
            }
        });


    }





    @Override
    public void onBackPressed() {
        Intent intent= new Intent(zobrazVysledkyActivity.this,UvodActivity.class);
        startActivity(intent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
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
