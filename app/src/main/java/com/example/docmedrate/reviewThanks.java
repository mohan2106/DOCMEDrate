package com.example.docmedrate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class reviewThanks extends AppCompatActivity {

    private Button gotIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_thanks);
        gotIt=(Button)findViewById(R.id.got_it);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(reviewThanks.this,MainActivity.class));
                finish();
            }
        });
    }
    public void onBackPressed() {
        startActivity(new Intent(reviewThanks.this,MainActivity.class));
        finish();
        // your code.
    }
}
