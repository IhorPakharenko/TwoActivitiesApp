package com.example.isao.twoactivities2.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.adapters.ContactsRecyclerViewAdapter;
import com.example.isao.twoactivities2.helpers.ContactHelper;
import com.example.isao.twoactivities2.helpers.NavigationDrawer;
import com.example.isao.twoactivities2.model.Contact;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.util.ArrayList;

public class ContactsActivity extends NavigationDrawer {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 90;
    private static final int ADD_NEW_CONTACT = 1;
    HeadsetIntentReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup content = (ViewGroup) findViewById(R.id.viewgroup_toolbar);
        getLayoutInflater().inflate(R.layout.activity_contacts, content, true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.WRITE_CONTACTS) !=
                                PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                            PERMISSIONS_REQUEST_WRITE_CONTACTS);
                }
                startActivityForResult(intent, ADD_NEW_CONTACT);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                refreshData();
            } else {
                Toast.makeText(this,
                        "Grant the permissions!",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
        if (requestCode == PERMISSIONS_REQUEST_WRITE_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                refreshData();
            } else {
                Toast.makeText(this,
                        "Grant the permissions!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ADD_NEW_CONTACT && intent != null) {
            String name = intent.getStringExtra("name");
            String number = intent.getStringExtra("number");
            ContactHelper.addContact(name, number, this);
            refreshData();
        }
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }


    public void refreshData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            ArrayList<Contact> contactList = ContactHelper.getContacts(this);

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_recyclerview);
            final ContactsRecyclerViewAdapter contactsAdapter = new ContactsRecyclerViewAdapter(this, contactList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(contactsAdapter);
        }
    }
}
