package sk.upjs.ics.android.knihareceptov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class hladatKategoriaActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> moznosti= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hladat_kategoria);
        listView= (ListView) findViewById(R.id.kategorieListView);
        moznosti.add("Predjedla");
        moznosti.add("Polievky");
        moznosti.add("Masite jedla");
        moznosti.add("Bezmasite jedla");
        moznosti.add("Salaty");
        moznosti.add("Dezerty");
        moznosti.add("Napoje");
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,moznosti);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(hladatKategoriaActivity.this,zobrazVysledkyActivity.class);
                String kategoria=moznosti.get(position);
                intent.putExtra("Kategoria",kategoria);
                startActivity(intent);
            }
        });
    }
}
