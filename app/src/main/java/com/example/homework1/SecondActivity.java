package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private LinearLayout linearLayout;
    private ScrollView scrollView;
    private TextView textView_title;
    private Button button_generate;
    private String libs;
    private String title;
    private JSONArray blanks;
    private JSONArray story;
    private ArrayList<String> userInput = new ArrayList<>();
    private ArrayList<EditText> allEds = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        constraintLayout = findViewById(R.id.second_root_layout);
        linearLayout = findViewById(R.id.linear_second);
        scrollView = findViewById(R.id.scroll);
        textView_title = findViewById(R.id.textView_title);
        button_generate = findViewById(R.id.button_generate);

        Intent intent = getIntent();
        libs = intent.getStringExtra("libs");
        Log.d("Data from Main Activity", libs);

        try {
            JSONObject libs_json = new JSONObject(libs);
            title = libs_json.getString("title");
            Log.d("title", title);
            textView_title.setText(title);

            blanks = libs_json.getJSONArray("blanks");
            // Log.d("blanks", blanks.getString(0));
            story = libs_json.getJSONArray("value");
            setBlanks();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button_generate.setOnClickListener(v -> {
            launchNextActivity(v);
        });

    }

    public void setBlanks() throws JSONException {
        for (int i = 0; i < blanks.length(); i++){
            TextView textView = new TextView(this);
            textView.setText(blanks.getString(i));
            EditText editText = new EditText(this);
            linearLayout.addView(textView);
            linearLayout.addView(editText);
            allEds.add(editText);
        }
    }

    public void launchNextActivity(View view){
        Boolean isFull = true;
        Toast toast = Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT);
        for(int i = 0; i < allEds.size(); i++){
            if (!allEds.get(i).getText().toString().equals("")){
                userInput.add(allEds.get(i).getText().toString().trim());
            }
        }
        Log.d("userInput", userInput.size() + "");
        if(userInput.size() < allEds.size()){
            toast.show();
            userInput.clear();
        }
        else{
            Intent intent = new Intent(this, ThirdActivity.class);
            String story_message = story.toString();
            intent.putExtra("story", story_message);
            Log.d("story", story_message);
            StringBuffer sb = new StringBuffer();
            for (String s : userInput){
                sb.append(s);
                sb.append("&&");
            }
            String input_message = sb.toString();
            intent.putExtra("input", input_message);
            Log.d("input", input_message);
            startActivity(intent);
        }
        // Log.d("user", userInput.get(0));
    }
}
