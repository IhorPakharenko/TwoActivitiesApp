package com.example.isao.twoactivities2.accountViews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isao.twoactivities2.R;
import com.example.isao.twoactivities2.helpers.ImageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        Intent intent = getIntent();
        GetInfoTask getInfoTask = new GetInfoTask();
        getInfoTask.execute(intent.getStringExtra("GITHUB_LINK"));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class GetInfoTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = GetInfoTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] strings) {
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

            //String nullString = "null";
            //userDataJson.put(nullString, nullString);
            //Log.w("null string is ", userDataJson.getString(nullString));

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