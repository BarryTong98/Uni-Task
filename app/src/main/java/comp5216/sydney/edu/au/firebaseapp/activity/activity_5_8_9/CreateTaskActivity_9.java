package comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12.UserAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.IdUtil;

public class CreateTaskActivity_9 extends AppCompatActivity {
    private TextView taskName;
    private TextView description;
    private Button add;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;

    private ArrayList<User> selectedUser;
    private String dateVal;
    private String assignmentId;
    private String groupName;
    private String groupId;
    private Tasks taskFromAssignment;
    private String taskId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_create_task);
        //Basic setup with component and layout
        recyclerView = findViewById(R.id.recyclerview);
        add = findViewById(R.id.create_task);
        taskName = findViewById(R.id.taskName);
        description = findViewById(R.id.taskDescription);
        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        //initial taskName and description
        //get the value from the previous activity
        taskFromAssignment = (Tasks) intent.getSerializableExtra("task");
        position = intent.getIntExtra("position", 0);
        userList = (ArrayList<User>) intent.getSerializableExtra("userList");
        dateVal = intent.getStringExtra("dateVal");
        assignmentId = intent.getStringExtra("id");
        groupName = intent.getStringExtra("groupName");
        groupId = intent.getStringExtra("groupId");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreateTaskActivity_9.this));
        taskId = IdUtil.getUUID("T");
        selectedUser = new ArrayList<>();

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(divider);

        //Determine the source of the userList, TaskListAdapter or CreateAssignmentActivity
        //if we click the assignment from list and transfer to this activity
        if (taskFromAssignment != null) {
            userList = taskFromAssignment.getUserList();
            taskName.setText(taskFromAssignment.getTaskName());
            description.setText(taskFromAssignment.getDescription());
            taskId = taskFromAssignment.getTaskId();
        }

        //create our adapter
        userAdapter = new UserAdapter(CreateTaskActivity_9.this, userList);
        recyclerView.setAdapter(userAdapter);
        setButtonListeners();

    }

    //create the task and upload the task to firebase
    private void setButtonListeners() {
        add.setOnClickListener(view -> {

            //add selected user
            for (User i : userList) {
                if (i.getStatus() == 1) {
                    selectedUser.add(i);
                }
            }

            String task_name = taskName.getText().toString();
            String des = description.getText().toString();
            Intent intent = new Intent(CreateTaskActivity_9.this, CreateAssignmentActivity_8.class);

            //if the task name or description is empty, we are not allow this operation
            if (task_name.isEmpty() || des.isEmpty()) {
                Toast.makeText(CreateTaskActivity_9.this, "Empty task or Empty description are not Allowed", Toast.LENGTH_SHORT).show();
            }

            //if we click the assignment from list and transfer to this activity
            //we will set some default value according to the previous assignment object
            if (taskFromAssignment != null) {
                DocumentReference dr = firestore.collection("tasks").document(taskFromAssignment.getTaskId());
                dr.update("taskName", task_name);
                dr.update("description", des);
                dr.update("selectedList", selectedUser);
                dr.get().addOnSuccessListener(documentSnapshot -> {
                    Tasks t = documentSnapshot.toObject(Tasks.class);
                    taskFromAssignment = t;
                    intent.putExtra("task", taskFromAssignment);
                    intent.putExtra("position", 0);
                    Toast.makeText(CreateTaskActivity_9.this, "Task Updated", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent);
                    finish();
                });

            }
            //upload the task to firebase
            else {
                Tasks uploadTask = new Tasks(taskId, assignmentId, groupId, task_name, des, dateVal, groupName, userList, selectedUser);
                firestore.collection("tasks").document(uploadTask.getTaskId()).set(uploadTask);
                Toast.makeText(CreateTaskActivity_9.this, "Task Saved", Toast.LENGTH_SHORT).show();
                intent.putExtra("task", uploadTask);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}