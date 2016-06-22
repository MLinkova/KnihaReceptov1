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



    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
