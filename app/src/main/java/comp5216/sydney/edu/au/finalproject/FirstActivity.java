package comp5216.sydney.edu.au.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import comp5216.sydney.edu.au.finalproject.model.Group;

public class FirstActivity extends AppCompatActivity {
    private Button btnAdd;
    private Group group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnAdd = findViewById(R.id.group_btn);
        setButtonListeners();
    }

    private void setButtonListeners() {
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(FirstActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });
    }
}