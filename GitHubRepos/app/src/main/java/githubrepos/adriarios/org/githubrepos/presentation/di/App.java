package githubrepos.adriarios.org.githubrepos.presentation.di;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by Adrian on 13/08/2015.
 */
public class App extends Application {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new AppDIModule(this));
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
