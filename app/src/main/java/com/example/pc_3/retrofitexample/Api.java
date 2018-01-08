package com.example.pc_3.retrofitexample;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by PC-3 on 09/10/2017.
 */

public interface Api {
    @GET("users/{name}")
    Observable<Response<User>> getUser(@Path("name") String name);
}