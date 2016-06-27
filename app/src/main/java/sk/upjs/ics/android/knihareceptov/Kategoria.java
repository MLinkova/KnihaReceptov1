package sk.upjs.ics.android.knihareceptov;

import java.io.Serializable;

/**
 * Created by Michaela on 27.06.2016.
 */
public class Kategoria implements Serializable {
 private int cisloKategorie;
 private String nazov;

 public Kategoria(){

 }
 public Kategoria(int cislo,String nazov){
     this.cisloKategorie=cislo;
     this.nazov=nazov;
 }

    public int getCisloKategorie() {
        return cisloKategorie;
    }

    public String getNazov() {
        return nazov;
    }

    @Override
    public String toString() {
        return this.nazov;
    }
}
