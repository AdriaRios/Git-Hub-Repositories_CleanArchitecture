package githubrepos.adriarios.org.githubrepos.domain.entities;

import java.util.ArrayList;
import java.util.List;

import githubrepos.adriarios.org.githubrepos.domain.entities.vo.RepositoryInfoVO;

/**
 * Created by Adrian on 13/08/2015.
 */
public class ReposInfoModel {
    public static final int PAGE_PER_CALL = 10;
    private int pageNum = 0;

    private List<RepositoryInfoVO> allRepositories = new ArrayList<RepositoryInfoVO>() ;

    public ReposInfoModel(){
    }

    public int getPageNum() {
        return pageNum;
    }

    public void incrementPageCalled() {
        this.pageNum++;
    }

    public List<RepositoryInfoVO> getAllRepositories() {
        return allRepositories;
    }

    public void addNewRepositories(List<RepositoryInfoVO> newRepositories) {
        this.allRepositories.addAll(newRepositories);;
    }

    public String getUserProfileURL(int position) {
        return allRepositories.get(position).getOwner().getHtml_url();
    }

    public String getRepositoryURL(int position) {
        return allRepositories.get(position).getHtml_url();
    }
}
