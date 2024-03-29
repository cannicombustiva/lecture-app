package com.salva.lecture.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salva.lecture.Interfaces.OnItemClickListener;
import com.salva.lecture.Interfaces.OnReservationClickListener;
import com.salva.lecture.R;
import com.salva.lecture.api.models.CourseTeacher;
import com.salva.lecture.api.models.Reservation;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservedViewHolder>  {

    private List<Reservation> reservationList;
    private OnReservationClickListener listener;

    public ReservationAdapter(List<Reservation> reservationList, OnReservationClickListener listener) {
        this.reservationList = reservationList;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    @Override
    public void onBindViewHolder(ReservedViewHolder reservedViewHolder, int i) {
        Reservation ci = reservationList.get(i);
        reservedViewHolder.course.setText(ci.courseName);
        reservedViewHolder.teacher.setText(ci.teacherName);
        reservedViewHolder.date.setText(String.format("Data di prenotazione:\n%s", ci.date));
        reservedViewHolder.dateReservation.setText(String.format("Data lezione:\n%s", ci.courseTeacherDate));
        bind(reservedViewHolder.itemView.findViewById(R.id.delete_button), ci, listener);
    }

    public void bind(View itemView ,final Reservation item, final OnReservationClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, v);
            }
        });
    }

    @Override
    public ReservedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_row_reservation, viewGroup, false);

        return new ReservedViewHolder(itemView);
    }
    public static class ReservedViewHolder extends RecyclerView.ViewHolder {

        protected TextView course;
        protected TextView teacher;
        protected TextView date;
        protected TextView dateReservation;

        public ReservedViewHolder(View v) {
            super(v);
            course = v.findViewById(R.id.course);
            teacher = v.findViewById(R.id.teacher);
            date = v.findViewById(R.id.date);
            dateReservation = v.findViewById(R.id.date_reservation);


        }
    }
}
