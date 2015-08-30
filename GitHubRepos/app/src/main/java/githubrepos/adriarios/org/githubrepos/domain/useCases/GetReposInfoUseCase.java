package githubrepos.adriarios.org.githubrepos.domain.useCases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import githubrepos.adriarios.org.githubrepos.data.ReposDataStorage;
import githubrepos.adriarios.org.githubrepos.dispatcher.Dispatcher;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.DataServiceEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.NoMoreRepositoriesEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.OpenUrlEvent;
import githubrepos.adriarios.org.githubrepos.dispatcher.events.ReposUpdatedEvent;
import githubrepos.adriarios.org.githubrepos.domain.entities.ReposInfoModel;
import githubrepos.adriarios.org.githubrepos.domain.entities.vo.RepositoryInfoVO;
import githubrepos.adriarios.org.githubrepos.presentation.di.App;

/**
 * Created by Adrian on 13/08/2015.
 */
public class GetReposInfoUseCase {
    @Inject
    ReposDataStorage reposDataStorage;
    @Inject
    Dispatcher dispatcher;
    @Inject
    ReposInfoModel reposInfoModel;

    public GetReposInfoUseCase(App application) {
        application.getObjectGraph().inject(this);
        dispatcher.subscribe(this);
    }

    public void getNewRepos() {
        reposInfoModel.incrementPageCalled();
        reposDataStorage.getNewRepos(reposInfoModel.getPageNum(), ReposInfoModel.PAGE_PER_CALL);
    }

    public void getUserProfileURL(int position) {
        String url = reposInfoModel.getUserProfileURL(position);
        dispatcher.dispatch(new OpenUrlEvent(url));
    }

    public void getRepositoryURL(int position) {
        String url = reposInfoModel.getRepositoryURL(position);
        dispatcher.dispatch(new OpenUrlEvent(url));
    }

    @Subscribe
    public void onNewReposReceived(DataServiceEvent event) {
        if (!event.fromLocalStorage){
            reposDataStorage.addNewRepositoryToLocalStorage(event.repoList, reposInfoModel.getPageNum());
        }
        Gson gson = new Gson();
        ArrayList<RepositoryInfoVO> newRepositoiresInfo;
        newRepositoiresInfo = gson.fromJson(event.repoList,  new TypeToken<ArrayList<RepositoryInfoVO>>(){}.getType());
        //If size is 0 means that there aren't more repositories, we will notify the presenter to stop request for more repositories
        if (newRepositoiresInfo.size() == 0){
            dispatcher.dispatch(new NoMoreRepositoriesEvent());
        }else {
            reposInfoModel.addNewRepositories(newRepositoiresInfo);
            dispatcher.dispatch(new ReposUpdatedEvent(reposInfoModel.getAllRepositories()));
        }
    }

    @Subscribe
    public void onNetworkErrorEvent(DataServiceEvent event) {}


}
