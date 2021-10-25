package comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9;


import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

        recyclerView = findViewById(R.id.recyclerview);
        add = findViewById(R.id.create_task);
        taskName = findViewById(R.id.taskName);
        description = findViewById(R.id.taskDescription);
        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        //initial taskName and description
        taskFromAssignment =(Tasks)intent.getSerializableExtra("task");
        position = intent.getIntExtra("position",0);
        userList = (ArrayList<User>) intent.getSerializableExtra("userList");
        dateVal = intent.getStringExtra("dateVal");
        assignmentId = intent.getStringExtra("id");
        groupName = intent.getStringExtra("groupName");
        groupId = intent.getStringExtra("groupId");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreateTaskActivity_9.this));
        taskId = IdUtil.getUUID("T");
        selectedUser = new ArrayList<>();
        //showUser();
        //判断userList的来源， TaskListAdapter 还是 CreateAssignmentActivity
        if(taskFromAssignment != null){
            System.out.println("NOT NULL");
            System.out.println(taskFromAssignment);
            userList = taskFromAssignment.getUserList();
            taskName.setText(taskFromAssignment.getTaskName());
            description.setText(taskFromAssignment.getDescription());
            taskId = taskFromAssignment.getTaskId();
        }

        userAdapter = new UserAdapter(CreateTaskActivity_9.this, userList);
        recyclerView.setAdapter(userAdapter);

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

            String task_name = taskName.getText().toString();
            String des = description.getText().toString();
            Intent intent = new Intent(CreateTaskActivity_9.this, CreateAssignmentActivity_8.class);

            if (task_name.isEmpty() || des.isEmpty()) {
                Toast.makeText(CreateTaskActivity_9.this, "Empty task or Empty description are not Allowed", Toast.LENGTH_SHORT).show();
            }

            if(taskFromAssignment != null) {
               DocumentReference dr =  firestore.collection("tasks").document(taskFromAssignment.getTaskId());
               dr.update("taskName", task_name);
               dr.update("description", des);
               dr.update("selectedList", selectedUser);

               dr.get().addOnSuccessListener(documentSnapshot -> {
                   Tasks t = documentSnapshot.toObject(Tasks.class);
                   System.out.println("-----   " + t.getTaskName() + "   " + t.getDescription());
                   taskFromAssignment = t;
                   intent.putExtra("task", taskFromAssignment);
                   intent.putExtra("position", 0);
                   Toast.makeText(CreateTaskActivity_9.this, "Task Updated", Toast.LENGTH_SHORT).show();
                   setResult(RESULT_OK, intent);
                   finish();
               });

            } else {
                System.out.println("GROUP NAME ************"+ groupName);
                Tasks uploadTask = new Tasks(taskId, assignmentId, groupId, task_name, des,dateVal, groupName, userList, selectedUser);
                firestore.collection("tasks").document(uploadTask.getTaskId()).set(uploadTask);
                Toast.makeText(CreateTaskActivity_9.this, "Task Saved", Toast.LENGTH_SHORT).show();
                intent.putExtra("task", uploadTask);
                setResult(RESULT_OK, intent);
                finish();
            }



        });
    }

//    public void showUser(){
//        firestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                for (DocumentChange documentChange : value.getDocumentChanges()) {
//                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
//                        User user = documentChange.getDocument().toObject(User.class);
//                        userList.add(user);
//                        userAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
//    }
}