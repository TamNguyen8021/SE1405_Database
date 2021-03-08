package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.database.daos.StudentDAO;
import com.example.database.dtos.StudentDTO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class DetailStudent extends AppCompatActivity {

    private EditText edtID, edtName, edtMark;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);
        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtMark = findViewById(R.id.edtMark);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        StudentDTO dto = (StudentDTO) intent.getSerializableExtra("DTO");
        String action = intent.getStringExtra("action");
        btnSave.setText(action);
        if (dto != null) {
            edtMark.setText(dto.getMark() + "");
            edtName.setText(dto.getName());
            edtID.setText(dto.getId());
        }
    }

    public void clickToSave(View view) {
        try {
            String id = edtID.getText().toString();
            String name = edtName.getText().toString();
            float mark = Float.parseFloat(edtMark.getText().toString());
            StudentDTO dto = new StudentDTO(id, name, mark);
            StudentDAO dao = new StudentDAO();
            FileInputStream fis = openFileInput("tamntt.txt");
            List<StudentDTO> students = dao.loadFromInternal(fis);
            if (btnSave.getText().equals("Create")) {
                students.add(dto);
            } else {
                for (StudentDTO student:
                     students) {
                    if (student.getId().equals(dto.getId())) {
                        student.setName(dto.getName());
                        student.setMark(dto.getMark());
                        break;
                    }
                }
            }
            FileOutputStream fos = openFileOutput("tamntt.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, students);
            Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}