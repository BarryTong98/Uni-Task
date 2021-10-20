package comp5216.sydney.edu.au.firebaseapp.activity.activity_13_14_15;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_13_14_15.DiscussionAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Comment;
import comp5216.sydney.edu.au.firebaseapp.classtype.Discussion;
import comp5216.sydney.edu.au.firebaseapp.util.IdUtil;
import comp5216.sydney.edu.au.firebaseapp.R;

public class DiscussionListActivity extends AppCompatActivity {

    private static final String TAG = "Final Project";
    private String groupID;
    private ListView discussionListView;
    private Button btnCreate;
    private DiscussionAdapter adapter;

    private FirebaseFirestore firebaseFirestore;

    private ArrayList<Discussion> discussions;

    FirebaseAuth mAuth;
    private String userID;
    private String userName;


    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        userID=firebaseUser.getUid();
        userName=firebaseUser.getDisplayName();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_list);
        discussionListView = findViewById(R.id.discussionList);
        btnCreate = findViewById(R.id.btnCreate);
        Bundle bundle = getIntent().getExtras();

        discussions=new ArrayList<>();
        //discussions = (ArrayList<Discussion>) getIntent().getSerializableExtra("discussions");
        groupID = getIntent().getStringExtra("groupID");
        initFirestore();
        adapter = new DiscussionAdapter(discussions, DiscussionListActivity.this);
        discussionListView.setAdapter(adapter);
        setupListener();
    }

    private void initFirestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("discussions")
                .whereEqualTo("groupID", groupID)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Discussion discussion = document.toObject(Discussion.class);
                                discussions.add(discussion);
                            }
                            adapter = new DiscussionAdapter(discussions, DiscussionListActivity.this);
                            discussionListView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void clickCreate(View view) {

        Intent intent = new Intent(DiscussionListActivity.this, CreateDiscussionActivity.class);
        if (intent != null) {
            intent.putExtra("groupID", groupID);
            intent.putExtra("userID", userID);
            intent.putExtra("userName",userName);
            intent.putExtra("assignmentID", 1);
        }
        resultLauncher.launch(intent);
    }

    private void setupListener() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                String title = result.getData().getExtras().getString("title");
                String description = result.getData().getExtras().getString("description");
                List<Comment> comments = new ArrayList<>();
                Discussion discussion = new Discussion(IdUtil.getUUID("D"), groupID, userName, 1, title, description, new Date(), comments);
                firebaseFirestore.collection("discussions").document(discussion.getDiscussionID()).set(discussion);
                discussions.add(discussion);
            }
            adapter.notifyDataSetChanged();
        });

        discussionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiscussionListActivity.this, DiscussionActivity.class);
                if (intent != null) {
                    intent.putExtra("userName",userName);
                    intent.putExtra("discussionID", discussions.get(position).getDiscussionID());
                }
                resultLauncher.launch(intent);
            }
        });
    }
}