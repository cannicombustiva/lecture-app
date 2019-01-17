package com.salva.lecture.api.models;

import java.util.List;

public class ReservationEndPointResponse {
    private List<Reservation> items;

    public ReservationEndPointResponse(List<Reservation> items) {
        this.items = items;
    }

    public List<Reservation> getList() {
        return items;
    }

    public void setItems(List<Reservation> items) {
        this.items = items;
    }
}