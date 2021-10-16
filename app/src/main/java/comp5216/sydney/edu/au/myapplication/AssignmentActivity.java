package comp5216.sydney.edu.au.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.myapplication.Adapter.AssignmentAdapter;
import comp5216.sydney.edu.au.myapplication.Model.AssignmentModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskOfAssignmentModel;
import comp5216.sydney.edu.au.myapplication.model1.Task;

public class AssignmentActivity extends AppCompatActivity {
    private Button home;
    private Button task;
    private Button profile;
    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private List<Task> taskFromFireBase;
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
        taskFromFireBase = new ArrayList<>();
        showData();


        adapter = new AssignmentAdapter(taskFromFireBase, AssignmentActivity.this);
        recyclerView.setAdapter(adapter);
    }

    public void toHome(View view) {
        Intent intent = new Intent(AssignmentActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void toTask(View view) {
        Intent intent = new Intent(AssignmentActivity.this, CreateAssignmentActivity.class);
        startActivity(intent);
    }

    private void showData() {
        firestore.collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        Task taskModel = documentChange.getDocument().toObject(Task.class);
                        taskFromFireBase.add(taskModel);
                        adapter.notifyDataSetChanged();
                    }
                }
                //Collections.reverse(taskList2);
            }
        });
    }
}