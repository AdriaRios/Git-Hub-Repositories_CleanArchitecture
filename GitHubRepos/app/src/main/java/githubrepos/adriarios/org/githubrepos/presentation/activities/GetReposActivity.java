package githubrepos.adriarios.org.githubrepos.presentation.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import githubrepos.adriarios.org.githubrepos.R;
import githubrepos.adriarios.org.githubrepos.domain.entities.vo.RepositoryInfoVO;
import githubrepos.adriarios.org.githubrepos.presentation.adapters.GitHubRepoAdapter;
import githubrepos.adriarios.org.githubrepos.presentation.di.App;
import githubrepos.adriarios.org.githubrepos.presentation.presenters.GitHubRepoPresenter;

public class GetReposActivity extends Activity implements AbsListView.OnScrollListener, IGitReposView {
    @Inject
    GitHubRepoPresenter gitHubPresenter;


    ListView repositoriesListView;
    ProgressBar progressBar;
    GitHubRepoAdapter gitHubRepoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getObjectGraph().inject(this);
        setContentView(R.layout.activity_get_repos);
        this.initialize();
    }

    private void initialize() {
        this.initViews();
        this.initPresenter();
    }

    private void initPresenter() {
        gitHubPresenter.init(this);
    }

    private void initViews() {
        //ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //ListVire
        repositoriesListView = (ListView) findViewById(R.id.gitHubReposListView);
        repositoriesListView.setOnScrollListener(this);
        repositoriesListView.setLongClickable(true);
        repositoriesListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence options[] = new CharSequence[]{getString(R.string.dialog_option1), getString(R.string.dialog_option2)};
                AlertDialog.Builder builder = new AlertDialog.Builder(GetReposActivity.this);
                builder.setTitle(getString(R.string.dialog_content));
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int option) {
                        switch (option) {
                            case 0:
                                gitHubPresenter.onOpenUserProfile(position);
                                break;
                            case 1:
                                gitHubPresenter.onOpenRepository(position);
                                break;
                            default:
                        }
                    }
                });
                builder.show();

                return false;
            }
        });
    }

    @Override
    public void renderReposList(List<RepositoryInfoVO> reposList) {
        if (gitHubRepoAdapter != null) {
            gitHubRepoAdapter.notifyDataSetChanged();
        } else {
            gitHubRepoAdapter = new GitHubRepoAdapter(this, reposList);
            repositoriesListView.setAdapter(gitHubRepoAdapter);
        }
    }

    @Override
    public void setProgressBarVisibility(Boolean visibility) {
        if (visibility) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToURL(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void showNetworkError() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.network_error);
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        switch (view.getId()) {
            case R.id.gitHubReposListView:
                gitHubPresenter.updateReposList(firstVisibleItem, visibleItemCount, totalItemCount);

        }
    }
}
