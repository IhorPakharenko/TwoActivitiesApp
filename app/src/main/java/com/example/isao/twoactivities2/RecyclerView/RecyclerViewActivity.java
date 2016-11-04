package com.example.isao.twoactivities2.recyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.data.ArrayLists;
import com.example.isao.twoactivities2.data.Student;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        ArrayList<Student> students = ArrayLists.makeStudentsList();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.students_recyclerview);
        final StudentRecyclerViewAdapter studentsAdapter = new StudentRecyclerViewAdapter(this, students);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentsAdapter);
    }
}
