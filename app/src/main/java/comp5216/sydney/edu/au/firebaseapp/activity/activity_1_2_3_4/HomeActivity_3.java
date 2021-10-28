package comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp5216.sydney.edu.au.firebaseapp.R;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_16;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.TaskListActivity_5;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_1_2_3_4.RecycleAdapter_group_3;
import comp5216.sydney.edu.au.firebaseapp.classtype.*;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;

public class HomeActivity_3 extends AppCompatActivity {
    private Button data;
    private RecyclerView recyclerView;
    private List<GroupBrief> groupItemList = new ArrayList<GroupBrief>();
    private Map<String, Group> groupMap = new HashMap<>();
    private FirebaseUser firebaseUser;
    RecycleAdapter_group_3 recycleAdapter_group_3;
    private User currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;
    ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_main);

        mCache = ACache.get(this);
        mCache.clear();
        getData();


    }

    private void display() {
        recyclerView = findViewById(R.id.recycle_home);
        recycleAdapter_group_3 = new RecycleAdapter_group_3(this, groupItemList, groupMap, db, userId, mCache, mLauncherEditGroup);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recycleAdapter_group_3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(divider);
    }

    ActivityResultLauncher<Intent> mLauncherEditGroup = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Extract name value from result extras
                    Group groupResult = (Group) result.getData().getSerializableExtra("GroupResult");
                    int deleteOrNot = result.getData().getIntExtra("deleteOrNot", 1);

                    if (deleteOrNot == 1) {
                        groupMap.put(groupResult.getGroupId(), groupResult);
                        //先把测试数据放上去
                        CollectionReference homeItems = db.collection("groupBriefs");
                        CollectionReference groupItems = db.collection("groups");
                        DocumentReference updateUser = db.collection("Users").document(userId);
                        groupItems.document(groupResult.getGroupId()).set(groupResult).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Map<String, Object> data1 = new HashMap<>();
                                data1.put("groupId", groupResult.getGroupId());
                                data1.put("groupName", groupResult.getGroupName());
                                data1.put("introduction", groupResult.getIntroduction());

                                List<String> tempList=new ArrayList<>();
                                if (currentUser.getGroupList() != null){
                                    tempList = currentUser.getGroupList();
                                }

                                if (!tempList.contains(groupResult.getGroupId())) {
                                    tempList.add(groupResult.getGroupId());
                                }

                                updateUser.update("groupList", tempList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(HomeActivity_3.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                homeItems.document(groupResult.getGroupId()).set(data1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(HomeActivity_3.this, "Success", Toast.LENGTH_SHORT).show();
                                        getData();
                                        recycleAdapter_group_3.change(groupItemList);
                                    }
                                });
                            }
                        });
                    } else {
                        if (groupResult != null) {
                            DocumentReference updateUser = db.collection("Users").document(userId);
                            DocumentReference updateGroup = db.collection("groups").document(groupResult.getGroupId());
                            List<String> tempGroupId = currentUser.getGroupList();
                            if (tempGroupId != null) {
                                for (String i : tempGroupId) {
                                    if (i.equalsIgnoreCase(groupResult.getGroupId())) {
                                        tempGroupId.remove(i);
                                        break;
                                    }
                                }
                            }

                            if (groupResult != null) {
                                List<User> userTempList = groupResult.getUserList();
                                if (userTempList != null) {
                                    for (User userTemp : userTempList) {
                                        if (userTemp.getUserId().equalsIgnoreCase(userId)) {
                                            userTempList.remove(userTemp);
                                            break;
                                        }
                                    }
                                }
                                updateGroup.update("userList", userTempList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        updateUser.update("groupList", tempGroupId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(HomeActivity_3.this, "Success", Toast.LENGTH_SHORT).show();
                                                getData();
                                                recycleAdapter_group_3.change(groupItemList);

                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }
                }
            }

    );


    /**
     * After entering the home page, connect with the firebase and get the group information automatically.
     */
    private void getData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
            DocumentReference docRef = db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            currentUser = document.toObject(User.class);
                            mCache.put(firebaseUser.getUid(), currentUser);

                            db.collection("groupBriefs")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                groupItemList.clear();
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    GroupBrief groupBrief = document.toObject(GroupBrief.class);
                                                    if (currentUser != null && currentUser.getGroupList() != null) {
                                                        for (String id : currentUser.getGroupList()) {
                                                            if (groupBrief.getGroupId().equals(id)) {
                                                                groupItemList.add(groupBrief);
                                                                Log.d("Home activity", document.getId() + " => " + document.getData());
                                                            }
                                                        }

                                                    } else {
                                                        Log.d("Home activity", "This user has no groups!");
                                                    }
                                                }
                                            } else {
                                                Log.d("Home activity", "Error getting documents: ", task.getException());
                                            }
                                            display();
                                        }
                                    });
                        } else {
                            Log.d("Home activity", "No such document");
                        }
                    } else {
                        Log.d("Home activity", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    /**
     * Click the profile button, jump to ProfileActivity
     */
    public void profileOnClick(View view) {
        Intent intent = new Intent(HomeActivity_3.this, ProfileActivity_4.class);
        startActivity(intent);
        finish();
    }

    /**
     * Click the task button, jump to CreateGroupActivity
     */
    public void taskOnClick(View view) {
        Intent intent = new Intent(HomeActivity_3.this, TaskListActivity_5.class);
        startActivity(intent);
    }

    /**
     * Click the create group image button, jump to CreateGroupActivity
     */
    public void createGroupTestOnClick(View view) {
        Intent intent = new Intent(HomeActivity_3.this, Activity_16.class);
        intent.putExtra("curUser", (Serializable) currentUser);
        mLauncherEditGroup.launch(intent);

    }

}