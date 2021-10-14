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
    private List<User> groupUserList;

    Button button;
    Group group;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        assignmentList = new ArrayList<>();
        groupUserList = new ArrayList<>();
        Member member1 = new Member("M1", "AA");
        Member member2 = new Member("M2", "BB");
        Member member3 = new Member("M3", "CC");
        Member member4 = new Member("M4", "DD");
        Member member5 = new Member("M5", "EE");
        Member member6 = new Member("M6", "FF");
        Member member7 = new Member("M7", "GG");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        User user1=new User("user1","userA","a@gmail",null);
        User user2=new User("user2","userB","b@gmail",null);
        User user3=new User("user3","userC","c@gmail",null);
        User user4=new User("user4","userD","d@gmail",null);
        User user5=new User("user5","userE","e@gmail",null);
        User user6=new User("user6","userF","f@gmail",null);
        User user7=new User("user7","userG","g@gmail",null);
        
        Task task1=new Task("task1","assignment1","group1","taskA","",timeStamp+"A",
                "group1",Arrays.asList(user1,user2));
        Task task2=new Task("task2","assignment1","group1","taskB","",timeStamp+"B",
                "group1",Arrays.asList(user3,user4));
        Task task3=new Task("task3","assignment2","group1","taskC","",timeStamp+"C",
                "group1",Arrays.asList(user5));




        Assignment assignment1=new Assignment("assignment1","assignemntA","",timeStamp+"D",
                "group1",Arrays.asList(task1,task2));
        Assignment assignment2=new Assignment("assignment2","assignemntB","",timeStamp+"E",
                "group1",Arrays.asList(task3));

        assignmentList.addAll(Arrays.asList(assignment1,assignment2));
        groupUserList.addAll(Arrays.asList(user1,user2,user3,user4,user5,user6,user7));

        group=new Group("group1","groupA","",assignmentList,groupUserList);

//        Task task1 = new Task("T1", "Ta","A" ,null,"gA",timeStamp + "A", Arrays.asList(member1, member2), "A1");
//        Task task2 = new Task("T2", "Tb", timeStamp + "B", Arrays.asList(member3, member4), "A1");
//        Task task3 = new Task("T3", "Tc", timeStamp + "C", Arrays.asList(member5), "A2");



        textView = findViewById(R.id.memberSize);
        textView.setText(Integer.toString(group.getUserList().size()));

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
