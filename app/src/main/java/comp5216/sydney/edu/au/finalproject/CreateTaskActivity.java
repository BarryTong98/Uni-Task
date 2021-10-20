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
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private UserAdapter adapter;
    private ArrayList<User> userList;
    private ArrayList<Task> taskList;
    private ArrayList<User> selectedUser = new ArrayList<>();
    private String dateVal;
    private String assignmentId;
    private String groupName;
    private Task taskFromAssignment;
    private String taskId;

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

        Intent intent = getIntent();
        //initial taskName and description

        taskFromAssignment =(Task)intent.getSerializableExtra("task");
        if(taskFromAssignment != null){
            taskName.setText(taskFromAssignment.getTaskName());
            description.setText(taskFromAssignment.getDescription());
        }

        userList = (ArrayList<User>) intent.getSerializableExtra("userList");
        taskList = (ArrayList<Task>) intent.getSerializableExtra("taskList");
        dateVal = intent.getStringExtra("dateVal");
        assignmentId = intent.getStringExtra("id");
        groupName = intent.getStringExtra("groupName");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreateTaskActivity.this));

        adapter = new UserAdapter(CreateTaskActivity.this, userList);
        recyclerView.setAdapter(adapter);
        setButtonListeners();

        //showDate();
    }

    private void setButtonListeners(){
        add.setOnClickListener(view -> {

            //add selected user
            for(User i: userList){
                if(i.getStatus() == 1){
                    selectedUser.add(i);
                }
            }

            if(taskFromAssignment != null){
                taskId = taskFromAssignment.getTaskId();
            } else {
                taskId = IdUtil.getUUID("T");
            }

            String task_name = taskName.getText().toString();
            String des = description.getText().toString();
            Task uploadTask = new Task(taskId, task_name, des, assignmentId, groupName, dateVal, selectedUser, 0);

//            ArrayList<Task> list;
//            for(User i : selectedUser){
//                list = i.getTaskList();
//                list.add(uploadTask);
//                i.setTaskList(list);
//                list.clear();
//            }

            if (task_name.isEmpty()) {
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
            intent.putExtra("task", uploadTask);
            startActivity(intent);
        });
    }
}