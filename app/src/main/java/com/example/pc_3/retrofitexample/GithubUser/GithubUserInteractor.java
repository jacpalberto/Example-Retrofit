package com.example.pc_3.retrofitexample.GithubUser;

import com.example.pc_3.retrofitexample.BaseClient;
import com.example.pc_3.retrofitexample.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC-3 on 09/10/2017.
 */

class GithubUserInteractor implements GithubUserContract.Model {
    private GithubUserContract.Presenter presenter;

    @Override
    public void setPresenter(GithubUserContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void fetchUserData(String user) {
        Call<User> call = BaseClient.provideApiService().getUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) presenter.userDataFound(response.body());
                else presenter.errorMessage(response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.userDataFailure();
            }
        });
    }
}
