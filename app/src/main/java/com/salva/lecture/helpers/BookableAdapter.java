package com.salva.lecture.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.salva.lecture.Interfaces.OnItemClickListener;
import com.salva.lecture.R;
import com.salva.lecture.api.models.CourseTeacher;

import java.util.List;

public class BookableAdapter extends RecyclerView.Adapter<BookableAdapter.BookableViewHolder>  {

    private List<CourseTeacher> courseTeacherList;
    private OnItemClickListener listener;

    public BookableAdapter(List<CourseTeacher> courseTeacherList, OnItemClickListener listener) {
        this.courseTeacherList = courseTeacherList;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return courseTeacherList.size();
    }

    @Override
    public void onBindViewHolder(BookableViewHolder bookableViewHolder, int i) {
        CourseTeacher ci = courseTeacherList.get(i);
        bookableViewHolder.course.setText(ci.courseName);
        bookableViewHolder.teacher.setText(ci.teacherName);
        bookableViewHolder.date.setText(ci.date);
        bind(bookableViewHolder.itemView.findViewById(R.id.reservation_button), ci, listener);
    }

    public void bind(View itemView ,final CourseTeacher item, final OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, v);
            }
        });
    }


    @Override
    public BookableViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_row, viewGroup, false);

        return new BookableViewHolder(itemView);
    }
    public static class BookableViewHolder extends RecyclerView.ViewHolder {

        protected TextView course;
        protected TextView teacher;
        protected TextView date;

        public BookableViewHolder(View v) {
            super(v);
            course = v.findViewById(R.id.course);
            teacher = v.findViewById(R.id.teacher);
            date = v.findViewById(R.id.date);

        }
    }
}
