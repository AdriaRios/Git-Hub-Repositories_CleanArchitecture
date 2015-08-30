package githubrepos.adriarios.org.githubrepos.dispatcher.events;

import java.util.List;

import githubrepos.adriarios.org.githubrepos.domain.entities.vo.RepositoryInfoVO;

/**
 * Created by Adrian on 13/08/2015.
 */
public class ReposUpdatedEvent {
    public List<RepositoryInfoVO> gitHubReposUpdated;
    public ReposUpdatedEvent(List<RepositoryInfoVO> gitHubReposUpdated){
        this.gitHubReposUpdated = gitHubReposUpdated;
    }
}
