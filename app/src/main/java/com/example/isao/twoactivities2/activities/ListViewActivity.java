package com.example.isao.twoactivities2.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.adapters.StudentListViewAdapter;
import com.example.isao.twoactivities2.data.ArrayLists;
import com.example.isao.twoactivities2.model.Student;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class ListViewActivity extends AppCompatActivity {

    HeadsetIntentReceiver headsetReceiver;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.options_menu, menu);
//
//        //Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.listview_fragment, container, false);
//
//        ArrayList<Student> studentsList = ArrayLists.makeStudentsList();
//
//
//        realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        RealmList<Student> studentRealmList = new RealmList<Student>();
//        studentRealmList.addAll(students.subList(0, students.size()));
//        realm.commitTransaction();
//
//        ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
//
//        //Adapter init
//        StudentListViewAdapter listAdapter = new StudentListViewAdapter(super.getContext(), studentsList);
//        listView.setAdapter(listAdapter);
//
//        return rootView;
//
//
//    }
//}

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

//    public static class ListViewActivityFragment extends Fragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.listview_fragment, container, false);
//
//            ArrayList<Student> studentsList = ArrayLists.makeStudentsList();
//
//
//            realm = Realm.getDefaultInstance();
//            realm.beginTransaction();
//            RealmList<Student> studentRealmList = new RealmList<Student>();
//            studentRealmList.addAll(students.subList(0, students.size()));
//            realm.commitTransaction();
//
//            ListView listView = (ListView) rootView.findViewById(R.id.listview_students);
//
//            //Adapter init
//            StudentListViewAdapter listAdapter = new StudentListViewAdapter(super.getContext(), studentsList);
//            listView.setAdapter(listAdapter);
//
//            return rootView;
//
//
//        }
//    }
}
