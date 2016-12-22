package com.example.isao.twoactivities2.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.helpers.NavigationDrawer;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;
import com.squareup.picasso.Picasso;

public class PictureViewActivity extends NavigationDrawer {

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
        ViewGroup content = (ViewGroup) findViewById(R.id.viewgroup_toolbar);
        getLayoutInflater().inflate(R.layout.activity_picture_view, content, true);

        imageView = (ImageView) findViewById(R.id.changeable_image);
        imageView.setOnClickListener(changeableImageClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                Picasso.with(this).load(data.getData()).into(imageView);
            } else if (requestCode == CAMERA_REQUEST) {
                Picasso.with(this).load(data.getData()).into(imageView);
            }
        }
    }
}
