package com.example.isao.twoactivities2.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class HeadsetIntentReceiver extends BroadcastReceiver {

    boolean isFirstTime = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ( intent.getAction().equals(Intent.ACTION_HEADSET_PLUG) && (!isFirstTime) ) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    Toast.makeText
                            (context, "Headset`s unplugged!", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText
                            (context, "Headset`s plugged!", Toast.LENGTH_SHORT).show();
            }
        } else {
            isFirstTime = false;
        }
    }
}
