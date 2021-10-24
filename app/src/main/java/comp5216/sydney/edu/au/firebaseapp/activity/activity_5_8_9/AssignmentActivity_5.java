package comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4.HomeActivity_3;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4.ProfileActivity_4;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_5_8_9.AssignmentAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class AssignmentActivity_5 extends AppCompatActivity {
    private Button home;
    private Button task;
    private Button profile;
    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private List<Tasks> tasksFromFireBase;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_task_page);
        recyclerView = findViewById(R.id.recyclerview);
        home = findViewById(R.id.home);
        task = findViewById(R.id.task);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerview);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentActivity_5.this));
        tasksFromFireBase = new ArrayList<>();
        showData();


        adapter = new AssignmentAdapter(tasksFromFireBase, AssignmentActivity_5.this);
        recyclerView.setAdapter(adapter);
    }

    public void toHome(View view) {
        Intent intent = new Intent(AssignmentActivity_5.this, HomeActivity_3.class);
        startActivity(intent);
    }


    public void toProfile(View view) {
        Intent intent = new Intent(AssignmentActivity_5.this, ProfileActivity_4.class);
        startActivity(intent);
    }

    private void showData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore.collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        Tasks tasksModel = documentChange.getDocument().toObject(Tasks.class);
                        List<User> taskUsers = tasksModel.getUserList();
                        System.out.println("TASKMODEL: user "+taskUsers);
                        for(User i: taskUsers){
                            //这边需要变成getid()
                            if(i.getUserId().equals(firebaseUser.getUid())){
                                tasksFromFireBase.add(tasksModel);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
    }
}