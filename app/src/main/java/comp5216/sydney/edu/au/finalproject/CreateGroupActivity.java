package comp5216.sydney.edu.au.finalproject;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import comp5216.sydney.edu.au.finalproject.adapter.ShowGroupMemberAdapter;
import comp5216.sydney.edu.au.finalproject.model.Assignment;
import comp5216.sydney.edu.au.finalproject.model.User;

public class CreateGroupActivity extends AppCompatActivity {
    private Button mbtnAddMember;
    private Button mbtnAddAssignment;
    private RecyclerView groupRV;
    private FirebaseFirestore mFirestore;
    private ArrayList<User> userList;
    private ArrayList<Assignment> assignmentList;
    private ShowGroupMemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create);
        mbtnAddMember = findViewById(R.id.btnAddMember);
        mbtnAddAssignment = findViewById(R.id.btnAddAssignment);
        groupRV = findViewById(R.id.member_list);
        userList = new ArrayList<>();

        buildRecycleView();
        setButtonListeners();
    }

    private void buildRecycleView() {
        userList = new ArrayList<>();
        adapter = new ShowGroupMemberAdapter(userList, CreateGroupActivity.this);
        groupRV.setHasFixedSize(true);
        groupRV.setLayoutManager(new LinearLayoutManager(this));
        groupRV.setAdapter(adapter);
    }

    private void setButtonListeners() {

        ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<User> l = (ArrayList<User>) data.getSerializableExtra("ml");
                        System.out.println(l.size());
                        userList.addAll(l);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        mbtnAddMember.setOnClickListener(
                (view) -> {
                    Intent intent = new Intent(CreateGroupActivity.this, AddGroupMemsActivity.class);
                    mLauncher.launch(intent);
                }
        );

        mbtnAddAssignment.setOnClickListener(
                (view) -> {
                    Intent intent = new Intent(CreateGroupActivity.this, CreateAssignmentActivity.class);
                    startActivity(intent);
                }
        );
    }

    private void setMemRecycleViewListener() {
        groupRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
