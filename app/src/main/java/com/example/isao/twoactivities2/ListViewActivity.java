package com.example.isao.twoactivities2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        if (savedInstanceState == null) {
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


            String[] names = {"Свєта", "Марія", "Одарка", "Стаська",
                    "Костя", "Ігорка", "Богдан Хмельницький", "Денис", "Андрійко", "Вася",
                    "Олесь", "Дмитрик", "Геральт Рівійський"};


            ListView listView = (ListView) rootView.findViewById(R.id.listview_people);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (getActivity(),
                            R.layout.list_item_people,
                            R.id.item_textview,
                            names);


            listView.setAdapter(adapter);

            return rootView;

        }
    }
}
