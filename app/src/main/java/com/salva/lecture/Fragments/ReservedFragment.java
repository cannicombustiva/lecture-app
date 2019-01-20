package com.salva.lecture.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.salva.lecture.App.App;
import com.salva.lecture.Interfaces.OnReservationClickListener;
import com.salva.lecture.R;
import com.salva.lecture.api.RestClient;
import com.salva.lecture.api.models.DumbEndPointResponse;
import com.salva.lecture.api.models.Reservation;
import com.salva.lecture.api.models.ReservationEndPointResponse;
import com.salva.lecture.helpers.BookableAdapter;
import com.salva.lecture.helpers.DateFormat;
import com.salva.lecture.helpers.ReservationAdapter;
import com.salva.lecture.helpers.SharedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservedFragment extends Fragment {

    private RestClient restClient = App.getRestClient();
    private SharedData sharedData = App.getSharedData();

    public ReservedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reserved, container, false);
        load(view);
        return view;
    }

    public void load(final View view) {
        String token = sharedData.getAccessToken();
        int userId = sharedData.getUserId();
        String date = DateFormat.getISODate();
        Call<ReservationEndPointResponse> res = restClient.getReservationService().getReservations(token, userId, date);
        res.enqueue(new Callback<ReservationEndPointResponse>() {
            @Override
            public void onResponse(Call<ReservationEndPointResponse> call, Response<ReservationEndPointResponse> response) {
                if (response.errorBody() != null) {
                    return;
                }
                List<Reservation> reservations = response.body().getList();

                RecyclerView recyclerView = view.findViewById(R.id.cardListReserved);
                recyclerView.setHasFixedSize(true);
                ReservationAdapter reservationAdapter = new ReservationAdapter(reservations, new OnReservationClickListener() {
                    @Override
                    public void onItemClick(Reservation item, View buttonView) {
                        Button button = (Button) buttonView;
                        deleteReservation(item, button, view);
                    }
                });
                recyclerView.setAdapter(reservationAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
            }

            @Override
            public void onFailure(Call<ReservationEndPointResponse> call, Throwable t) {
                Log.e("errore", t.toString());
            }
        });
    }

    private void deleteReservation(Reservation item, final Button buttonView, final View view) {
        String token = sharedData.getAccessToken();
        Call<DumbEndPointResponse> res = restClient.getReservationService().delete(
                token, new Reservation(item.id)
        );
        res.enqueue(new Callback<DumbEndPointResponse>() {
            @Override
            public void onResponse(Call<DumbEndPointResponse> call, Response<DumbEndPointResponse> response) {
                boolean success = response.body() != null && response.body().success;
                if(response.errorBody() != null || !success) {
                    Toast.makeText(getContext(), "Cancellazione non riuscita!", Toast.LENGTH_LONG).show();
                    return;
                }

                buttonView.setText("Eliminato");
                buttonView.setEnabled(false);
                buttonView.setBackgroundColor(Color.GRAY);
                Toast.makeText(getContext(), "Cancellazione avvenuta", Toast.LENGTH_LONG).show();
                load(view);
            }

            @Override
            public void onFailure(Call<DumbEndPointResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Cancellazione non riuscita!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
