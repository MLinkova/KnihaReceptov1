package sk.upjs.ics.android.knihareceptov;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Michaela on 27.06.2016.
 */
public enum KategoriaDao {
    INSTANCE;
    private List<Kategoria> kategorie = new ArrayList<>();

    private KategoriaDao(){
        Kategoria predjedla ;
        Kategoria polievky;
        Kategoria masite;
        Kategoria bezmesite;
        Kategoria salat ;
        Kategoria dezert ;
        Kategoria napoj;
        if(Locale.getDefault().getDisplayLanguage().equals("slovenčina")) {
             predjedla = new Kategoria(Konstanty.KATEGORIA_PREDJEDLA, "Predjedlá");
             polievky = new Kategoria(Konstanty.KATEGORIA_POLIEVKY, "Polievky");
             masite = new Kategoria(Konstanty.KATEGORIA_MASITE_JEDLA, "Mäsité jedlá");
             bezmesite = new Kategoria(Konstanty.KATEGORIA_BEZMASITE_JEDLA, "Bezmäsité jedlá");
             salat = new Kategoria(Konstanty.KATEGORIA_SALATY, "Šaláty");
             dezert = new Kategoria(Konstanty.KATEGORIA_DEZERTY, "Dezerty");
             napoj = new Kategoria(Konstanty.KATEGORIA_NAPOJE, "Nápoje");

        }else{
             predjedla = new Kategoria(Konstanty.KATEGORIA_PREDJEDLA, "Appetizers");
             polievky = new Kategoria(Konstanty.KATEGORIA_POLIEVKY, "Soups");
             masite = new Kategoria(Konstanty.KATEGORIA_MASITE_JEDLA, "Meat and Poultry");
             bezmesite = new Kategoria(Konstanty.KATEGORIA_BEZMASITE_JEDLA, "Meals without meat");
             salat = new Kategoria(Konstanty.KATEGORIA_SALATY, "Salads");
             dezert = new Kategoria(Konstanty.KATEGORIA_DEZERTY, "Desserts");
             napoj = new Kategoria(Konstanty.KATEGORIA_NAPOJE, "Drinks");

        }
        kategorie.add(predjedla);
        kategorie.add(polievky);
        kategorie.add(masite);
        kategorie.add(bezmesite);
        kategorie.add(salat);
        kategorie.add(dezert);
        kategorie.add(napoj);
    }


    public List<Kategoria> list() {
        return new ArrayList<>(this.kategorie);
    }

    public Kategoria getKategoria(int cislo) {
        for (Kategoria kategoria : this.kategorie) {
            if (kategoria.getCisloKategorie() == cislo) {
                return kategoria;
            }
        }
        return null;
    }
}
