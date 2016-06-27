package sk.upjs.ics.android.knihareceptov;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import de.ecotastic.android.camerautil.lib.CameraIntentHelperActivity;
import de.util.BitmapHelper;

/*
* pomocne zdrojove kody:
* http://ics.upjs.sk/~novotnyr/android/8-stretnutie-cast-2-fotoaparat.html
* http://ics.upjs.sk/~novotnyr/android/8-stretnutie-cast-3-zdielanie-dat.html*/

public class fotoActivity extends  CameraIntentHelperActivity  {
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
      Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setDataAndType(this.photoUri, "image/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Photo");
        shareIntent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);

        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
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


}
