package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;

public class ThirdActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView textView_story;
    private String[] input;
    private String lib_final;
    private Button goHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        constraintLayout = findViewById(R.id.constraint_third);
        textView_story = findViewById(R.id.textView_final);
        goHome = findViewById(R.id.button_goHome);

        Intent intent = getIntent();
        try {
            JSONArray story = new JSONArray(intent.getStringExtra("story"));
            input = intent.getStringExtra("input").split("&&");
            lib_final = "";

            for(int i = 0; i < story.length()-2; i++){
                lib_final = lib_final + story.getString(i) + input[i];
            }
            lib_final = lib_final + story.getString(story.length()-2);
            Log.d("final", lib_final);
            textView_story.setText(lib_final);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        goHome.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        });
    }
}
