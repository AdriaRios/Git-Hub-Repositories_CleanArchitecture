package githubrepos.adriarios.org.githubrepos.presentation.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import githubrepos.adriarios.org.githubrepos.data.ReposDataStorage;
import githubrepos.adriarios.org.githubrepos.data.net.RestAPI;
import githubrepos.adriarios.org.githubrepos.dispatcher.Dispatcher;
import githubrepos.adriarios.org.githubrepos.domain.entities.ReposInfoModel;
import githubrepos.adriarios.org.githubrepos.domain.useCases.GetReposInfoUseCase;
import githubrepos.adriarios.org.githubrepos.presentation.activities.GetReposActivity;
import githubrepos.adriarios.org.githubrepos.presentation.presenters.GitHubRepoPresenter;

/**
 * Created by Adrian on 13/08/2015.
 */
@Module(injects = {
        GetReposActivity.class,
        GitHubRepoPresenter.class,
        GetReposInfoUseCase.class,
        ReposDataStorage.class,
        RestAPI.class,
})
public class AppDIModule {
    private App application;

    public AppDIModule(App application) {
        this.application = application;
    }

    @Provides
    public GitHubRepoPresenter providesPresenter() {
        return new GitHubRepoPresenter(this.application);
    }

    @Provides
    public GetReposInfoUseCase providesReposUseCase() {
        return new GetReposInfoUseCase(this.application);
    }

    @Provides
    @Singleton
    public ReposInfoModel providesReposInfoModel() {
        return new ReposInfoModel();
    }

    @Provides
    @Singleton
    public ReposDataStorage providesDataStorage() {
        return new ReposDataStorage(this.application);
    }

    @Provides
    @Singleton
    public Dispatcher providesDispatcher() {
        return new Dispatcher();
    }


}

