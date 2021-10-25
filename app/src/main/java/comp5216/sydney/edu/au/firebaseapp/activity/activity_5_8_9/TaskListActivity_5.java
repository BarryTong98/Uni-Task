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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4.HomeActivity_3;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4.ProfileActivity_4;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_5_8_9.TaskListAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class TaskListActivity_5 extends AppCompatActivity {
    private Button home;
    private Button task;
    private Button profile;
    private RecyclerView recyclerView;
    private TaskListAdapter adapter;
    private List<Tasks> tasksFromFireBase;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_task_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview);
        home = findViewById(R.id.home);
        task = findViewById(R.id.task);
        profile = findViewById(R.id.profile);
        recyclerView = findViewById(R.id.recyclerview);
        firestore = FirebaseFirestore.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskListActivity_5.this));
        tasksFromFireBase = new ArrayList<>();
        showData();


        adapter = new TaskListAdapter(tasksFromFireBase, TaskListActivity_5.this);
        recyclerView.setAdapter(adapter);
    }

    public void toHome(View view) {
        Intent intent = new Intent(TaskListActivity_5.this, HomeActivity_3.class);
        startActivity(intent);
    }


    public void toProfile(View view) {
        Intent intent = new Intent(TaskListActivity_5.this, ProfileActivity_4.class);
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
                        Collections.sort(tasksFromFireBase, new Comparator<Tasks>() {
                            @Override
                            public int compare(Tasks o1, Tasks o2) {
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                String due1=o1.getDueDate();
                                String due2=o2.getDueDate();
                                Date date1 = new Date(),date2 = new Date();
                                try {
                                    date1=simpleDateFormat.parse(due1);
                                    date2=simpleDateFormat.parse(due2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return date1.compareTo(date2);
                            }
                        });
                    }

                }
            }
        });
    }
}