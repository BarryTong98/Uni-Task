package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16;


import comp5216.sydney.edu.au.firebaseapp.activity.activity_13_14_15.DiscussionListActivity_13;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16.RecycleAdapter_ass_10_16;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16.RecycleAdapter_mem_10_16;
import comp5216.sydney.edu.au.firebaseapp.classtype.*;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Activity_10 extends AppCompatActivity {
    private Group group;
    private RecyclerView assignmentRecyclerView;
    private RecyclerView memberRecyclerView;
    private List<Assignment> assignmentList;
    private List<User> userList;
    private ArrayList<Discussion> discussionList;
    private FirebaseFirestore firebaseFirestore;
    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        group = (Group) i.getSerializableExtra("Group");
        if (group != null) {
            groupID = group.getGroupId();
            display();
        }

    }



    private void display() {
        assignmentList = group.getAssignmentList();
        userList = group.getUserList();
        assignmentRecyclerView = findViewById(R.id.assignmentView);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));

        if (assignmentList!=null) {
            RecycleAdapter_ass_10_16 recycleAdapter_ass_10 = new RecycleAdapter_ass_10_16(this, assignmentList, "activity10");
            assignmentRecyclerView.setHasFixedSize(true);
            assignmentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            assignmentRecyclerView.setAdapter(recycleAdapter_ass_10);
            assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            assignmentRecyclerView.addItemDecoration(divider);
        }
        memberRecyclerView = findViewById(R.id.groupMemberView);
        if (userList!=null) {
            RecycleAdapter_mem_10_16 recycleAdapter_mem_10 = new RecycleAdapter_mem_10_16(this, userList, "activity10");
            memberRecyclerView.setHasFixedSize(true);
            memberRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            memberRecyclerView.setAdapter(recycleAdapter_mem_10);
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            memberRecyclerView.addItemDecoration(divider);

        }


    }

    public void onDiscussionListClick(View view) {
        Intent intent=new Intent(Activity_10.this, DiscussionListActivity_13.class);
        if (intent != null) {
            intent.putExtra("groupID", groupID);
        }
        startActivity(intent);
    }


}
