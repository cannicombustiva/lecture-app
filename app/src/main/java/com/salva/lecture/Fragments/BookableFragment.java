package com.salva.lecture.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.salva.lecture.App.App;
import com.salva.lecture.HomeActivity;
import com.salva.lecture.Interfaces.OnItemClickListener;
import com.salva.lecture.LoginActivity;
import com.salva.lecture.R;
import com.salva.lecture.api.RestClient;
import com.salva.lecture.api.models.CourseTeacher;
import com.salva.lecture.api.models.CourseTeacherEndPointResponse;
import com.salva.lecture.api.models.DumbEndPointResponse;
import com.salva.lecture.api.models.Reservation;
import com.salva.lecture.api.models.ReservationEndPointResponse;
import com.salva.lecture.api.models.User;
import com.salva.lecture.helpers.BookableAdapter;
import com.salva.lecture.helpers.DateFormat;
import com.salva.lecture.helpers.SharedData;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookableFragment extends Fragment {
    private RestClient restClient = App.getRestClient();
    private SharedData sharedData = App.getSharedData();
    private List<CourseTeacher> courseTeacherList;

    public BookableFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bookable, container, false);
        load(view);
        return view;
    }

    public void load(final View view) {
        String token = sharedData.getAccessToken();
        int userId = sharedData.getUserId();
        Call<CourseTeacherEndPointResponse> res = restClient.getReservationService().getBookables(token, userId);
        res.enqueue(new Callback<CourseTeacherEndPointResponse>() {
            @Override
            public void onResponse(Call<CourseTeacherEndPointResponse> call, Response<CourseTeacherEndPointResponse> response) {
                if(response.errorBody() != null) {

                }
                courseTeacherList = response.body().getItems();

                final RecyclerView recyclerView = view.findViewById(R.id.cardList);
                recyclerView.setHasFixedSize(true);
                BookableAdapter bookableAdapter = new BookableAdapter(courseTeacherList, new OnItemClickListener() {
                    @Override
                    public void onItemClick(CourseTeacher item, View view1) {
                        Button button = (Button) view1;
                        bookCourse(item, button, view);
                    }
                });
                recyclerView.setAdapter(bookableAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
            }

            @Override
            public void onFailure(Call<CourseTeacherEndPointResponse> call, Throwable t) {
                Log.e("errore", t.toString());
                courseTeacherList = new ArrayList<>();
            }
        });
    }

    private void bookCourse(CourseTeacher item, final Button buttonView, final View view){
        String token = sharedData.getAccessToken();
        int userId = sharedData.getUserId();
        Call<DumbEndPointResponse> res = restClient.getReservationService().book(
                token, new Reservation(userId, item.id)
        );
        res.enqueue(new Callback<DumbEndPointResponse>() {
            @Override
            public void onResponse(Call<DumbEndPointResponse> call, Response<DumbEndPointResponse> response) {
                boolean success = response.body() != null && response.body().success;
                if(response.errorBody() != null || !success) {
                    Toast.makeText(getContext(), "Prenotazione non riuscita!", Toast.LENGTH_LONG).show();
                    return;
                }

                buttonView.setText("prenotato");
                buttonView.setEnabled(false);
                buttonView.setBackgroundColor(Color.GRAY);
                Toast.makeText(getContext(), "Prenotazione avvenuta", Toast.LENGTH_LONG).show();
                load(view);
            }

            @Override
            public void onFailure(Call<DumbEndPointResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Prenotazione non riuscita!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
