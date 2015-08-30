package githubrepos.adriarios.org.githubrepos.dispatcher.events;

/**
 * Created by Adrian on 13/08/2015.
 */
public class DataServiceEvent {
    public String repoList;
    public Boolean fromLocalStorage = false;
    public DataServiceEvent(String response, Boolean fromLocalStorage){
        repoList = response;
        this.fromLocalStorage = fromLocalStorage;
    }
}
