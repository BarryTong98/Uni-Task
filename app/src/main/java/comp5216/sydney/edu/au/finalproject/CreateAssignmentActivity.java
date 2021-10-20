package comp5216.sydney.edu.au.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.finalproject.model.Group;
import comp5216.sydney.edu.au.finalproject.model.Task;
import comp5216.sydney.edu.au.finalproject.adapter.ShowTaskAdapter;
import comp5216.sydney.edu.au.finalproject.model.Assignment;
import comp5216.sydney.edu.au.finalproject.model.User;
import comp5216.sydney.edu.au.finalproject.utils.IdUtil;

public class CreateAssignmentActivity extends AppCompatActivity {
    private TextView assignmentName;
    private TextView description;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RecyclerView taskView;
    private Button create_assignment;
    private Button create_task;
    private FirebaseFirestore firestore;
    private Group group;
    private ShowTaskAdapter adapter;
    private ArrayList<Task> taskList;
    private ArrayList<Assignment> assignmentList;
    private ArrayList<User> userList;
    private String assignmentId;
    private String dateVal;
    private String groupId;
    private String groupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_assignment);
        assignmentName = findViewById(R.id.assignmentName);
        description = findViewById(R.id.assignmentDes);
        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);
        taskView = findViewById(R.id.tasks);
        create_assignment = findViewById(R.id.create_assignment);
        create_task = findViewById(R.id.add_task);

        //这边创建assignment页面的时候，需要getExtra从 CreateGroupPage中获取
        Intent intent = this.getIntent();
        group = (Group)intent.getSerializableExtra("group");
        userList = group.getUserList();
        assignmentList = group.getAssignmentList();
        groupName = group.getName();
        taskList = new ArrayList<>();

        firestore = FirebaseFirestore.getInstance();
        taskView.setHasFixedSize(true);
        taskView.setLayoutManager(new LinearLayoutManager(CreateAssignmentActivity.this));
        showData();


        adapter = new ShowTaskAdapter(taskList, CreateAssignmentActivity.this);
        taskView.setAdapter(adapter);

        setButtonListeners();
    }

    private void setButtonListeners() {

        create_assignment.setOnClickListener(view -> {
            dateVal = formatDate();
            assignmentId = IdUtil.getUUID("a");
            String AssignmentName = assignmentName.getText().toString();
            System.out.println("Assignment:     "+AssignmentName);
            String Des = description.getText().toString();
            System.out.println("DES!!!!!!!!!!"+Des);
            System.out.println("CREATE TASK 2 RUNNING");
            if (AssignmentName.isEmpty()) {
                System.out.println("AssignmentName: "+AssignmentName);
                Toast.makeText(CreateAssignmentActivity.this, "Empty task not Allowed", Toast.LENGTH_SHORT).show();
            } else if (Des.isEmpty()) {
                Toast.makeText(CreateAssignmentActivity.this, "Empty description not Allowed", Toast.LENGTH_SHORT).show();
            } else {
                Assignment single = new Assignment(assignmentId, AssignmentName, Des, dateVal, groupId, 0, taskList);
                firestore.collection("assignments").document(single.getAssignmentId()).set(single);
                Toast.makeText(CreateAssignmentActivity.this, "Assignment Saved", Toast.LENGTH_SHORT).show();

                assignmentList.add(single);
                group.setAssignmentList(assignmentList);

                Intent intent = new Intent(CreateAssignmentActivity.this, CreateGroupActivity.class);
                intent.putExtra("group", group);
                intent.putExtra("assignment", single);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        create_task.setOnClickListener(view -> {
            dateVal = formatDate();
            Intent intent = new Intent(CreateAssignmentActivity.this, CreateTaskActivity.class);
            intent.putExtra("userList", userList);
            intent.putExtra("dateVal", dateVal);
            intent.putExtra("id", assignmentId);
            intent.putExtra("groupName", groupName);
            intent.putExtra("taskList", taskList);
            startActivity(intent);
        });

    }

    private String formatDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String date = String.format("%d-%d-%d %d:%d", year, month, day, hour, minute);
        return date;
    }

    private void showData() {
        firestore.collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        //String id = documentChange.getDocument().getId();
                        Task taskModel = documentChange.getDocument().toObject(Task.class);
                        taskList.add(taskModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                //Collections.reverse(taskList2);
            }
        });
    }
}