package com.salva.lecture.api.models;

public class CourseTeacher {
    public String courseName;
    public String teacherName;
    public String date;
    public int teacherId;
    public int courseId;
    public int id;

    public CourseTeacher(String courseName, String teacherName, String date, int teacherId, int courseId, int id) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.date = date;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.id = id;
    }
}
