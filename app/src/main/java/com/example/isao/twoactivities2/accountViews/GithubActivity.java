package com.example.isao.twoactivities2.accountViews;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.helpers.ImageHelper;
import com.example.isao.twoactivities2.interfaces.GithubRequestInterface;
import com.example.isao.twoactivities2.model.GithubUser;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubActivity extends AppCompatActivity {

    private final String LOG_TAG = GithubActivity.class.getSimpleName();
    HeadsetIntentReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        Intent intent = getIntent();

        if (intent.hasExtra("GITHUB_LINK")) {
            String linkSegment = intent.getStringExtra("GITHUB_LINK");
            tryGetInfo(linkSegment);
        } else if (intent.getData().getHost().equals("github.com")) {
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
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GithubRequestInterface request = retrofit.create(GithubRequestInterface.class);
            Call<GithubUser> call = request.getJSON(parameter);
            call.enqueue(new Callback<GithubUser>() {
                @Override
                public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {

                    if (response.body() != null) {
                        GithubUser githubUser = response.body();
                        setInfo(githubUser);
                    } else handleIncorrectUrl();
                }

                @Override
                public void onFailure(Call<GithubUser> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        } else {
            handleIncorrectUrl();
        }

    }

    public void setInfo(GithubUser githubUser) {
        String name;
        if (githubUser.getGitLogin() != null) {
            try {
                name = githubUser.getGitName();
                Log.w("name", name);
            } catch (NullPointerException e) {
                name = githubUser.getGitLogin();
                Log.w("name", name);
            }
            TextView textViewName = (TextView) findViewById(R.id.git_name);
            textViewName.setText(name);
            TextView textViewEmail = (TextView) findViewById(R.id.git_email);
            textViewEmail.setText(githubUser.getGitEmail());
            TextView textViewAddress = (TextView) findViewById(R.id.git_address);
            textViewAddress.setText(githubUser.getGitAddress());
            ImageView imageViewAvatar = (ImageView) findViewById(R.id.git_avatar);
            new ImageHelper(imageViewAvatar)
                    .execute(githubUser.getGitAvatar());
        } else {
            handleIncorrectUrl();
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

    private void handleIncorrectUrl() {
        Toast.makeText
                (getApplicationContext(), "Please open only user pages", Toast.LENGTH_LONG)
                .show();
        finish();
    }
}
