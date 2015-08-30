package githubrepos.adriarios.org.githubrepos.presentation.presenters;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import githubrepos.adriarios.org.githubrepos.dispatcher.Dispatcher;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.NetworkErrorEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.NoMoreRepositoriesEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.OpenUrlEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.ReposUpdatedEvent;
import githubrepos.adriarios.org.githubrepos.domain.useCases.GetReposInfoUseCase;
import githubrepos.adriarios.org.githubrepos.presentation.activities.IGitReposView;
import githubrepos.adriarios.org.githubrepos.presentation.di.App;

/**
 * Created by Adrian on 13/08/2015.
 */
public class GitHubRepoPresenter {
    @Inject
    GetReposInfoUseCase reposInfoUseCase;
    @Inject
    Dispatcher dispatcher;

    private final int ITEMS_PENDING_TO_GET_MORE_REPOSITORIES = 2;
    private final int NUMBERS_OF_ITEMS_PER_CALL = 10;
    private Boolean pendingForResponse = false;
    private Boolean moreRepositoriesPending = true;

    private IGitReposView gitReposView;

    public GitHubRepoPresenter(App application){
        application.getObjectGraph().inject(this);
        dispatcher.subscribe(this);
    }

    public void init(IGitReposView gitReposView) {
        this.gitReposView = gitReposView;
        this.gitReposView.setProgressBarVisibility(true);
        reposInfoUseCase.getNewRepos();
    }

    public void updateReposList(int firstVisibleItem, int visibleItemCount, int totalItemCount){
        final int lastItem = firstVisibleItem + visibleItemCount;
        if (this.gitReposView != null) {
            if (totalItemCount - lastItem <= ITEMS_PENDING_TO_GET_MORE_REPOSITORIES
                    && totalItemCount >= NUMBERS_OF_ITEMS_PER_CALL
                    && !pendingForResponse
                    && moreRepositoriesPending) {
                this.gitReposView.setProgressBarVisibility(true);
                pendingForResponse = true;
                reposInfoUseCase.getNewRepos();
            }
        }

    }

    public void onOpenUserProfile(int position) {
        this.reposInfoUseCase.getUserProfileURL(position);
    }

    public void onOpenRepository(int position) {
        this.reposInfoUseCase.getRepositoryURL(position);
    }

    @Subscribe
    public void onNewReposReceived(ReposUpdatedEvent event) {
        this.gitReposView.renderReposList(event.gitHubReposUpdated);
        this.gitReposView.setProgressBarVisibility(false);
        pendingForResponse = false;
    }

    @Subscribe
    public void onNoMoreRepositoriesEvent(NoMoreRepositoriesEvent event) {
        moreRepositoriesPending = false;
        this.gitReposView.setProgressBarVisibility(false);
    }

    @Subscribe
    public void onOpenURlEvent(OpenUrlEvent event) {
        gitReposView.goToURL(event.url);
    }

    @Subscribe
    public void onNetworkErrorEvent(NetworkErrorEvent event) {
        this.gitReposView.setProgressBarVisibility(false);
        gitReposView.showNetworkError();
    }
}
