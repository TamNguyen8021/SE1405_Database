package com.example.database.daos;

import android.os.Environment;

import com.example.database.dtos.StudentDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements Serializable {

    public void copyFile(String src, String des) throws Exception {
        File srcF = new File(src);
        File desF = new File(des);
        FileChannel srcChannel = new FileInputStream(srcF).getChannel();
        FileChannel desChannel = new FileOutputStream(desF).getChannel();
        desChannel.transferFrom(srcChannel, 0, srcChannel.size());
    }

    public List<StudentDTO> loadFromRaw(InputStream is) throws Exception {
        List<StudentDTO> result = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            String[] tmp = s.split("-");
            StudentDTO dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
            result.add(dto);
        }
        return result;
    }

    public void saveToInternal(FileOutputStream fos, List<StudentDTO> list) throws Exception {
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        String result = "";
        for (StudentDTO dto: list) {
            result += dto.toString() + "\n";
        }
        osw.write(result);
        osw.flush();
    }

    public List<StudentDTO> loadFromInternal(FileInputStream fis) throws Exception {
        List<StudentDTO> result = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            String[] tmp = s.split("-");
            StudentDTO dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
            result.add(dto);
        }
        return result;
    }

    public boolean saveToExternal(List<StudentDTO> students) throws Exception {
        File sdCard = Environment.getExternalStorageDirectory();
        String realPath = sdCard.getAbsolutePath();
        File directory = new File(realPath + "/MyFiles");
        directory.mkdir();
        File f = new File(directory, "tamntt.txt");
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        String result = "";
        for (StudentDTO student:
             students) {
            result += student.toString() + "\n";
        }
        osw.write(result);
        osw.flush();
        return true;
    }

    public List<StudentDTO> loadFromExternal() throws Exception {
        List<StudentDTO> result = new ArrayList<>();
        File sdCard = Environment.getExternalStorageDirectory();
        String realPath = sdCard.getAbsolutePath();
        File directory = new File(realPath + "/MyFiles");
        File file = new File(directory, "tamntt.txt");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            String[] tmp = s.split("-");
            StudentDTO dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
            result.add(dto);
        }
        return result;
    }
}
