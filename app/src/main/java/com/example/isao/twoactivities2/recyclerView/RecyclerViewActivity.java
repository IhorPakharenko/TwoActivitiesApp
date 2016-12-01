package com.example.isao.twoactivities2.recyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.data.ArrayLists;
import com.example.isao.twoactivities2.data.Student;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    HeadsetIntentReceiver headsetReceiver;

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

    @Override
    public void onResume() {
        headsetReceiver = new HeadsetIntentReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetReceiver, filter);
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(headsetReceiver);
        super.onPause();
    }
}
