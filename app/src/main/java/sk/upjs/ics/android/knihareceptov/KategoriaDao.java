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
             predjedla = new Kategoria(1, "Predjedlá");
             polievky = new Kategoria(2, "Polievky");
             masite = new Kategoria(3, "Mäsité jedlá");
             bezmesite = new Kategoria(4, "Bezmäsité jedlá");
             salat = new Kategoria(5, "Šaláty");
             dezert = new Kategoria(6, "Dezerty");
             napoj = new Kategoria(7, "Nápoje");

        }else{
             predjedla = new Kategoria(1, "Appetizers");
             polievky = new Kategoria(2, "Soups");
             masite = new Kategoria(3, "Meat and Poultry");
             bezmesite = new Kategoria(4, "Meals without meat");
             salat = new Kategoria(5, "Salads");
             dezert = new Kategoria(6, "Desserts");
             napoj = new Kategoria(7, "Drinks");

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
