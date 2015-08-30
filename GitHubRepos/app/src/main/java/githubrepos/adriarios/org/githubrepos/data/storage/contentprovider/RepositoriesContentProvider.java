package githubrepos.adriarios.org.githubrepos.data.storage.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import githubrepos.adriarios.org.githubrepos.data.storage.sql.DatabaseOpenHelper;
import githubrepos.adriarios.org.githubrepos.data.storage.sql.SQLiteDataRepository;

/**
 * Created by Adrian on 21/03/2015.
 */
public class RepositoriesContentProvider extends ContentProvider {
    /* ========COURSES_PROVIDER CONTRACT===============================================
	 * Defines
	 * 		. CONTENT_URI
	 * 		. COURSES_PROVIDER COLUMNS
	 */
    public static final Uri CONTENT_URI = Uri
            .parse("content://org.adriarios.githubrepos.data.contentprovider/repositories");

    public static final String REPOSITORY_ID = DatabaseOpenHelper._ID;
    public static final String REPOSITORY_INFO = DatabaseOpenHelper.REPOSITORY_INFO;
    public static final String PAGE = DatabaseOpenHelper.PAGE;

	/* ================================================================================= */

    private static final int REPOSITORIES_BY_PAGE = 1;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI("org.adriarios.githubrepos.data.contentprovider",
                "repositories", REPOSITORIES_BY_PAGE);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDataRepository repositoriesDB = new SQLiteDataRepository(getContext());
        repositoriesDB.openDatabaseForReadOnly();
        Cursor result = null;

        switch ( uriMatcher.match(uri) ) {
            case REPOSITORIES_BY_PAGE:
                result = repositoriesDB.fetchAllReposPerPage(projection, selection);
            default:
                break;
        }


        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if ( uriMatcher.match(uri) == REPOSITORIES_BY_PAGE ) {
            SQLiteDataRepository repositoriesDB = new SQLiteDataRepository(getContext());
            repositoriesDB.openDatabaseForWrite();

            long rowId = repositoriesDB.insert(values);
            if ( rowId > 0 ) {
                Uri resultUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
                return resultUri;
            }
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }

}
