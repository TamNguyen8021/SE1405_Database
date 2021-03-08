package com.example.database.daos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.database.R;
import com.example.database.dtos.StudentDTO;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private List<StudentDTO> studentDTOList;
    @Override
    public int getCount() {
        return studentDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
        }
        TextView txtID = convertView.findViewById(R.id.txtID);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtMark = convertView.findViewById(R.id.txtMark);
        StudentDTO dto = studentDTOList.get(position);
        txtID.setText(dto.getId());
        txtName.setText(dto.getName());
        txtMark.setText(dto.getMark() + "");
        return convertView;
    }

    public void setStudentDTOList(List<StudentDTO> studentDTOList) {
        this.studentDTOList = studentDTOList;
    }

    public List<StudentDTO> getStudentDTOList() {
        return studentDTOList;
    }
}
