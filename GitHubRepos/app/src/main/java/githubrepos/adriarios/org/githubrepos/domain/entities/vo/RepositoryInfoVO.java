package githubrepos.adriarios.org.githubrepos.domain.entities.vo;

/**
 * Created by Adrian on 13/08/2015.
 */
public class RepositoryInfoVO {
    private String name;
    private String description;
    private String html_url;
    private Boolean fork;
    private OwnerInfoVO owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public Boolean getFork() {
        return fork;
    }

    public void setFork(Boolean fork) {
        this.fork = fork;
    }

    public OwnerInfoVO getOwner() {
        return owner;
    }

    public void setOwner(OwnerInfoVO owner) {
        this.owner = owner;
    }

}
