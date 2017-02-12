package com.example.dikshant.tutorial1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView messagetext;
    Button changetextbtn;
    Button changetextbtn2;
    Spinner spinnertest;
    //private ClarifaiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.potato_list, android.R.layout.simple_spinner_item);

        messagetext = (TextView) findViewById(R.id.message_1);

        changetextbtn = (Button) findViewById(R.id.button_1);
        changetextbtn.setText("Select an Image");

        changetextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagetext.setText(getString(R.string.user_message));
                userClarification(adapter);


            }
        });
    }

    private void userClarification(ArrayAdapter<CharSequence> adapter) {
        setContentView(R.layout.activity_second);

        spinnertest = (Spinner) findViewById(R.id.spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertest.setAdapter(adapter);

        messagetext = (TextView) findViewById(R.id.message_2);
        changetextbtn2 = (Button) findViewById(R.id.button_2);
        changetextbtn2.setText("Select Potato");

        changetextbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagetext.setText("Use the Drop-Down Menu");
            }
        });
    }
}
