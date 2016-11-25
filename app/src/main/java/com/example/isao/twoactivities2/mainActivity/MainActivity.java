package com.example.isao.twoactivities2.mainActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.isao.twoactivities2.ContactsActivity;
import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.listView.ListViewActivity;
import com.example.isao.twoactivities2.pictureViewActivity.PictureViewActivity;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;
import com.example.isao.twoactivities2.recyclerView.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {

    HeadsetIntentReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
