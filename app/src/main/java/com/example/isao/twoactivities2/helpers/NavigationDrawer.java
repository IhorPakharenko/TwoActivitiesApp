package com.example.isao.twoactivities2.helpers;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.activities.ContactsActivity;
import com.example.isao.twoactivities2.activities.ListViewActivity;
import com.example.isao.twoactivities2.activities.MainActivity;
import com.example.isao.twoactivities2.activities.PictureViewActivity;
import com.example.isao.twoactivities2.activities.RecyclerViewActivity;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class NavigationDrawer extends AppCompatActivity {

    HeadsetIntentReceiver headsetReceiver;
    Drawer drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.app_name),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.title_activity_listview),
                        new SecondaryDrawerItem().withName(R.string.title_activity_recyclerview),
                        new SecondaryDrawerItem().withName(R.string.title_activity_pictureview),
                        new SecondaryDrawerItem().withName(R.string.title_activity_contacts)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                startActivity(new Intent(NavigationDrawer.this, MainActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(NavigationDrawer.this, ListViewActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(NavigationDrawer.this, RecyclerViewActivity.class));
                                break;
                            case 5:
                                startActivity(new Intent(NavigationDrawer.this, PictureViewActivity.class));
                                break;
                            case 6:
                                startActivity(new Intent(NavigationDrawer.this, ContactsActivity.class));
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
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
