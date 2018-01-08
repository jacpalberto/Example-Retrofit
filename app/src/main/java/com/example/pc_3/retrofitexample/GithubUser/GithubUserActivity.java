package com.example.pc_3.retrofitexample.GithubUser;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc_3.retrofitexample.R;
import com.example.pc_3.retrofitexample.User;
import com.squareup.picasso.Picasso;

public class GithubUserActivity extends AppCompatActivity implements GithubUserContract.View {
    private GithubUserContract.Presenter presenter;
    private EditText etUsername;
    private Button btnSearch;
    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvUrl;
    private TextView tvRepositories;
    private TextView tvFollowers;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkUI();
        init();
    }

    private void init() {
        presenter = new GithubUserPresenter(this, new GithubUserInteractor());
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestGithubUser();
                hideKeyboard();
            }
        });
        etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    requestGithubUser();
                }
                return false;
            }
        });
    }

    private void requestGithubUser() {
        String username = etUsername.getText().toString().trim();
        if (!username.isEmpty()) presenter.fetchUserData(username);
        else showMessage(getString(R.string.empty_username));
    }

    private void linkUI() {
        etUsername = (EditText) findViewById(R.id.et_username);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvUrl = (TextView) findViewById(R.id.tv_url);
        tvRepositories = (TextView) findViewById(R.id.tv_repos);
        tvFollowers = (TextView) findViewById(R.id.tv_followers);
        btnSearch = (Button) findViewById(R.id.btn_search);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progress_bar);
        ivProfile = (ImageView) findViewById(R.id.iv_profile);
    }

    @Override
    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showProgress() {
        progressBar.show();
    }

    @Override
    public void dismissProgress() {
        progressBar.hide();
    }

    @Override
    public void showGithubUser(User githubUser) {
        etUsername.setText("");
        tvName.setText(githubUser.getLogin());
        tvUrl.setText(githubUser.getHtmlUrl());
        tvFollowers.setText(getString(R.string.followers, githubUser.getFollowers().toString()));
        tvRepositories.setText(getString(R.string.repositories, githubUser.getPublicRepos().toString()));
        Picasso.with(this).load(githubUser.getAvatarUrl()).into(ivProfile);
    }

    @Override
    public void showErrorMessage(int code) {
        String message = getString(R.string.connection_error);
        if (code == 404) message = getString(R.string.user_not_found);
        showMessage(message);
    }

    @Override
    public void showErrorMessage() {
        showErrorMessage(R.string.connection_error);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}