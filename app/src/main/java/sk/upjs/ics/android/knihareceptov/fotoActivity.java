package sk.upjs.ics.android.knihareceptov;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import de.ecotastic.android.camerautil.lib.CameraIntentHelperActivity;
import de.util.BitmapHelper;

/*
* pouzite zdrojove kody: http://ics.upjs.sk/~novotnyr/android/8-stretnutie-cast-2-fotoaparat.html*/

public class fotoActivity extends  CameraIntentHelperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
    }
    public void onShootButtonClick(View v) {
        startCameraIntent();
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
