package com.example.isao.twoactivities2.pictureViewActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

public class PictureViewActivity extends AppCompatActivity {

    final int GALLERY_REQUEST = 0;
    final int CAMERA_REQUEST = 1;
    HeadsetIntentReceiver headsetReceiver;
    ImageView imageView;
    View.OnClickListener changeableImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder actionChooseBuilder = new AlertDialog.Builder(PictureViewActivity.this);
            actionChooseBuilder.setTitle("Choose an action");

            final CharSequence[] items = {
                    "Gallery", "Camera"
            };
            actionChooseBuilder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Gallery")) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickPhoto.setType("image/*");
                                startActivityForResult(pickPhoto, GALLERY_REQUEST);
                            } else if (items[item].equals("Camera")) {
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, CAMERA_REQUEST);
                            }
                        }
                    }
            );
            actionChooseBuilder.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);

        imageView = (ImageView) findViewById(R.id.changeable_image);
        imageView.setOnClickListener(changeableImageClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                Log.w("Picture from", "gallery");
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
            } else if (requestCode == CAMERA_REQUEST) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    @Override
    public void onResume() {
        Log.w("Receiver", "registering");
        headsetReceiver = new HeadsetIntentReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetReceiver, filter);
        super.onResume();
        Log.w("Receiver", "registered!");
    }

    @Override
    public void onPause() {
        Log.w("Receiver", "unregistering!");
        unregisterReceiver(headsetReceiver);
        super.onPause();

    }
}
