package githubrepos.adriarios.org.githubrepos.presentation.activities;

import java.util.List;

import githubrepos.adriarios.org.githubrepos.domain.entities.vo.RepositoryInfoVO;

/**
 * Created by Adrian on 13/08/2015.
 */
public interface IGitReposView {
    void renderReposList(List<RepositoryInfoVO> reposList);
    void setProgressBarVisibility(Boolean visibility);
    void goToURL(String url);
    void showNetworkError();
}
