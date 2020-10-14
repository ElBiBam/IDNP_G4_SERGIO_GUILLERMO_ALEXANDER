package com.practica04.student.adapter;

import android.app.Activity;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.practica04.student.R;
import com.practica04.student.model.StudentModel;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<StudentModel> {
    private final List<StudentModel> list;
    private final Activity context;

    public StudentAdapter(Activity context, List<StudentModel> list) {
        super(context, R.layout.student_item, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView textView_cui;
        protected TextView textView_lastName;
        protected TextView textView_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.student_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView_cui = (TextView) view.findViewById(R.id.textView_cui);
            viewHolder.textView_lastName = (TextView) view.findViewById(R.id.textView_lastName);
            viewHolder.textView_name = (TextView) view.findViewById(R.id.textView_name);

            view.setTag(viewHolder);
            viewHolder.textView_cui.setText(list.get(position).getCui());
            viewHolder.textView_lastName.setText(list.get(position).getLastName());
            viewHolder.textView_name.setText(list.get(position).getName());
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).textView_cui.setText(list.get(position).getCui());
            ((ViewHolder) view.getTag()).textView_lastName.setText(list.get(position).getLastName());
            ((ViewHolder) view.getTag()).textView_name.setText(list.get(position).getName());
        }

        return view;
    }
}
