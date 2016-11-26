package com.example.isao.twoactivities2.contactsActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.data.Contact;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    HeadsetIntentReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refreshData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivityForResult(intent, 0);
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
                Toast.makeText(this, "Until you grant the permission, the app is not able to show contacts", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        headsetReceiver = new HeadsetIntentReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetReceiver, filter);
        refreshData();
        super.onResume();
    }

    @Override
    public void onPause() {
        unregisterReceiver(headsetReceiver);
        super.onPause();
    }

    public void refreshData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            ArrayList<Contact> contactList = contacts();

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_recyclerview);
            final ContactsRecyclerViewAdapter contactsAdapter = new ContactsRecyclerViewAdapter(this, contactList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(contactsAdapter);
        }

    }

    public ArrayList<Contact> contacts() {
        ArrayList<Contact> contacts = new ArrayList<>();

        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                "DISPLAY_NAME"
        );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Uri table = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
                    String[] selectionArgs = {id};

                    Cursor numCursor = resolver.query(table, null, selection, selectionArgs, null);
                    while (numCursor.moveToNext()) {
                        String phoneNum = numCursor.getString(
                                numCursor.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(new Contact(name, phoneNum));
                    }
                    numCursor.close();
                }
            }
        }
        cursor.close();
        return contacts;
    }
}
