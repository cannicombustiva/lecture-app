package com.salva.lecture.api;

import com.salva.lecture.api.services.ReservationService;
import com.salva.lecture.api.services.UserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String BASE_URL = "http://192.168.1.11:8080";
    private UserService userService;
    private ReservationService reservationService;

    public RestClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        reservationService = retrofit.create(ReservationService.class);
    }

    public UserService getUserService() {
        return userService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }
}


