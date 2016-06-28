package sk.upjs.ics.android.knihareceptov;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import de.ecotastic.android.camerautil.lib.CameraIntentHelperActivity;
import de.util.BitmapHelper;

/*
* pomocne zdrojove kody:
* http://ics.upjs.sk/~novotnyr/android/8-stretnutie-cast-2-fotoaparat.html
* http://ics.upjs.sk/~novotnyr/android/8-stretnutie-cast-3-zdielanie-dat.html
* https://www.instagram.com/developer/mobile-sharing/android-intents/*/

public class fotoActivity extends  CameraIntentHelperActivity {
private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        imageView= (ImageView) findViewById(R.id.fotoImageView);
        imageView.setImageResource(android.R.drawable.ic_menu_camera);




    }
    public void onShootButtonClick(View v) {
        startCameraIntent();
    }

    public void onShareButtonClick(View v) {
      Intent zdielanieIntent = new Intent(Intent.ACTION_SEND);
        zdielanieIntent.setType("image/*");
        zdielanieIntent.putExtra(Intent.EXTRA_STREAM, this.photoUri);
        zdielanieIntent.putExtra(Intent.EXTRA_TEXT, "Photo");
        zdielanieIntent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);

        if (zdielanieIntent.resolveActivity(getPackageManager()) != null) {

            Intent intentPreVyber = null;
            if(Locale.getDefault().getDisplayLanguage().equals("slovenčina")) {
                intentPreVyber = Intent.createChooser(zdielanieIntent, Konstanty.SHARE_SK);
            }else{
                intentPreVyber = Intent.createChooser(zdielanieIntent, Konstanty.SHARE_EN);
            }
            if (intentPreVyber.resolveActivity(getPackageManager()) != null) {
                startActivity(intentPreVyber);
            }
        }



    }


    @Override
    protected void onPhotoUriFound() {
        super.onPhotoUriFound();
        Bitmap photo = BitmapHelper.readBitmap(this, this.photoUri);
        if (photo != null) {
            photo = BitmapHelper.shrinkBitmap(photo, 300, this.rotateXDegrees);
            ImageView imageView = (ImageView) findViewById(R.id.fotoImageView);
            imageView.setImageBitmap(photo);
        }


        if (this.preDefinedCameraUri != null && !this.preDefinedCameraUri.equals(this.photoUri)) {
            BitmapHelper.deleteImageWithUriIfExists(this.preDefinedCameraUri, this);
        }

        if (this.photoUriIn3rdLocation != null) {
            BitmapHelper.deleteImageWithUriIfExists(this.photoUriIn3rdLocation, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.foto_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.homeMenu:
                intent = new Intent(this,UvodActivity.class);
                startActivity(intent);
                return true;
            case R.id.addMenu:
                intent= new Intent(this,pridatActivity.class);
                startActivity(intent);
                return true;
            case  R.id.searchMenu:
                hladat();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }

    }

    private void hladat() {
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
                    Intent intent= new Intent(fotoActivity.this, hladatActivity.class);
                    intent.putExtra("Nazov","nazov");
                    startActivity(intent);
                }
                if (kategoriaRadioButton.isChecked()) {
                    Intent intent= new Intent(fotoActivity.this, hladatKategoriaActivity.class);
                    startActivity(intent);
                }
                if(ingrediencieRadioButton.isChecked()){
                    Intent intent= new Intent(fotoActivity.this, hladatActivity.class);
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



    }
}
