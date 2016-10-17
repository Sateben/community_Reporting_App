package com.krrishdholakia.community_reporting.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.krrishdholakia.community_reporting.application.App_Sign_In.ChooserActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button response_Btn = (Button)findViewById(R.id.home_btn);



        if (response_Btn != null)
            response_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, ChooserActivity.class);

                    /* Starts an active showing the details for the selected list */
                    startActivity(intent);

                }
            });
    }
}
