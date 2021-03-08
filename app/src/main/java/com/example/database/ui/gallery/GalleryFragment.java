package com.example.database.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.database.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private Button btnSave, btnLoad;
    private EditText edtID, edtName, edtMark;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        btnLoad = root.findViewById(R.id.btnLoad);
        btnSave = root.findViewById(R.id.btnSave);
        edtID = root.findViewById(R.id.edtID);
        edtName = root.findViewById(R.id.edtName);
        edtMark = root.findViewById(R.id.edtMark);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences("com.example.database_preferences", Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("IDPref", "");
                String name = sharedPreferences.getString("NamePref", "");
                String mark = sharedPreferences.getString("MarkPref", "");
                edtID.setText(id);
                edtName.setText(name);
                edtMark.setText(mark);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences("com.example.database_preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("IDPref", edtID.getText().toString());
                editor.putString("NamePref", edtName.getText().toString());
                editor.putString("MarkPref", edtMark.getText().toString());
                editor.commit();
            }
        });
        return root;
    }
}