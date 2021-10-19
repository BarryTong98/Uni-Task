package comp5216.sydney.edu.au.finalproject;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.finalproject.adapter.UserAdapter;
import comp5216.sydney.edu.au.finalproject.barryModel.TaskModel;
import comp5216.sydney.edu.au.finalproject.barryModel.TaskOfAssignmentModel;
import comp5216.sydney.edu.au.finalproject.model.Group;
import comp5216.sydney.edu.au.finalproject.model.Task;
import comp5216.sydney.edu.au.finalproject.model.User;
import comp5216.sydney.edu.au.finalproject.utils.DateUtil;
import comp5216.sydney.edu.au.finalproject.utils.IdUtil;

public class CreateTaskActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView description;
    private Button add;
    private Button to;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private UserAdapter adapter;
    private List<User> userList;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        recyclerView = findViewById(R.id.recyclerview);
        add = findViewById(R.id.create_task);
        taskName = findViewById(R.id.taskName);
        description = findViewById(R.id.taskDescription);
        firestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        Task taskFromAssignment =(Task)i.getSerializableExtra("task");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreateTaskActivity.this));
        taskList = new ArrayList<>();
        userList = new ArrayList<>();

        User u1 = new User(IdUtil.getUUID("U"), "Barry", "IT", "Java,Vue");
        User u2 = new User(IdUtil.getUUID("U"), "Shela", "CV Engineering", "Python");
        User u3 = new User(IdUtil.getUUID("U"), "Tim", "IT", "C++, Web Develop");
        User u4 = new User(IdUtil.getUUID("U"), "Sam", "IT", "C++, Java");
        User u5 = new User(IdUtil.getUUID("U"), "Rickey", "IT", "React,Sprintboot");
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5);

        String Assignment1Id = IdUtil.getUUID("a");

        adapter = new UserAdapter(CreateTaskActivity.this, userList);
        recyclerView.setAdapter(adapter);
        if(taskFromAssignment != null){
            taskName.setText(taskFromAssignment.getTaskName());
            description.setText(taskFromAssignment.getDescription());
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task taskFromAssignment =(Task)i.getSerializableExtra("task");
                List<User> selectedUser = new ArrayList<>();
                for(User i: userList){
                    if(i.getStatus() == 1){
                        selectedUser.add(i);
                    }
                }
                String Assignment1Id = IdUtil.getUUID("a");
                String date = "1/11/2021";
                String groupName = "COMP5216 Group 18";
                String taskId = IdUtil.getUUID("T");
                if(taskFromAssignment != null){
                    System.out.println("NOT NULL          "+taskFromAssignment.getTaskId());
                    taskId = taskFromAssignment.getTaskId();
                    System.out.println("TASK"+taskId);
                    Assignment1Id = taskFromAssignment.getAssignmentId();
                    groupName = taskFromAssignment.getGroupName();
                    date = taskFromAssignment.getDueDate();
                }
                String task = taskName.getText().toString();
                String des = description.getText().toString();
                Task uploadTask = new Task(taskId, task, des, Assignment1Id, groupName, date, selectedUser, 0);
                List<Task> list = new ArrayList<>();
                for(User i : selectedUser){
                    list = i.getTaskList();
                    list.add(uploadTask);
                    i.setTaskList(list);
                    list.clear();
                }
                if (task.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Empty task not Allowed", Toast.LENGTH_SHORT).show();
                } else if (des.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Empty description not Allowed", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put(uploadTask.getTaskName(), uploadTask);
                    firestore.collection("tasks").document(uploadTask.getTaskId()).set(uploadTask);
                    Toast.makeText(CreateTaskActivity.this, "Task Saved", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(CreateTaskActivity.this, CreateAssignmentActivity.class);
                startActivity(intent);
            }
        });

        //showDate();
    }

//    public void trans(View view) {
//        Intent intent = new Intent(CreateTaskActivity.this, AssignmentActivity.class);
//        startActivity(intent);
//    }
}