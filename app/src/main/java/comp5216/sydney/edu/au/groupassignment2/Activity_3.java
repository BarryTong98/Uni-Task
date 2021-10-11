package comp5216.sydney.edu.au.groupassignment2;

import comp5216.sydney.edu.au.groupassignment2.classtype.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Activity_3 extends AppCompatActivity {
    private List<Assignment> assignmentList;
    private List<Member> groupMemberList;
    Button button;
    Group group;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        assignmentList = new ArrayList<>();
        groupMemberList = new ArrayList<>();
        Member member1 = new Member("M1", "AA");
        Member member2 = new Member("M2", "BB");
        Member member3 = new Member("M3", "CC");
        Member member4 = new Member("M4", "DD");
        Member member5 = new Member("M5", "EE");
        Member member6 = new Member("M6", "FF");
        Member member7 = new Member("M7", "GG");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        Task task1 = new Task("T1", "Ta", timeStamp + "A", Arrays.asList(member1, member2), "A1");
        Task task2 = new Task("T2", "Tb", timeStamp + "B", Arrays.asList(member3, member4), "A1");
        Task task3 = new Task("T3", "Tc", timeStamp + "C", Arrays.asList(member5), "A2");
        Assignment assignment1 = new Assignment("A1", "Aa",
                timeStamp + "D", Arrays.asList(member1, member2, member3, member4), Arrays.asList(task1, task2));
        Assignment assignment2 = new Assignment("A2", "Ab",
                timeStamp + "E", Arrays.asList(member5, member6), Arrays.asList(task3));

        assignmentList.add(assignment1);
        assignmentList.add(assignment2);

        groupMemberList.addAll(Arrays.asList(member1, member2, member3, member4, member5, member6, member7));
        group = new Group(groupMemberList, assignmentList);
        textView = findViewById(R.id.memberSize);
        textView.setText(Integer.toString(group.getMemberList().size()));

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Activity_3.this, Activity_10.class);
                intent.putExtra("Group", group);
                startActivity(intent);
//                mAddLauncher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> mAddLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Extract name value from result extras
                    // Make a standard toast that just contains text
                    Toast.makeText(getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();
                }
            }
    );
}
