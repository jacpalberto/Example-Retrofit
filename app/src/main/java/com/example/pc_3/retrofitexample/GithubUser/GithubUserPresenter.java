package com.example.pc_3.retrofitexample.GithubUser;

import com.example.pc_3.retrofitexample.User;

/**
 * Created by PC-3 on 09/10/2017.
 */

class GithubUserPresenter implements GithubUserContract.Presenter {
    private GithubUserContract.View view;
    private GithubUserContract.Model model;

    GithubUserPresenter(GithubUserContract.View view, GithubUserContract.Model model) {
        this.view = view;
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void fetchUserData(String user) {
        view.showProgress();
        model.fetchUserData(user);
    }

    @Override
    public void userDataFound(User githubUser) {
        view.dismissProgress();
        view.showGithubUser(githubUser);
    }

    @Override
    public void userDataFailure() {
        view.dismissProgress();
        view.showErrorMessage();
    }

    @Override
    public void errorMessage(int code) {
        view.dismissProgress();
        view.showErrorMessage(code);
    }
}