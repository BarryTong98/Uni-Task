package comp5216.sydney.edu.au.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import comp5216.sydney.edu.au.finalproject.adapter.AssignmentAdapter;
import comp5216.sydney.edu.au.finalproject.model.Comment;
import comp5216.sydney.edu.au.finalproject.model.Discussion;

public class CreateDiscussionActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private AssignmentAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnPost;



    private int groupID;
    private int userID;
    private int assignmentID;

    private FirebaseFirestore firebaseFirestore;

    HashMap<Integer,Boolean> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_discussion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        recyclerView = findViewById(R.id.lvAssignment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreateDiscussionActivity.this));

        ArrayList<String> assignments=new ArrayList<>();
        assignments.add("Assignments1");
        assignments.add("Assignments2");
        assignments.add("Assignments3");
        assignments.add("Assignments4");

        map=new HashMap<>();

        adapter=new AssignmentAdapter(assignments,CreateDiscussionActivity.this,map);
        recyclerView.setAdapter(adapter);



        btnPost = findViewById(R.id.btnPost);

        groupID = getIntent().getIntExtra("groupID", -1);
        userID = getIntent().getIntExtra("userID", -1);
        assignmentID = getIntent().getIntExtra("assignmentID", -1);

        System.out.println("groupID: " + groupID);
        System.out.println("userID: " + userID);

        initFirestore();
    }

    private void initFirestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    public void clickPost(View view) {
        System.out.println(map.toString());
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        Intent intent = new Intent(CreateDiscussionActivity.this, DiscussionListActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        setResult(RESULT_OK,intent);
        finish();
    }
}