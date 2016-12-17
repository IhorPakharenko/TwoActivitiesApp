package com.example.isao.twoactivities2.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.adapters.StudentRecyclerViewAdapter;
import com.example.isao.twoactivities2.data.ArrayLists;
import com.example.isao.twoactivities2.model.Student;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RecyclerViewActivity extends AppCompatActivity {

    HeadsetIntentReceiver headsetReceiver;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Realm.init(RecyclerViewActivity.this);
        realm = Realm.getDefaultInstance();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.students_recyclerview);
        final StudentRecyclerViewAdapter studentsAdapter =
                new StudentRecyclerViewAdapter(this, ArrayLists.makeStudentsList());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                RealmResults<Student> studentRealmResults =
                        realm.where(Student.class)
                                .findAllSorted("studentsName", Sort.ASCENDING);
                if (studentRealmResults.size() <= 0) {
                    ArrayList<Student> students = ArrayLists.makeStudentsList();
                    realm.beginTransaction();
                    realm.insertOrUpdate(students);
                    realm.commitTransaction();
                    studentRealmResults = realm.where(Student.class)
                            .findAllSorted("studentsName");
                }
                ArrayList<Student> studentArrayList = new ArrayList<Student>();
                for (Student student : studentRealmResults) {
                    if (student.getStudentsName().toLowerCase().contains(newText.toLowerCase())) {
                        studentArrayList.add(student);
                    }
                }

                final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.students_recyclerview);
                final StudentRecyclerViewAdapter studentsAdapter =
                        new StudentRecyclerViewAdapter(RecyclerViewActivity.this, studentArrayList);
                recyclerView.setAdapter(studentsAdapter);

                return false;
            }
        });

        return true;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
