package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16;


import comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4.HomeActivity_3;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.CreateAssignmentActivity_8;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_6_7_12.AddGroupMemsActivity_7;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16.RecycleAdapter_ass_10_16;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16.RecycleAdapter_mem_10_16;
import comp5216.sydney.edu.au.firebaseapp.classtype.*;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.util.IdUtil;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Activity_16 extends AppCompatActivity {
    private Group group;
    private RecyclerView assignmentRecyclerView;
    private RecyclerView memberRecyclerView;
    private EditText groupNameEdit;
    private EditText descriptionEdit;
    private ImageView deleteIcon;
    private User curUser;

    private final static int ADDASSIGNMENT = 101;
    private final static int ADDMEMBER = 102;

    private List<Assignment> assignmentList;
    private List<User> userList;
    private ArrayList<Discussion> discussionList;
    private String groupId;
    private String groupName;
    private String description;
    private FirebaseUser firebaseUser;
    RecycleAdapter_ass_10_16 recycleAdapter_ass_16;
    RecycleAdapter_mem_10_16 recycleAdapter_mem_16;

    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_16);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignmentRecyclerView = findViewById(R.id.assignmentView16);
        memberRecyclerView = findViewById(R.id.groupMemberView16);
        deleteIcon = findViewById(R.id.delete_icon_16);
        groupNameEdit = findViewById(R.id.group_name_edit_16);
        descriptionEdit = findViewById(R.id.group_intro_edit_16);
        userList = new ArrayList<>();

        Intent i = getIntent();
        curUser = (User)i.getSerializableExtra("curUser");
        userList.add(curUser);
        group = (Group) i.getSerializableExtra("Group");
        if (group != null) {
            groupId = group.getGroupId();
            groupName = group.getGroupName();
            description = group.getIntroduction();
            assignmentList = group.getAssignmentList();
            userList = group.getUserList();
            display();
        } else {
            display();
        }

    }


    private void display() {

        if (groupName != null && description != null) {
            groupNameEdit.setText(groupName);
            descriptionEdit.setText(description);
        }

        createAssignmentList(this.assignmentList);
        createUserList(this.userList);

    }

    public void deleteOnClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_group)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent resultIntent = new Intent(Activity_16.this, HomeActivity_3.class);
                        resultIntent.putExtra("GroupResult", group);
                        resultIntent.putExtra("deleteOrNot", -1);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                        // Remove item from the ArrayList
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User cancelled the dialog
                        // Nothing happens
                    }
                });
        builder.create().show();
    }

    public void createAssignmentList(List<Assignment> assignmentList) {
        if (assignmentList != null) {
            recycleAdapter_ass_16 = new RecycleAdapter_ass_10_16(this, assignmentList, "activity16");
            assignmentRecyclerView.setHasFixedSize(true);
            assignmentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            assignmentRecyclerView.setAdapter(recycleAdapter_ass_16);
            assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
            assignmentRecyclerView.addItemDecoration(divider);
        }
    }

    public void createUserList(List<User> userList) {
        if (userList != null) {
            recycleAdapter_mem_16 = new RecycleAdapter_mem_10_16(this, userList, "activity16");
            memberRecyclerView.setHasFixedSize(true);

            memberRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            memberRecyclerView.setAdapter(recycleAdapter_mem_16);
            memberRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
            memberRecyclerView.addItemDecoration(divider);
        }
    }


    public void onCompleteClick(View view) {
        Intent resultIntent = new Intent(this, HomeActivity_3.class);
        if (group == null) {
            group = new Group(IdUtil.getUUID("F"));
        }
        group.setGroupName(groupNameEdit.getText().toString());
        group.setIntroduction(descriptionEdit.getText().toString());
        resultIntent.putExtra("GroupResult", group);
        resultIntent.putExtra("deleteOrNot", 1);
        saveData();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void saveData() {
        group.setGroupName(groupNameEdit.getText().toString());
        group.setIntroduction(descriptionEdit.getText().toString());
        group.setAssignmentList(assignmentList);
        group.setUserList(userList);
        CollectionReference groupItems = db.collection("groups");
        groupItems.document(group.getGroupId()).set(group);

        //把每个member的grouplist加上这个group
        List<String> updateGroupList;
        if (userList != null) {
            for (User member : userList) {
                System.out.println("members:" + member.toString());
                DocumentReference updateUser = db.collection("Users").document(member.getUserId());
                updateGroupList = member.getGroupList();
                if (updateGroupList != null){
                    updateGroupList.add(group.getGroupId());
                }else {
                    updateGroupList = new ArrayList<>();
                    updateGroupList.add(group.getGroupId());
                }

                updateUser
                        .update(
                                "groupList", updateGroupList
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Add group", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Add group", "Error updating document", e);
                            }
                        });
            }
        }
    }

    public void onAddAssignmentClick16(View view) {
        if (userList.size() > 1) {
            System.out.println("----------------- " + userList.size());
            Intent intent = new Intent(this, CreateAssignmentActivity_8.class);
            intent.putExtra("userList", (Serializable) userList);
            intent.putExtra("name", groupNameEdit.getText().toString());
            intent.putExtra("gpId", groupId);
            mLauncher.launch(intent);
        } else {
            Toast.makeText(this, "Please add group member first.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onAddMemberClick16(View view) {
        Intent memberIntent = new Intent(this, AddGroupMemsActivity_7.class);
        mLauncher.launch(memberIntent);
    }

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    Assignment assignment = (Assignment) data.getSerializableExtra("assignment");
                    if (assignmentList != null) {
                        assignmentList.add(assignment);
                    } else {
                        assignmentList = new ArrayList<>();
                        assignmentList.add(assignment);
                    }
//                    group.setAssignmentList(assignmentList);
                    createAssignmentList(assignmentList);

                } else if (result.getResultCode() == ADDMEMBER) {
                    Intent data = result.getData();
                    ArrayList<User> returnUserList = (ArrayList<User>) data.getSerializableExtra("memberReturn");
                    if (returnUserList != null) {
                        if (userList != null) {
                            Map<String, User> map = new HashMap<>();
                            for (User user : userList) {
                                map.put(user.getUserId(), user);
                            }

                            for (User add : returnUserList) {
                                map.put(add.getUserId(), add);
                            }
                            userList = new ArrayList<>(map.values());

                        } else {
                            userList = new ArrayList<>(returnUserList);
                        }
                    }
                    if (group != null && groupId != null) {
                        DocumentReference updateGroup = db.collection("groups").document(groupId);
                        updateGroup.update("userList", userList).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Activity_16.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {

                    }
                    createUserList(userList);
                }

            }
    );


}
