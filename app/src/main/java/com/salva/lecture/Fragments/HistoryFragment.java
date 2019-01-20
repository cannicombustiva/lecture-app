package com.salva.lecture.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.salva.lecture.App.App;
import com.salva.lecture.R;
import com.salva.lecture.api.RestClient;
import com.salva.lecture.api.models.Reservation;
import com.salva.lecture.api.models.ReservationEndPointResponse;
import com.salva.lecture.helpers.HistoryAdapter;
import com.salva.lecture.helpers.ReservationAdapter;
import com.salva.lecture.helpers.SharedData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    private RestClient restClient = App.getRestClient();
    private SharedData sharedData = App.getSharedData();

    public HistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        load(view);
        return view;
    }

    public void load(final View view) {
        String token = sharedData.getAccessToken();
        int userId = sharedData.getUserId();
        Call<ReservationEndPointResponse> res = restClient.getReservationService().getHistory(token, userId);
        res.enqueue(new Callback<ReservationEndPointResponse>() {
            @Override
            public void onResponse(Call<ReservationEndPointResponse> call, Response<ReservationEndPointResponse> response) {
                if(response.errorBody() != null) {
                    return;
                }
                List<Reservation> reservations = response.body().getList();

                RecyclerView recyclerView = view.findViewById(R.id.cardListHistory);
                recyclerView.setHasFixedSize(true);
                HistoryAdapter reservationAdapter = new HistoryAdapter(reservations);
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

}
