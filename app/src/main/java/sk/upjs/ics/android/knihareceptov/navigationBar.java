package sk.upjs.ics.android.knihareceptov;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/*
* pomocne zdrojove kody :
* http://ics.upjs.sk/~novotnyr/android/vysuvny-panel-navigation-drawer.html
* http://stackoverflow.com/questions/19442378/navigation-drawer-to-switch-activities-instead-of-fragments*/
public class navigationBar {



    public void selectItem(int position,Activity activita){
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(activita,UvodActivity.class);
                activita.startActivity(intent);
                break;
            case 1:
                intent= new Intent(activita,pridatActivity.class);
                activita.startActivity(intent);
                break;
            case 2:
                hladat(activita);
                break;
            case 3:
                intent= new Intent(activita,fotoActivity.class);
                activita.startActivity(intent);
                break;
            default:
                intent = new Intent(activita,UvodActivity.class);
                activita.startActivity(intent);
                break;


        }

    }
    private void hladat( final Activity activita){
        final RadioGroup radioGroup= new RadioGroup(activita);
        final RadioButton nazovRadioButton = new RadioButton(activita);
        nazovRadioButton.setText(R.string.nazov);
        final RadioButton kategoriaRadioButton = new RadioButton(activita);
        kategoriaRadioButton.setText(R.string.kategoria);
        final RadioButton ingrediencieRadioButton = new RadioButton(activita);
        ingrediencieRadioButton.setText(R.string.ingrediencie);
        final LinearLayout layout= new LinearLayout(activita);
        layout.setOrientation(LinearLayout.VERTICAL);
        radioGroup.addView(nazovRadioButton);
        radioGroup.addView(kategoriaRadioButton);
        radioGroup.addView(ingrediencieRadioButton);
        layout.addView(radioGroup);



        DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (nazovRadioButton.isChecked()) {
                    Intent intent= new Intent(activita, hladatActivity.class);
                    intent.putExtra("Nazov","nazov");
                    activita.startActivity(intent);
                }
                if (kategoriaRadioButton.isChecked()) {
                    Intent intent= new Intent(activita, hladatKategoriaActivity.class);
                    activita.startActivity(intent);
                }
                if(ingrediencieRadioButton.isChecked()){
                    Intent intent= new Intent(activita, hladatActivity.class);
                    intent.putExtra("Ingrediencie","ingrediencie");
                    activita.startActivity(intent);
                }
            }
        };


        new AlertDialog.Builder(activita)
                .setTitle(R.string.search_title).
                setMessage(R.string.search_message)
                .setPositiveButton(R.string.ok_button, listener)
                .setView(layout)
                .show();


    }
}
