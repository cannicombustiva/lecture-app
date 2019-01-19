package com.salva.lecture.api.services;

import com.salva.lecture.api.models.CourseTeacherEndPointResponse;
import com.salva.lecture.api.models.DumbEndPointResponse;
import com.salva.lecture.api.models.Reservation;
import com.salva.lecture.api.models.ReservationEndPointResponse;
import com.salva.lecture.api.models.UserAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ReservationService {
    @Headers("AppLecture: true")
    @GET("/LecturesRes/Api/Reservations/Bookable")
    Call<CourseTeacherEndPointResponse> getBookables(
            @Header("authorization") String token,
            @Query("userId") int userId
    );

    @Headers("AppLecture: true")
    @GET("/LecturesRes/Api/Reservations/History")
    Call<ReservationEndPointResponse> getHistory(
            @Header("authorization") String token,
            @Query("userId") int userId
    );

    @Headers("AppLecture: true")
    @GET("/LecturesRes/Api/Reservations")
    Call<ReservationEndPointResponse> getReservations(
            @Header("authorization") String token,
            @Query("userId") int userId,
            @Query("date") String date
    );

    @Headers("AppLecture: true")
    @POST("/LecturesRes/Api/Reservations")
    Call<DumbEndPointResponse> book(
            @Header("authorization") String token,
            @Body Reservation reservation
    );
}
