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
import comp5216.sydney.edu.au.myapplication.Adapter.TaskOfAssignmentAdapter;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskOfAssignmentModel;

public class CreateAssignmentActivity extends AppCompatActivity {
    private TextView assignmentName;
    private TextView description;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RecyclerView taskView;
    private List<TaskOfAssignmentModel> taskList;
    private List<TaskModel> memberList;
    private TaskOfAssignmentAdapter adapter;

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
        TaskModel t1 = new TaskModel("Barry", "IT", "Java,Vue", 0);
        TaskModel t2 = new TaskModel("Shela", "CV Engineering", "None", 0);
        TaskModel t3 = new TaskModel("Tim", "IT", "C++, Web Develop", 0);
        TaskModel t4 = new TaskModel("Sam", "IT", "C++, Java", 0);
        TaskModel t5 = new TaskModel("Rickey", "IT", "React,Sprintboot", 0);
        memberList.add(t1);
        memberList.add(t2);
        TaskOfAssignmentModel task1 = new TaskOfAssignmentModel("Task 1","Develop UniTask Part 5 From Proposal",memberList,"COMP5216 Group 18","1/11/2021",0);
        TaskOfAssignmentModel task2 = new TaskOfAssignmentModel("Task 2","Develop UniTask Part 8 From Proposal",memberList,"COMP5216 Group 18","2/11/2021",0);
        TaskOfAssignmentModel task3 = new TaskOfAssignmentModel("Task 3","Develop UniTask Part 9 From Proposal",memberList,"COMP5216 Group 18","3/11/2021",0);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        adapter = new TaskOfAssignmentAdapter( taskList,CreateAssignmentActivity.this);
        taskView.setAdapter(adapter);
    }

    public void toTaskPage(View view){
        Intent intent = new Intent(CreateAssignmentActivity.this, MainActivity.class);
        startActivity(intent);
    }
}