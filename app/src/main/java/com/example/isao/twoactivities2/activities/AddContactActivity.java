package com.example.isao.twoactivities2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isao.twoactivities2.R;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        final EditText name = (EditText) findViewById(R.id.add_name);
        final EditText number = (EditText) findViewById(R.id.add_number);
        final Button ok = (Button) findViewById(R.id.add_contact_button);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.length() > 0 && number.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("number", number.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Enter a name and a number",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
