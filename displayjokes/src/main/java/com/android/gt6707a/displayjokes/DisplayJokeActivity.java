package com.android.gt6707a.displayjokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        jokeTextView = findViewById(R.id.joke_textview);

        Intent intent = getIntent();
        String joke = intent.getStringExtra("joke");

        jokeTextView.setText(joke);
    }
}
