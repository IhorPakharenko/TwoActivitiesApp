package com.example.isao.twoactivities2.accountViews;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isao.twoactivities2.GoogleRequestInterface;
import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.helpers.ImageHelper;
import com.example.isao.twoactivities2.model.GoogleUser;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.io.IOException;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleActivity extends AppCompatActivity {
    private final String LOG_TAG = GoogleActivity.class.getSimpleName();
    HeadsetIntentReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        Intent intent = getIntent();

        if (intent.hasExtra("GOOGLE_LINK")) {
            Log.w("if", "true");
            String linkSegment = intent.getStringExtra("GOOGLE_LINK");
            tryGetInfo(linkSegment);
        } else if (intent.getData().getHost().equals("plus.google.com")) {
            Log.w("else if", "true");
            String linkSegment = intent.getData().getLastPathSegment();
            tryGetInfo(linkSegment);
        } else {
            handleIncorrectUrl();
        }
    }

    public void tryGetInfo(String userSegment) {
        try {
            getInfo(userSegment);
        } catch (IOException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Check your Internet connection",
                    Toast.LENGTH_LONG)
                    .show();
            Log.e(LOG_TAG, "Error " + e);
        }
    }

    public void getInfo(String parameter) throws IOException {
        if (parameter != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GoogleRequestInterface request = retrofit.create(GoogleRequestInterface.class);
            Call<GoogleUser> call = request.getJSON(parameter);
            call.enqueue(new Callback<GoogleUser>() {
                @Override
                public void onResponse(Call<GoogleUser> call, Response<GoogleUser> response) {

                    if (response.body() != null) {
                        GoogleUser googleUser = response.body();
                        setInfo(googleUser);
                    } else handleIncorrectUrl();
                }

                @Override
                public void onFailure(Call<GoogleUser> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        } else {
            handleIncorrectUrl();
        }

    }

    public void setInfo(GoogleUser googleUser) {
        if (googleUser.getGoogleName() != null) {
            TextView textViewName = (TextView) findViewById(R.id.google_name);
            textViewName.setText(googleUser.getGoogleName());
            ImageView imageViewAvatar = (ImageView) findViewById(R.id.google_avatar);
            new ImageHelper(imageViewAvatar)
                    .execute(getImprovedAvatarUrl(googleUser.getGoogleAvatar().getUrl(), 200));
        } else {
            handleIncorrectUrl();
        }
    }


    private void handleIncorrectUrl() {
        Toast.makeText
                (getApplicationContext(), "Please open only user pages", Toast.LENGTH_LONG)
                .show();
        finish();
    }

    private String getImprovedAvatarUrl(String defaultUrl, int size) {
        HttpUrl url = HttpUrl.parse(defaultUrl)
                .newBuilder()
                .removeAllQueryParameters("sz")
                .addQueryParameter("sz", new Integer(size).toString())
                .build();
        return url.toString();
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
