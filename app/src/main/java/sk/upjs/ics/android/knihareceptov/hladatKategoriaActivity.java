package sk.upjs.ics.android.knihareceptov;

import android.content.Intent;
import android.content.res.Configuration;
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
import java.util.Locale;
/*
* pomocne zdrojove kody :
* http://ics.upjs.sk/~novotnyr/android/vysuvny-panel-navigation-drawer.html
* http://stackoverflow.com/questions/19442378/navigation-drawer-to-switch-activities-instead-of-fragments*/

public class hladatKategoriaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    private ListView listView;
    private List<String> moznosti= new ArrayList<>();
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hladat_kategoria);
        listView= (ListView) findViewById(R.id.kategorieListView);


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


       if( Locale.getDefault().getDisplayLanguage().equals("slovenčina")) {
            moznosti.add("Predjedlá");
            moznosti.add("Polievky");
            moznosti.add("Mäsité jedlá");
            moznosti.add("Bezmäsité jedlá");
            moznosti.add("Šaláty");
            moznosti.add("Dezerty");
            moznosti.add("Nápoje");
        }else{
           moznosti.add("Appetizers");
           moznosti.add("Soups");
           moznosti.add("Meat and Poultry");
           moznosti.add("Meals without meat");
           moznosti.add("Salads");
           moznosti.add("Desserts");
           moznosti.add("Drinks");

       }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.custom_textview,moznosti);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(hladatKategoriaActivity.this, zobrazVysledkyActivity.class);
                String kategoria = moznosti.get(position);
                intent.putExtra("Kategoria", kategoria);
                startActivity(intent);
            }
        });
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
