package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11;

import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11.RecycleAdapter_task_11;
import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.*;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Activity_11 extends AppCompatActivity {
    private Assignment assignment;
    private RecyclerView taskRecyclerView;
    private TextView assignmentId,assignmentBrief,assignmentDue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_11);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        assignment = (Assignment) i.getSerializableExtra("Assignment");

        if (assignment != null) {
            display();
        }
    }

    private void display() {
        assignmentId=findViewById(R.id.assignemntName_11);
        assignmentBrief=findViewById(R.id.assignmentBrief_11);
        assignmentDue=findViewById(R.id.assignmentDue_11);

        assignmentId.setText(assignment.getAssignmentId());
        assignmentBrief.setText(assignment.getDescription());
        assignmentDue.setText(assignment.getDueDate());


        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        RecycleAdapter_task_11 recycleAdapter_task_11 = new RecycleAdapter_task_11(this, assignment);
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        taskRecyclerView.setAdapter(recycleAdapter_task_11);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}


