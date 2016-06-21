package sk.upjs.ics.android.knihareceptov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class hladatNazovActivity extends AppCompatActivity {
 private EditText hladatEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hladat_nazov);
        hladatEditText= (EditText) findViewById(R.id.hladatEditText);
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(hladatNazovActivity.this,zobrazVysledkyActivity.class);
        String nazov=hladatEditText.getText().toString();
        intent.putExtra("Nazov",nazov);

        startActivity(intent);




    }
}
