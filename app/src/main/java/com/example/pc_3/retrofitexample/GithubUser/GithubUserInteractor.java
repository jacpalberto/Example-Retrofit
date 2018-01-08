package com.example.pc_3.retrofitexample.GithubUser;

import com.example.pc_3.retrofitexample.BaseClient;
import com.example.pc_3.retrofitexample.User;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    public void fetchUserData(final String user) {
        Observable<Response<User>> observable = BaseClient.provideApiService().getUser(user);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        if (userResponse.isSuccessful())
                            presenter.userDataFound(userResponse.body());
                        else presenter.errorMessage(userResponse.code());
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.userDataFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
