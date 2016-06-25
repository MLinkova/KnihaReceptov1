package sk.upjs.ics.android.knihareceptov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class hladatActivity extends AppCompatActivity {
 private EditText hladatEditText;
    private TextView hladatView;
    private Button okButton;
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

    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();




    }
}
