package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11;


import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11.RecycleAdapter_ass_10;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11.RecycleAdapter_mem_10;
import comp5216.sydney.edu.au.firebaseapp.classtype.*;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        } else {
            if (savedInstanceState != null) {
                group = (Group) savedInstanceState.getSerializable("groupSave");
                display();
            }
        }
        discussionList = new ArrayList<>();
        getDiscussions(discussionList,groupID);
    }

    private void getDiscussions(ArrayList<Discussion> list,String groupID) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("discussions")
                .whereEqualTo("groupID", 1)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            System.out.println("333333");
                            for(QueryDocumentSnapshot document:task.getResult()){
                                Discussion discussion=document.toObject(Discussion.class);
                                list.add(discussion);
                                System.out.println("2222"+discussion.toString());
                            }
                        }
                    }
                });
        System.out.println("list.size()"+list.size());
    }


    private void display() {
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
//        Intent intent=new Intent(Activity_10.this,DiscussionListActivity.class);
//        if (intent != null) {
//            intent.putExtra("discussions", discussionList);
//            intent.putExtra("groupID", 1);
//        }
//        startActivity(intent);
//        Toast.makeText(this, "Go to Discussion List", Toast.LENGTH_SHORT).show();
    }


}
