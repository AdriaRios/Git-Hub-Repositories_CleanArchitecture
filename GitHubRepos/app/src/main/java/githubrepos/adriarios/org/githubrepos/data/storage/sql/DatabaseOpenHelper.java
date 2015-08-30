package githubrepos.adriarios.org.githubrepos.data.storage.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adrian on 21/03/2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    // Variable holding the name of the database to be created
    private static final String DATABASE_NAME = "repositories";
    private static final int DATABASE_VERSION = 1;

    // Variables holding the name of the table and columns to be created
    public static final String REPOSITORY_TABLE_NAME = "REPOSITORY";
    public static final String _ID = "_id";
    public static final String REPOSITORY_INFO = "info";
    public static final String PAGE = "page";


    // Static variable containing the name of all the table's columns
    static final String[] COLUMNS = {
            _ID,
            REPOSITORY_INFO,
            PAGE
    };

    // Variable holding the SQL instruction to create a new table
    private static final String CREATE_MEMORY_TABLE =
            "CREATE TABLE " + REPOSITORY_TABLE_NAME + "( " +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    REPOSITORY_INFO + " TEXT, " +
                    PAGE + " INTEGER)";

    // Public constructor. Delegates the construction to the SQLiteOpenHelper class
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create and initialize the database if not present
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_MEMORY_TABLE);
    }

    // Update the database if any changes have occurred (changes in the version number)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
