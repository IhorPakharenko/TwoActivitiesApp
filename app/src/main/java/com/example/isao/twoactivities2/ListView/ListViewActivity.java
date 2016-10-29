package com.example.isao.twoactivities2.listView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.data.Student;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);


        if (savedInstanceState == null) {
            //bind the fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_listview, new ListViewActivityFragment())
                    .commit();

        }
    }

    public static class ListViewActivityFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.listview_fragment, container, false);

            ArrayList<Student> studentsList = Student.makeStudentsList();

            ListView listView = (ListView) rootView.findViewById(R.id.listview_students);

            //Adapter init
            StudentListViewAdapter listAdapter = new StudentListViewAdapter(super.getContext(), studentsList);
            listView.setAdapter(listAdapter);

            return rootView;


        }


    }

}