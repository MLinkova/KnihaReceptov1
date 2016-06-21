package sk.upjs.ics.android.knihareceptov;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Michaela on 19.06.2016.
 */
public interface Recepty {
    public interface Recept extends BaseColumns{
        public static final Uri CONTENT_URI=Uri.parse("content://sk.upjs.ics.android.knihaReceptov.ReceptyContentProvider/Recept");
        public static final String TABLE_NAME="Recepty";
        public static final String NAZOV="nazov";
        public static final String KATEGORIA="kategoria";
        public static final String INGREDIENCIE="ingrediencie";
        public static final String POSTUP="postup";

    }
}
