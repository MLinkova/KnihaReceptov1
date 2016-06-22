package sk.upjs.ics.android.knihareceptov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class hladatNazovActivity extends AppCompatActivity {
 private EditText hladatEditText;
    private Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hladat_nazov);
        hladatEditText= (EditText) findViewById(R.id.hladatEditText);
        okButton= (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(hladatNazovActivity.this,zobrazVysledkyActivity.class);
                String nazov=hladatEditText.getText().toString();
                intent.putExtra("Nazov",nazov);

                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();




    }
}
