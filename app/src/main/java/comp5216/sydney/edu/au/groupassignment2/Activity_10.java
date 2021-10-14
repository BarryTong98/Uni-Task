package comp5216.sydney.edu.au.groupassignment2;

import comp5216.sydney.edu.au.groupassignment2.adapter.*;
import comp5216.sydney.edu.au.groupassignment2.classtype.Assignment;
import comp5216.sydney.edu.au.groupassignment2.classtype.Group;
import comp5216.sydney.edu.au.groupassignment2.classtype.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class Activity_10 extends AppCompatActivity {
    private Group group;
    private RecyclerView assignmentRecyclerView;
    private RecyclerView memberRecyclerView;
    private List<Assignment> assignmentList;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        group = (Group) i.getSerializableExtra("Group");
        if (group != null) {

           display();
        }else {
            if (savedInstanceState!=null){
                group = (Group) savedInstanceState.getSerializable("groupSave");
                display();
            }
        }
    }


    private void display(){

        assignmentList = group.getAssignmentList();
        userList = group.getUserList();
        assignmentRecyclerView = findViewById(R.id.assignmentView);
        RecycleAdapter_ass_10 recycleAdapter_ass_10 = new RecycleAdapter_ass_10(this, assignmentList);
        assignmentRecyclerView.setHasFixedSize(true);
        assignmentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        assignmentRecyclerView.setAdapter(recycleAdapter_ass_10);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        memberRecyclerView = findViewById(R.id.groupMemberView);
        RecycleAdapter_mem_10 recycleAdapter_mem_10 = new RecycleAdapter_mem_10(this, userList);
        memberRecyclerView.setHasFixedSize(true);
        memberRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        memberRecyclerView.setAdapter(recycleAdapter_mem_10);
        memberRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onAddAssignmentClick(View view) {
//        Intent intent=new Intent(Activity_10.this,Activity_8.class)
//        startActivity(intent);
        Toast.makeText(this, "Add Assignment Intent", Toast.LENGTH_SHORT).show();
    }

    public void onAddMemberClick(View view) {
        //        Intent intent=new Intent(Activity_10.this,Activity_8.class)
//        startActivity(intent);
        Toast.makeText(this, "Add Member Intent", Toast.LENGTH_SHORT).show();
    }

    public void onDiscussionListClick(View view) {
        //        Intent intent=new Intent(Activity_10.this,Activity_8.class)
//        startActivity(intent);
        Toast.makeText(this, "Go to Discussion List", Toast.LENGTH_SHORT).show();
    }


}