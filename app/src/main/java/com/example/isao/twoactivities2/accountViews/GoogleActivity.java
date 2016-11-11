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

import okhttp3.HttpUrl;

public class GoogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        Intent intent = getIntent();
        GetInfoTask getInfoTask = new GetInfoTask();
        getInfoTask.execute(intent.getStringExtra("GOOGLE_LINK"));
    }

    private String getImprovedAvatarUrl(String defaultUrl, int size) {
        HttpUrl url = HttpUrl.parse(defaultUrl)
                .newBuilder()
                .removeAllQueryParameters("sz")
                .addQueryParameter("sz", new Integer(size).toString())
                .build();
        return url.toString();
    }

    public class GetInfoTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = GetInfoTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] strings) {

            TextView textViewName = (TextView) findViewById(R.id.google_name);
            textViewName.setText(strings[1]);
            ImageView imageViewAvatar = (ImageView) findViewById(R.id.google_avatar);
            new ImageHelper(imageViewAvatar)
                    .execute(getImprovedAvatarUrl(strings[0], 200));
            super.onPostExecute(strings);
        }

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String infoJsonStr = null;
            final String apiKey = "?key=AIzaSyC0y5Lh1Qlhmyb04F4jtcSX4MGEJEgbuBw";

            try {
                URL url = new URL("https://www.googleapis.com/plus/v1/people/" +
                        params[0] + apiKey);
                Log.w(LOG_TAG, url.toString());
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
            final String AVATAR_URL = "url";
            final String NAME = "displayName";
            final String AVATAR_PATH = "image";

            JSONObject userDataJson = new JSONObject(infoJsonStr);
            JSONObject avatarPath = userDataJson.getJSONObject(AVATAR_PATH);
            //JSONObject avatarObject = avatarPath.getJSONObject(AVATAR_URL);

            String avatar;
            String name;

            avatar = avatarPath.getString(AVATAR_URL);

            if (!(userDataJson.getString(NAME).equals("null"))) {
                name = userDataJson.getString(NAME);
            } else {
                name = "Ім'я невідоме";
            }

            String[] resultStrings = {
                    avatar, name
            };

            Log.w(LOG_TAG, resultStrings[0] + " first");
            Log.w(LOG_TAG, resultStrings[1] + " second");

            return resultStrings;
        }
    }
}