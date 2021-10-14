package comp5216.sydney.edu.au.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.myapplication.Adapter.TaskAdapter;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.model1.Task;
import comp5216.sydney.edu.au.myapplication.model1.User;
import comp5216.sydney.edu.au.myapplication.util.IdUtil;

public class CreateAssignmentActivity extends AppCompatActivity {
    private TextView assignmentName;
    private TextView description;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RecyclerView taskView;
    private List<Task> taskList;
    private List<TaskModel> memberList;
    private List<User> userList;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_assignment);
        assignmentName = findViewById(R.id.assignmentName);
        description = findViewById(R.id.assignmentDes);
        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);
        taskView = findViewById(R.id.tasks);


        taskView.setHasFixedSize(true);
        taskView.setLayoutManager(new LinearLayoutManager(CreateAssignmentActivity.this));
        taskList = new ArrayList<>();
        memberList = new ArrayList<>();
        userList = new ArrayList<>();
        User u1 = new User(IdUtil.getUUID("U"),"Barry", "IT", "Java,Vue");
        User u2 = new User(IdUtil.getUUID("U"),"Shela", "CV Engineering", "None");
        User u3 = new User(IdUtil.getUUID("U"),"Tim", "IT", "C++, Web Develop");
        User u4 = new User(IdUtil.getUUID("U"),"Sam", "IT", "C++, Java");
        User u5 = new User(IdUtil.getUUID("U"),"Rickey", "IT", "React,Sprintboot");
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5);
        String Assignment1Id = IdUtil.getUUID("a");
        Task task1 = new Task(IdUtil.getUUID("T"), "Task1", "Develop UniTask Part 5 From Proposal", Assignment1Id, "COMP5216 Group 18", "1/11/2021", userList, 0);
        Task task2 = new Task(IdUtil.getUUID("T"), "Task2", "Develop UniTask Part 8 From Proposal", Assignment1Id, "COMP5216 Group 18", "2/11/2021", userList, 0);
        Task task3 = new Task(IdUtil.getUUID("T"), "Task3", "Develop UniTask Part 9 From Proposal", Assignment1Id, "COMP5216 Group 18", "3/11/2021", userList, 0);
        Task task4 = new Task(IdUtil.getUUID("T"), "Task4", "Develop UniTask Part 0 From Proposal", Assignment1Id, "COMP5216 Group 18", "4/11/2021", userList, 0);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        adapter = new TaskAdapter( taskList,CreateAssignmentActivity.this);
        taskView.setAdapter(adapter);
    }

    public void toTaskPage(View view){
        Intent intent = new Intent(CreateAssignmentActivity.this, MainActivity.class);
        startActivity(intent);
    }
}