package com.example.isao.twoactivities2.activities;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.adapters.StudentListViewAdapter;
import com.example.isao.twoactivities2.data.ArrayLists;
import com.example.isao.twoactivities2.helpers.NavigationDrawer;
import com.example.isao.twoactivities2.model.Student;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class ListViewActivity extends NavigationDrawer {

    HeadsetIntentReceiver headsetReceiver;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup content = (ViewGroup) findViewById(R.id.viewgroup_toolbar);
        getLayoutInflater().inflate(R.layout.activity_listview, content, true);

        Realm.init(ListViewActivity.this);
        realm = Realm.getDefaultInstance();
        RealmResults<Student> studentRealmResults;
        studentRealmResults = realm.where(Student.class).findAllSorted("studentsName", Sort.ASCENDING);

        if (studentRealmResults.size() <= 0) {
            ArrayList<Student> students = ArrayLists.makeStudentsList();
            realm.beginTransaction();
            realm.insertOrUpdate(students);
            realm.commitTransaction();
            studentRealmResults = realm.where(Student.class).findAllSorted("studentsName", Sort.ASCENDING);
        }

        ListView listView = (ListView) findViewById(R.id.listview_students);

        //Adapter init
        StudentListViewAdapter listAdapter =
                new StudentListViewAdapter(ListViewActivity.this, studentRealmResults);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
