package com.salva.lecture.api.models;

import java.util.List;

public class Reservation {
    public int id;
    public int userId;
    public int courseTeacherId;
    public String courseName;
    public String teacherName;
    public String courseTeacherDate;
    public String date;

    public Reservation(int id, int userId, int courseTeacherId, String courseName, String teacherName, String courseTeacherDate, String date) {
        this.id = id;
        this.userId = userId;
        this.courseTeacherId = courseTeacherId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.courseTeacherDate = courseTeacherDate;
        this.date = date;
    }

    public Reservation(int userId, int courseTeacherId) {
        this.userId = userId;
        this.courseTeacherId = courseTeacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseTeacherId() {
        return courseTeacherId;
    }

    public void setCourseTeacherId(int courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseTeacherDate() {
        return courseTeacherDate;
    }

    public void setCourseTeacherDate(String courseTeacherDate) {
        this.courseTeacherDate = courseTeacherDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

