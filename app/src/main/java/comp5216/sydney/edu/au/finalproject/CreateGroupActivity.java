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
import comp5216.sydney.edu.au.finalproject.model.Person;

public class CreateGroupActivity extends AppCompatActivity {
    private Button mbtnAdd;
    private Button addData;
    private RecyclerView groupRV;
    private FirebaseFirestore mFirestore;
    private ArrayList<Person> list;
    private ShowGroupMemberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create);
        mbtnAdd = findViewById(R.id.btnAddMember);
        addData = findViewById(R.id.btnAddData);
        groupRV = findViewById(R.id.member_list);
        list = new ArrayList<>();

        buildRecycleView();
        setButtonListeners();
    }

    private void buildRecycleView() {
        list = new ArrayList<>();
        Person p1 = new Person("1", "tzy", "112233@qq.com", "asdfasdflk", "postgraduate");
        list.add(p1);
        adapter = new ShowGroupMemberAdapter(list, CreateGroupActivity.this);
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
                        ArrayList<Person> l = (ArrayList<Person>) data.getSerializableExtra("ml");
                        System.out.println(l.size());
                        list.addAll(l);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        mbtnAdd.setOnClickListener(
                (view) -> {
                    Intent intent = new Intent(CreateGroupActivity.this, AddGroupMemsActivity.class);
                    mLauncher.launch(intent);
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
