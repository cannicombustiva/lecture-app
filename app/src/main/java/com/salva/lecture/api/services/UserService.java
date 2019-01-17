package com.salva.lecture.api.services;

import com.salva.lecture.api.models.User;
import com.salva.lecture.api.models.UserAuth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    @Headers("AppLecture: true")
    @FormUrlEncoded
    @POST("/LecturesRes/Login")
    Call<UserAuth> makeLogin(@Field("email") String email, @Field("password") String password);

    @Headers("AppLecture: true")
    @POST("/LecturesRes/Api/Me")
    Call<User> me(@Header("authorization") String token);

}