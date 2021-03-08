package com.example.database;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.daos.StudentAdapter;
import com.example.database.daos.StudentDAO;
import com.example.database.dtos.StudentDTO;
import com.example.database.ui.StudentPreferenceActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewStudent;
    private TextView txtTitle;
    private StudentAdapter adapter;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listViewStudent = findViewById(R.id.listStudent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void clickToLoadFromRaw(MenuItem item) {
        listViewStudent = findViewById(R.id.listStudent);
        txtTitle = findViewById(R.id.txtTitle);
        adapter = new StudentAdapter();
        txtTitle.setText("List Student From RAW");
        InputStream is = getResources().openRawResource(R.raw.data);
        try {
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> listStudent = dao.loadFromRaw(is);
            adapter.setStudentDTOList(listStudent);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
//                    Toast.makeText(MainActivity.this, dto.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DetailStudent.class);
                    intent.putExtra("DTO", dto);
                    intent.putExtra("action", "Update");
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToSaveFromRawToInternal(MenuItem item) {
        List<StudentDTO> listStudents = adapter.getStudentDTOList();
        try {
            StudentDAO dao = new StudentDAO();
            FileOutputStream fos = openFileOutput("tamntt.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, listStudents);
            Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToLoadFromInternal(MenuItem item) {
        listViewStudent = findViewById(R.id.listStudent);
        txtTitle = findViewById(R.id.txtTitle);
        adapter = new StudentAdapter();
        txtTitle.setText("List Student From Internal");
        try {
            FileInputStream fis = openFileInput("tamntt.txt");
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> listStudent = dao.loadFromInternal(fis);
            adapter.setStudentDTOList(listStudent);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
//                    Toast.makeText(MainActivity.this, dto.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DetailStudent.class);
                    intent.putExtra("DTO", dto);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToCreate(View view) {
        Intent intent = new Intent(this, DetailStudent.class);
        intent.putExtra("action", "Create");
        startActivity(intent);
    }

    public void clickToSaveToExternal(MenuItem item) {
        try {
            FileInputStream fis = openFileInput("tamntt.txt");
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> students = dao.loadFromInternal(fis);
            if (dao.saveToExternal(students)) {
                Toast.makeText(this, "Save success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToLoadFromExternal(MenuItem item) {
        listViewStudent = findViewById(R.id.listStudent);
        txtTitle = findViewById(R.id.txtTitle);
        adapter = new StudentAdapter();
        txtTitle.setText("List Student From External");
        try {
            StudentDAO dao = new StudentDAO();
            List<StudentDTO> students = dao.loadFromExternal();
            adapter.setStudentDTOList(students);
            listViewStudent.setAdapter(adapter);
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailStudent.class);
                    intent.putExtra("DTO", dto);
                    intent.putExtra("action", "Update");
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToSharePreference(MenuItem item) {
        Intent intent = new Intent(this, StudentPreferenceActivity.class);
        startActivity(intent);
    }

    public void clickToBackup(MenuItem item) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            String realPath = sdCard.getAbsolutePath();
            String desDir = realPath + "/MyFiles";
            File directory = new File(desDir);
            if (!directory.exists()) {
                directory.mkdir();
            }
            String dataPath = "/data/data/" + this.getPackageName() + "/files";
            File dataDir = new File(dataPath);
            File[] listFile = dataDir.listFiles();
            if (listFile != null) {
                StudentDAO dao = new StudentDAO();
                for (int i = 0; i < listFile.length; i++) {
                    File f = listFile[i];
                    dao.copyFile(f.getAbsolutePath(), desDir + "/" + f.getName());
                    Toast.makeText(this, "Backup success", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToRestore(MenuItem item) {
        try {
            String dataPathDes = "/data/data/" + this.getPackageName() + "/files";
            File sdCard = Environment.getExternalStorageDirectory();
            String realPath = sdCard.getAbsolutePath();
            String srcDir = realPath + "/MyFiles";
            File dataDir = new File(srcDir);

            File[] listFiles = dataDir.listFiles();
            if (listFiles != null) {
                StudentDAO dao = new StudentDAO();
                for (int i = 0; i < listFiles.length; i++) {
                    File f = listFiles[i];
                    dao.copyFile(f.getAbsolutePath(), dataPathDes + "/" + f.getName());
                    Toast.makeText(this, "Restore success", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}