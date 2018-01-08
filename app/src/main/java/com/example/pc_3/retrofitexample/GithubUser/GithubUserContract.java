package com.example.pc_3.retrofitexample.GithubUser;

import com.example.pc_3.retrofitexample.User;

/**
 * Created by PC-3 on 09/10/2017.
 */

interface GithubUserContract {
    interface View {
        void showMessage(String message);

        void showProgress();

        void dismissProgress();

        void showGithubUser(User githubUser);

        void showErrorMessage(int code);

        void showErrorMessage();
    }

    interface Presenter {
        void fetchUserData(String user);

        void userDataFound(User githubUser);

        void userDataFailure();

        void errorMessage(int code);
    }

    interface Model {
        void setPresenter(GithubUserContract.Presenter presenter);

        void fetchUserData(String user);
    }
}
