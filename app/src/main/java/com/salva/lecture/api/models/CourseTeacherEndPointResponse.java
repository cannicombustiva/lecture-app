package com.salva.lecture.api.models;

import java.util.List;

public class CourseTeacherEndPointResponse {
    private List<CourseTeacher> items;

    public CourseTeacherEndPointResponse(List<CourseTeacher> items) {
        this.items = items;
    }

    public List<CourseTeacher> getItems() {
        return items;
    }

    public void setItems(List<CourseTeacher> items) {
        this.items = items;
    }
}
