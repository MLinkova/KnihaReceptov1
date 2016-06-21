package sk.upjs.ics.android.knihareceptov;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michaela on 19.06.2016.
 */
public class ReceptyDBHelper extends SQLiteOpenHelper {

    public ReceptyDBHelper(Context context) {
        super(context, Recepty.Recept.TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT,"
                + "%s TEXT"
                + ")";
        db.execSQL(String.format(sql,
                Recepty.Recept.TABLE_NAME,
                Recepty.Recept._ID,
                Recepty.Recept.NAZOV,
                Recepty.Recept.KATEGORIA,
                Recepty.Recept.INGREDIENCIE,
                Recepty.Recept.POSTUP));

        insertSampleEntry(db,
                "Paradajkova polievka",
                "Polievky",
                "4 ks paradajkový pretlak,4 litre vody,olej,hladká múka,trochu soli,5 PL kryštálového cukru,mleté čierne korenie, syr tvrdý",
                "Z múky a oleja si spravíme redšiu zásmažku a za stáleho miešania pridáme všetky pretlaky.Stále miešame a zalievame vodou.Dochutíme soľou, korením a cukrom a necháme prejsť varom. Polievku si na tanieri ozdobíme postrúhaným syrom."
        );

    }


    private void insertSampleEntry(SQLiteDatabase db, String nazov,String kategoria,String ingrediencie,String postup) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recepty.Recept.NAZOV, nazov);
        contentValues.put(Recepty.Recept.KATEGORIA, kategoria);
        contentValues.put(Recepty.Recept.INGREDIENCIE, ingrediencie);
        contentValues.put(Recepty.Recept.POSTUP, postup);

        db.insert(Recepty.Recept.TABLE_NAME,null, contentValues);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
