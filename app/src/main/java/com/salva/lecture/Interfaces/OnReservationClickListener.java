package com.salva.lecture.Interfaces;

import android.view.View;
import com.salva.lecture.api.models.Reservation;

public interface OnReservationClickListener {
    void onItemClick(Reservation item, View view);
}
