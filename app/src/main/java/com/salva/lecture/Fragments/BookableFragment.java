package com.salva.lecture.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.salva.lecture.App.App;
import com.salva.lecture.HomeActivity;
import com.salva.lecture.LoginActivity;
import com.salva.lecture.R;
import com.salva.lecture.api.RestClient;
import com.salva.lecture.api.models.CourseTeacher;
import com.salva.lecture.api.models.CourseTeacherEndPointResponse;
import com.salva.lecture.api.models.Reservation;
import com.salva.lecture.api.models.ReservationEndPointResponse;
import com.salva.lecture.api.models.User;
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
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookableFragment extends Fragment {
    private RestClient restClient = App.getRestClient();
    private SharedData sharedData = App.getSharedData();
    public BookableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bookable, container, false);
        String token = sharedData.getAccessToken();
        int userId = sharedData.getUserId();
        Call<CourseTeacherEndPointResponse> res = restClient.getReservationService().getBookables(token, userId);
        res.enqueue(new Callback<CourseTeacherEndPointResponse>() {
            @Override
            public void onResponse(Call<CourseTeacherEndPointResponse> call, Response<CourseTeacherEndPointResponse> response) {
                if(response.errorBody() != null) {

                }
                List<CourseTeacher> courseTeachers = response.body().getItems();
                for(CourseTeacher courseTeacher : courseTeachers){
                    LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    LinearLayout.LayoutParams textParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    LinearLayout.LayoutParams textParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                    TextView textDate = new TextView(getContext());
                    textDate.append(courseTeacher.date);
                    textDate.setLayoutParams(textParams);
                    TextView textTeacher = new TextView(getContext());
                    textTeacher.append(courseTeacher.teacherName);
                    textTeacher.setLayoutParams(textParams1);
                    TextView text = new TextView(getContext());
                    text.append(courseTeacher.courseName);
                    text.setLayoutParams(textParams2);

                    LinearLayout cardLayout = new LinearLayout(getContext());
                    cardLayout.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT));
                    cardLayout.addView(text);
                    cardLayout.addView(textTeacher);
                    cardLayout.addView(textDate);

                    CardView card = new CardView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                    params.gravity = Gravity.CENTER;
                    params.leftMargin = 10;
                    params.rightMargin = 10;
                    params.bottomMargin = 50;
                    params.topMargin = 50;
                    card.setLayoutParams(params);
                    card.setRadius(4);
                    card.addView(cardLayout);
                    LinearLayout linearLayout = view.findViewById(R.id.scrollList);
                    linearLayout.addView(card);
                }
            }

            @Override
            public void onFailure(Call<CourseTeacherEndPointResponse> call, Throwable t) {
                Log.e("errore", t.toString());
            }
        });
        return view;
    }
}
