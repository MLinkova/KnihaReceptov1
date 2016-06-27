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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
/*
* pomocne zdrojove kody :
* http://ics.upjs.sk/~novotnyr/android/vysuvny-panel-navigation-drawer.html
* http://stackoverflow.com/questions/19442378/navigation-drawer-to-switch-activities-instead-of-fragments*/
public class hladatActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
 private EditText hladatEditText;
    private TextView hladatView;
    private Button okButton;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hladat_nazov);
        hladatEditText= (EditText) findViewById(R.id.hladatEditText);
        hladatView= (TextView) findViewById(R.id.hladatTextView);
        Intent intent=getIntent();
        if(intent.hasExtra("Nazov")) {
            hladatView.setText(R.string.search_nazov);
            okButton = (Button) findViewById(R.id.okButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(hladatActivity.this, zobrazVysledkyActivity.class);
                    String nazov = hladatEditText.getText().toString();
                    intent.putExtra("Nazov", nazov);

                    startActivity(intent);

                }
            });
        }
        if(intent.hasExtra("Ingrediencie")) {
            hladatView.setText(R.string.search_ingrediencie);
            okButton = (Button) findViewById(R.id.okButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(hladatActivity.this, zobrazVysledkyActivity.class);
                    String ingrediencie = hladatEditText.getText().toString();
                    intent.putExtra("Ingrediencie", ingrediencie);

                    startActivity(intent);

                }
            });
        }

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

    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();

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
