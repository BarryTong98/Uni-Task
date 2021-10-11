package comp5216.sydney.edu.au.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.myapplication.Adapter.TaskAdapter;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskOfAssignmentModel;

public class MainActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView description;
    private Button add;
    private Button to;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private TaskAdapter adapter;
    private List<TaskModel> mList;
    private List<TaskOfAssignmentModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        recyclerView = findViewById(R.id.recyclerview);
        add = findViewById(R.id.create_task);
        taskName = findViewById(R.id.taskName);
        description = findViewById(R.id.taskDescription);
        firestore = FirebaseFirestore.getInstance();
        to = findViewById(R.id.to);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mList = new ArrayList<>();
        taskList = new ArrayList<>();
        TaskModel t1 = new TaskModel("Barry", "IT", "Java,Vue", 0);
        TaskModel t2 = new TaskModel("Shela", "CV Engineering", "None", 0);
        TaskModel t3 = new TaskModel("Tim", "IT", "C++, Web Develop", 0);
        TaskModel t4 = new TaskModel("Sam", "IT", "C++, Java", 0);
        TaskModel t5 = new TaskModel("Rickey", "IT", "React,Sprintboot", 0);
        mList.add(t1);
        mList.add(t2);
        mList.add(t3);
        mList.add(t4);
        mList.add(t5);

        TaskOfAssignmentModel task1 = new TaskOfAssignmentModel("App Part 5","Develop UniTask Part 5 From Proposal",mList,"COMP5216 Group 18","1/11/2021",0);
        TaskOfAssignmentModel task2 = new TaskOfAssignmentModel("App Part 8","Develop UniTask Part 8 From Proposal",mList,"COMP5216 Group 18","2/11/2021",0);
        TaskOfAssignmentModel task3 = new TaskOfAssignmentModel("App Part 9","Develop UniTask Part 9 From Proposal",mList,"COMP5216 Group 18","3/11/2021",0);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        adapter = new TaskAdapter(MainActivity.this, mList);
        recyclerView.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskName.getText().toString();
                String des = description.getText().toString();
                if (task.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty task not Allowed", Toast.LENGTH_SHORT).show();
                } else if (des.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty description not Allowed", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("task", taskList);
//                    taskMap.put("description", des);
//                    taskMap.put("status", 0);
//                    taskMap.put("Group Member",mList);

                    firestore.collection("task").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Task Saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //showDate();
    }

    public void trans(View view){
        Intent intent = new Intent(MainActivity.this, AssignmentActivity.class);
        startActivity(intent);
    }


    private void showDate() {
        firestore.collection("task").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        String id = documentChange.getDocument().getId();
                        TaskModel taskModel = documentChange.getDocument().toObject(TaskModel.class).withId(id);
                        mList.add(taskModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                Collections.reverse(mList);
            }
        });
    }
}