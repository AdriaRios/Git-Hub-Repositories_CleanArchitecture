package githubrepos.adriarios.org.githubrepos.domain.entities.vo;

/**
 * Created by Adrian on 13/08/2015.
 */
public class OwnerInfoVO {
    String login;
    String html_url;
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
