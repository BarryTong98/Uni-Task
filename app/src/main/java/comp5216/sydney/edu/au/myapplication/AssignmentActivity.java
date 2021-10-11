package comp5216.sydney.edu.au.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.myapplication.Adapter.AssignmentAdapter;
import comp5216.sydney.edu.au.myapplication.Adapter.TaskAdapter;
import comp5216.sydney.edu.au.myapplication.Model.AssignmentModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskOfAssignmentModel;

public class AssignmentActivity extends AppCompatActivity {
    private Button home;
    private Button task;
    private Button profile;
    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private List<AssignmentModel> mList;
    private List<TaskModel> list;
    private List<TaskModel> list1;
    private List<TaskModel> list2;
    private List<TaskOfAssignmentModel> taskList;
    private FirebaseFirestore firestore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        recyclerView = findViewById(R.id.recyclerview);
        home = findViewById(R.id.home);
        task = findViewById(R.id.task);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerview);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentActivity.this));
        mList = new ArrayList<>();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        taskList = new ArrayList<>();

        TaskModel t1 = new TaskModel("Barry", "IT", "Java,Vue", 0);
        TaskModel t2 = new TaskModel("Shela", "CV Engineering", "None", 0);
        TaskModel t3 = new TaskModel("Tim", "IT", "C++, Web Develop", 0);
        TaskModel t4 = new TaskModel("Sam", "IT", "C++, Java", 0);
        list.add(t1);
        list1.add(t1);
        list2.add(t1);
        list1.add(t2);
        list2.add(t3);
        TaskOfAssignmentModel task1 = new TaskOfAssignmentModel("App Part 5","Develop UniTask Part 5 From Proposal",list,"COMP5216 Group 18","1/11/2021",0);
        TaskOfAssignmentModel task2 = new TaskOfAssignmentModel("App Part 8","Develop UniTask Part 8 From Proposal",list1,"COMP9103","2/11/2021",0);
        TaskOfAssignmentModel task3 = new TaskOfAssignmentModel("App Part 9","Develop UniTask Part 9 From Proposal",list2,"COMP9120","3/11/2021",0);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        AssignmentModel a1 = new AssignmentModel("COMP5216 Group 18","Build UniTask","13/7/2021",taskList,0);
        AssignmentModel a2 = new AssignmentModel("COMP9103","Java Basic","14/8/2021",taskList,0);
        AssignmentModel a3 = new AssignmentModel("COMP9120","SQL Introduction","14/8/2021",taskList,0);
        mList.add(a1);
        mList.add(a2);
        mList.add(a3);



        adapter = new AssignmentAdapter(taskList,AssignmentActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public void toHome(View view){
        Intent intent = new Intent( AssignmentActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toTask(View view){
        Intent intent = new Intent(AssignmentActivity.this, CreateAssignmentActivity.class);
        startActivity(intent);
    }
}