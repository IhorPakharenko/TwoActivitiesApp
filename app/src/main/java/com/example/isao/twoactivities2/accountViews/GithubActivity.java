package com.example.isao.twoactivities2.accountViews;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.helpers.ImageHelper;
import com.example.isao.twoactivities2.receivers.HeadsetIntentReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubActivity extends AppCompatActivity {

    private final String LOG_TAG = GetInfoTask.class.getSimpleName();
    HeadsetIntentReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        Intent intent = getIntent();

        if (intent.hasExtra("GITHUB_LINK")) {
            Log.d(LOG_TAG, "there`s a right link");
            Log.d(LOG_TAG, intent.getStringExtra("GITHUB_LINK"));
            GetInfoTask getInfoTask = new GetInfoTask();
            getInfoTask.execute(intent.getStringExtra("GITHUB_LINK"));
        } else if (intent.getData().getHost().equals("github.com")) {
            String customLink = intent.getData().getLastPathSegment();
            GetInfoTask getInfoTask = new GetInfoTask();
            getInfoTask.execute(customLink);
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

    public class GetInfoTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPostExecute(String[] strings) {
            if (strings != null) {
                TextView textViewName = (TextView) findViewById(R.id.git_name);
                textViewName.setText(strings[1]);
                TextView textViewEmail = (TextView) findViewById(R.id.git_email);
                textViewEmail.setText(strings[2]);
                TextView textViewAddress = (TextView) findViewById(R.id.git_address);
                textViewAddress.setText(strings[3]);
                ImageView imageViewAvatar = (ImageView) findViewById(R.id.git_avatar);
                new ImageHelper(imageViewAvatar)
                        .execute(strings[0]);
                super.onPostExecute(strings);
            } else {
                handleIncorrectUrl();
            }

        }

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String infoJsonStr = null;


            try {
                URL url = new URL("https://api.github.com/users/" + params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 404) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    infoJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    infoJsonStr = null;
                }
                infoJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                infoJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            Log.w(LOG_TAG, infoJsonStr);
            try {
                String[] result = getDataFromJson(infoJsonStr);
                Log.w(LOG_TAG, "Before return " + result[0]);
                return result;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        private String[] getDataFromJson(String infoJsonStr) throws JSONException {
            final String AVATAR_URL = "avatar_url";
            final String NAME = "name";
            final String LOGIN = "login";
            final String EMAIL = "email";
            final String ADDRESS = "location";

            JSONObject userDataJson = new JSONObject(infoJsonStr);

            String avatar;
            String name;
            String email;
            String address;

            avatar = userDataJson.getString(AVATAR_URL);
            if (!(userDataJson.getString(NAME).equals("null"))) {
                name = userDataJson.getString(NAME);
            } else {
                name = userDataJson.getString(LOGIN);
            }
            if (!(userDataJson.getString(EMAIL).equals("null"))) {
                email = userDataJson.getString(EMAIL);
            } else {
                email = "Невідомий імейл";
            }
            if (!(userDataJson.getString(ADDRESS).equals("null"))) {
                address = userDataJson.getString(ADDRESS);
            } else {
                address = "Невідома адреса";
            }
            String[] resultStrings = {
                    avatar, name, email, address
            };

            Log.w(LOG_TAG, resultStrings[0] + " first");
            Log.w(LOG_TAG, resultStrings[1] + " second");
            Log.w(LOG_TAG, resultStrings[2] + " third");
            Log.w(LOG_TAG, resultStrings[3] + " fourth");

            return resultStrings;
        }
    }
}
