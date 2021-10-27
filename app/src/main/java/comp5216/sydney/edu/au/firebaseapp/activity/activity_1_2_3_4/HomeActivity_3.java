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
    //获取数据库的用户
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


    private void getData() {
        //用本地数据库
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //从firebase cloud 的Users中拿数据下来
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
            //从firebase cloud database拿数据下来
            DocumentReference docRef = db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            currentUser = document.toObject(User.class);
                            //把登录的用户信息放在缓存中
                            mCache.put(firebaseUser.getUid(), currentUser);

                            //从firebase cloud 的Groups中拿数据下来
                            db.collection("groupBriefs")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                //先清空一下list
                                                groupItemList.clear();
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    GroupBrief groupBrief = document.toObject(GroupBrief.class);
                                                    if (currentUser != null && currentUser.getGroupList() != null) {
                                                        for (String id : currentUser.getGroupList()) {
                                                            if (groupBrief.getGroupId().equals(id)) {
                                                                System.out.println("检查有没有这个item:" + groupBrief.getGroupName());

                                                                groupItemList.add(groupBrief);

//                                                                groupIdList.add(groupBrief.getGroupId());
                                                                //放在缓存中
//                                                                mCache.put(group.getGroupId(), group);
                                                                Log.d("group item test:", document.getId() + " => " + document.getData());
                                                            }
                                                        }

                                                    } else {
                                                        System.out.println("这个用户没有加入任何group！！！");
                                                    }
                                                }

                                                // Create an adapter for the list view using Android's built-in item layout
//                                                groupAdapter = new GroupAdapter(groupItemList, HomeActivity_3.this, groupIdList);

                                                // Connect the listView and the adapter
//                                                listView.setAdapter(groupAdapter);
                                            } else {
                                                Log.d("home item test:", "Error getting documents: ", task.getException());
                                            }
                                            display();
                                        }
                                    });
                        } else {
                            Log.d("拿数据测试", "No such document");
                        }
                    } else {
                        Log.d("拿数据测试", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public void profileOnClick(View view) {
        Intent intent = new Intent(HomeActivity_3.this, ProfileActivity_4.class);
        startActivity(intent);
        finish();
    }

    public void taskOnClick(View view) {
        Intent intent = new Intent(HomeActivity_3.this, TaskListActivity_5.class);
        startActivity(intent);
    }



    public void createGroupTestOnClick(View view) {
        Intent intent = new Intent(HomeActivity_3.this, Activity_16.class);
        intent.putExtra("curUser", (Serializable) currentUser);
        mLauncherEditGroup.launch(intent);

    }

    //造一些假数据
    public void setData(View view) {
        //先把测试数据放上去
        CollectionReference homeItems = db.collection("groupBriefs");
        CollectionReference groupItems = db.collection("groups");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(userId);
        ref.child(userId).child("result").setValue(1);

        List<Assignment> assignmentList = new ArrayList<>();
        List<User> groupUserList = new ArrayList<>();
        Group group;

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        User user1 = new User("user1", "userA", "a@gmail", null);
        User user2 = new User("user2", "userB", "b@gmail", null);
        User user3 = new User("user3", "userC", "c@gmail", null);
        User user4 = new User("user4", "userD", "d@gmail", null);
        User user5 = new User("user5", "userE", "e@gmail", null);
        User user6 = new User("user6", "userF", "f@gmail", null);
        User user7 = new User("user7", "userG", "g@gmail", null);


        Tasks task1 = new Tasks("task1", "assignment1", "group1", "taskA", "", timeStamp + "A",
                "group1", new ArrayList<>(Arrays.asList(user1, user2)));
        Tasks task2 = new Tasks("task2", "assignment1", "group1", "taskB", "", timeStamp + "B",
                "group1", new ArrayList<>(Arrays.asList(user3, user4)));
        Tasks task3 = new Tasks("task3", "assignment2", "group1", "taskC", "", timeStamp + "C",
                "group1", new ArrayList<>(Arrays.asList(user5)));


        Assignment assignment1 = new Assignment("assignment1", "assignemntA", "", timeStamp + "D",
                "group1", new ArrayList<>(Arrays.asList(task1, task2)));
        Assignment assignment2 = new Assignment("assignment2", "assignemntB", "", timeStamp + "E",
                "group1", new ArrayList<>(Arrays.asList(task3)));

        assignmentList.addAll(Arrays.asList(assignment1, assignment2));
        groupUserList.addAll(Arrays.asList(user1, user2, user3, user4, user5, user6, user7));

        group = new Group("group1", "groupA", "", assignmentList, groupUserList);
        groupItems.document("group1").set(group);

        Map<String, Object> data1 = new HashMap<>();
        data1.put("groupId", "group1");
        data1.put("groupName", "COMP5216 Group7");
        data1.put("introduction", "mobile computing!");
        homeItems.document("group1").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("groupId", "group2");
        data2.put("groupName", "IT Innovation");
        data2.put("introduction", "INFO5992 Group1");
        homeItems.document("group2").set(data2);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("groupId", "group4");
        data4.put("groupName", "New Group");
        data4.put("introduction", "INFO5990 Group8");
        homeItems.document("group4").set(data4);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference updateUser = db.collection("Users").document(user.getUid());

        updateUser.update("groupList", Arrays.asList("group1",currentUser.getGroupList())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(HomeActivity_3.this, "Success", Toast.LENGTH_SHORT).show();
                getData();
                recycleAdapter_group_3.change(groupItemList);

            }
        });
    }
//test

}