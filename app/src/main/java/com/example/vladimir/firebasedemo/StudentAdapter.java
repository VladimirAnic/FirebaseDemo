package com.example.vladimir.firebasedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vladimir on 16.9.2017..
 */

public class StudentAdapter extends BaseAdapter {

    private ArrayList<Student> mStudents;
    public StudentAdapter(ArrayList<Student> students) { mStudents = students; }

    @Override
    public int getCount() {
        return this.mStudents.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mStudents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder studentViewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_book, parent, false);
            studentViewHolder = new ViewHolder(convertView);
            convertView.setTag(studentViewHolder);
        } else {
            studentViewHolder = (ViewHolder) convertView.getTag();
        }
        Student student = this.mStudents.get(position);
        studentViewHolder.tvFandLName.setText(student.getmFAndLName());

        return convertView;
    }


    public static class ViewHolder {
        public TextView tvFandLName;

        public ViewHolder(View taskView) {
            tvFandLName = (TextView) taskView.findViewById(R.id.tvFandLName);
        }
    }
}
