package com.salva.lecture.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salva.lecture.App.App;
import com.salva.lecture.R;
import com.salva.lecture.api.RestClient;
import com.salva.lecture.api.models.Reservation;
import com.salva.lecture.api.models.ReservationEndPointResponse;
import com.salva.lecture.helpers.DateFormat;
import com.salva.lecture.helpers.SharedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservedFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RestClient restClient = App.getRestClient();
    private SharedData sharedData = App.getSharedData();

    public ReservedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reserved, container, false);
        String token = sharedData.getAccessToken();
        int userId = sharedData.getUserId();
        String date = DateFormat.getISODate();
        Call<ReservationEndPointResponse> res = restClient.getReservationService().getReservations(token, userId, date);
        res.enqueue(new Callback<ReservationEndPointResponse>() {
            @Override
            public void onResponse(Call<ReservationEndPointResponse> call, Response<ReservationEndPointResponse> response) {
                if(response.errorBody() != null) {

                }
                List<Reservation> reservations = response.body().getList();
                for(Reservation reservation : reservations){
                    TextView text = new TextView(getContext());
                    text.append(reservation.courseName);
                    text.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT));
                    CardView card = new CardView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                    params.gravity = Gravity.CENTER;
                    params.leftMargin = 10;
                    params.rightMargin = 10;
                    params.bottomMargin = 50;
                    params.topMargin = 50;
                    card.setLayoutParams(params);
                    card.setRadius(4);
                    card.addView(text);
                    LinearLayout linearLayout = view.findViewById(R.id.scrollList);
                    linearLayout.addView(card);
                }
            }

            @Override
            public void onFailure(Call<ReservationEndPointResponse> call, Throwable t) {
                Log.e("errore", t.toString());
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
