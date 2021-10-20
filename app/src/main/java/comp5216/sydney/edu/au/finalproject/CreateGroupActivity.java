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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import comp5216.sydney.edu.au.finalproject.adapter.ShowAssignmentAdapter;
import comp5216.sydney.edu.au.finalproject.adapter.ShowGroupMemberAdapter;
import comp5216.sydney.edu.au.finalproject.model.Assignment;
import comp5216.sydney.edu.au.finalproject.model.Group;
import comp5216.sydney.edu.au.finalproject.model.User;
import comp5216.sydney.edu.au.finalproject.utils.IdUtil;

public class CreateGroupActivity extends AppCompatActivity {
    private ImageButton mbtnAddMember;
    private Button mbtnnextStep;
    private TextView groupName;
    private TextView groupDescription;
    private RecyclerView groupMemRV;
    private ArrayList<User> userList;
    private ShowGroupMemberAdapter showGroupMemberAdapter;

    public CreateGroupActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create);

        //initial view
        mbtnAddMember = findViewById(R.id.btnAddMember);
        mbtnnextStep = findViewById(R.id.btnNextStep);
        groupMemRV = findViewById(R.id.groupmember_list);
        groupName = findViewById(R.id.group_name_edit);
        groupDescription = findViewById(R.id.group_intro_edit);

        userList = new ArrayList<>();

        buildRecycleView();
        setButtonListeners();
    }

    private void buildRecycleView() {
        userList = new ArrayList<>();
        showGroupMemberAdapter = new ShowGroupMemberAdapter(userList, CreateGroupActivity.this);
        groupMemRV.setHasFixedSize(true);
        groupMemRV.setLayoutManager(new LinearLayoutManager(this));
        groupMemRV.setAdapter(showGroupMemberAdapter);
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
                        showGroupMemberAdapter.notifyDataSetChanged();
                    }
                }
        );

        mbtnAddMember.setOnClickListener(
                (view) -> {
                    Intent intent = new Intent(CreateGroupActivity.this, AddGroupMemsActivity.class);
                    mLauncher.launch(intent);
                }
        );

        mbtnnextStep.setOnClickListener(view -> {
            Intent intent = new Intent(CreateGroupActivity.this, AssignmentListActivity.class);
            startActivity(intent);
        });
    }


}
