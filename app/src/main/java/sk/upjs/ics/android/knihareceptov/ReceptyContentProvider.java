package sk.upjs.ics.android.knihareceptov;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ReceptyContentProvider extends ContentProvider {
private ReceptyDBHelper dbhelper;



    public ReceptyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long id = ContentUris.parseId(uri);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        int pocetRiakov = db.delete(Recepty.Recept.TABLE_NAME, Recepty.Recept._ID + " = " + id, null);
        getContext().getContentResolver().notifyChange(Recepty.Recept.CONTENT_URI,null);
        return pocetRiakov;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long id = db.insert(Recepty.Recept.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(Recepty.Recept.CONTENT_URI,null);
        return ContentUris.withAppendedId(Recepty.Recept.CONTENT_URI, id);
    }

    @Override
    public boolean onCreate() {
        dbhelper= new ReceptyDBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor =  db.query(Recepty.Recept.TABLE_NAME, null, selection, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), Recepty.Recept.CONTENT_URI);
                return cursor;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        long id = ContentUris.parseId(uri);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int pocetRiakov = db.update(Recepty.Recept.TABLE_NAME, values, Recepty.Recept._ID + " = " + id, null);
        getContext().getContentResolver().notifyChange(Recepty.Recept.CONTENT_URI, null);
        return pocetRiakov;
    }

}
