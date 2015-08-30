package githubrepos.adriarios.org.githubrepos.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import javax.inject.Inject;

import githubrepos.adriarios.org.githubrepos.data.net.RestAPI;
import githubrepos.adriarios.org.githubrepos.data.storage.contentprovider.RepositoriesContentProvider;
import githubrepos.adriarios.org.githubrepos.dispatcher.Dispatcher;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.DataServiceEvent;
import githubrepos.adriarios.org.githubrepos.presentation.di.App;

/**
 * Created by Adrian on 13/08/2015.
 */

public class ReposDataStorage {
    @Inject
    Dispatcher dispatcher;
    RestAPI restAPI;
    ContentResolver contentResolver;
    public ReposDataStorage(App application){
        contentResolver = application.getContentResolver();
        restAPI = new RestAPI(application);
        application.getObjectGraph().inject(this);
    }

    public void getNewRepos(int pageNum, int pagePerCall) {
        //Check if current page exists on local storage
        Cursor queryResult = getCurrentRepositoryPageFromLocalStorage(pageNum);
        //If the are a result dispatch the info to use case
        if (queryResult.moveToFirst()) {
            do {
                String repositoryInfo = queryResult.getString(
                        queryResult.getColumnIndex(RepositoriesContentProvider.REPOSITORY_INFO)
                );
                dispatcher.dispatch(new DataServiceEvent(repositoryInfo, true));
            } while (queryResult.moveToNext());
            //If there aren't result get the info from server
        }else{
            restAPI.getNewRepos(pageNum, pagePerCall);
        }
    }

    private Cursor getCurrentRepositoryPageFromLocalStorage(int pageNum) {
        Cursor cursor = this.contentResolver.query(
                RepositoriesContentProvider.CONTENT_URI,
                new String[]{RepositoriesContentProvider.REPOSITORY_ID,
                        RepositoriesContentProvider.REPOSITORY_INFO,
                        RepositoriesContentProvider.PAGE
                },
                String.valueOf(pageNum),
                null,
                null);
        return cursor;
    }

    public void addNewRepositoryToLocalStorage(String newRepositories, int pageNum) {
        ContentValues values = new ContentValues();
        values.put(RepositoriesContentProvider.PAGE, pageNum);
        values.put(RepositoriesContentProvider.REPOSITORY_INFO, newRepositories);

        // Save the data through the ContentProvider
        contentResolver.insert(RepositoriesContentProvider.CONTENT_URI, values);

    }
}
