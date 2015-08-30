package githubrepos.adriarios.org.githubrepos.presentation.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import githubrepos.adriarios.org.githubrepos.R;
import githubrepos.adriarios.org.githubrepos.domain.entities.vo.RepositoryInfoVO;

/**
 * Created by Adrian on 13/08/2015.
 */
public class GitHubRepoAdapter extends BaseAdapter {
    // Application context
    Context context;

    // List of users that will be rendered in the ListView
    List<RepositoryInfoVO> repositoriesList;

    // Set the context and user list from the constructor
    public GitHubRepoAdapter(Context context, List<RepositoryInfoVO> repositoriesList) {
        this.context = context;
        this.repositoriesList = repositoriesList;
    }

    @Override
    public int getCount() {
        return repositoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return repositoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The View corresponding to the ListView's row layout
        View currentView = convertView;
        // If the convertView element is not null, the ListView is asking to
        // recycle the existing View, so there is no need to create the View;
        // just update its content
        if (currentView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = inflater.inflate(R.layout.repository_item, parent, false);
        }

        TextView repoNameValue = (TextView) currentView.findViewById(R.id.repoNameValueTextView);
        TextView loginOwnerValue = (TextView) currentView.findViewById(R.id.LoginOwnerValueTextView);
        TextView descriptionValue = (TextView) currentView.findViewById(R.id.DescriptionValueTextView);

        RepositoryInfoVO gitHubRepo = repositoriesList.get(position);
        repoNameValue.setText(gitHubRepo.getName());
        loginOwnerValue.setText(gitHubRepo.getOwner().getLogin());
        descriptionValue.setText(gitHubRepo.getDescription());

        if (gitHubRepo.getFork()){
            currentView.setBackgroundColor(Color.rgb(255, 255, 255));
        }else{
            currentView.setBackgroundColor(Color.rgb(12, 249, 20));
        }
        return currentView;
    }
}
