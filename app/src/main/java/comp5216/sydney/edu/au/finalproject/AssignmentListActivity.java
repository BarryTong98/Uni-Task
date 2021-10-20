package comp5216.sydney.edu.au.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.finalproject.adapter.ShowAssignmentAdapter;
import comp5216.sydney.edu.au.finalproject.model.Assignment;
import comp5216.sydney.edu.au.finalproject.model.Group;
import comp5216.sydney.edu.au.finalproject.model.User;

public class AssignmentListActivity extends AppCompatActivity {
    private ImageButton addAssignment;
    private RecyclerView assignmentRV;
    private ArrayList<Assignment> assignmentList;
    private Group group;
    private ShowAssignmentAdapter showAssignmentAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        addAssignment = findViewById(R.id.btnAddAssignment);
        assignmentRV = findViewById(R.id.assignment_list);

        buildRecycleView();
    }

    private void buildRecycleView() {
        assignmentList = new ArrayList<>();
        showAssignmentAdapter = new ShowAssignmentAdapter(assignmentList, AssignmentListActivity.this);
        assignmentRV.setHasFixedSize(true);
        assignmentRV.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        db.collection("assignments").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d: list) {
                            Assignment a = d.toObject(Assignment.class);
                            assignmentList.add(a);
                            System.out.println(assignmentList.size());
                        }
                        showAssignmentAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AssignmentListActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(AssignmentListActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show());

        assignmentRV.setAdapter(showAssignmentAdapter);
    }
}