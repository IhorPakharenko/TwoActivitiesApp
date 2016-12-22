package com.example.isao.twoactivities2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.helpers.NavigationDrawer;

public class MainActivity extends NavigationDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup content = (ViewGroup) findViewById(R.id.viewgroup_toolbar);
        getLayoutInflater().inflate(R.layout.activity_main, content, true);

        final View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.listview_start:
                        startListViewActivity(v);
                        break;
                    case R.id.recyclerview_start:
                        startRecyclerViewActivity(v);
                        break;
                    case R.id.pictureview_start:
                        startPictureViewActivity(v);
                        break;
                    case R.id.contactsview_start:
                        startContactsActivity(v);
                        break;
                }
            }
        };
        Button listViewButton = (Button) findViewById(R.id.listview_start);
        Button recyclerViewButton = (Button) findViewById(R.id.recyclerview_start);
        Button pictureViewButton = (Button) findViewById(R.id.pictureview_start);
        Button contactsViewButton = (Button) findViewById(R.id.contactsview_start);

        listViewButton.setOnClickListener(onClickListener);
        recyclerViewButton.setOnClickListener(onClickListener);
        pictureViewButton.setOnClickListener(onClickListener);
        contactsViewButton.setOnClickListener(onClickListener);
    }

    public void startListViewActivity(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }

    public void startRecyclerViewActivity(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }

    public void startPictureViewActivity(View view) {
        startActivity(new Intent(
                this, PictureViewActivity.class
        ));
    }

    public void startContactsActivity(View view) {
        startActivity(new Intent(
                this, ContactsActivity.class
        ));
    }
}
