package sk.upjs.ics.android.knihareceptov;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class zobrazVysledkyActivity extends AppCompatActivity {
    public static ListView listView = null;
    public static List<String> nazvy = null;
    public static List<Long> indexy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zobraz_vysledky);
        listView= (ListView) findViewById(R.id.receptListView);
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
                            ArrayAdapter<String> adapter= new ArrayAdapter<String>(zobrazVysledkyActivity.this,android.R.layout.simple_list_item_1,nazvy);
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
                            ArrayAdapter<String> adapter= new ArrayAdapter<String>(zobrazVysledkyActivity.this,android.R.layout.simple_list_item_1,nazvy);
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
                Intent intent = new Intent(zobrazVysledkyActivity.this, zobrazActivity.class);
                Long index = indexy.get(position);
                intent.putExtra("ID", index);
                startActivity(intent);
            }
        });


    }





    @Override
    public void onBackPressed() {
        Intent intent= new Intent(zobrazVysledkyActivity.this,MainActivity.class);
        startActivity(intent);


    }
}
