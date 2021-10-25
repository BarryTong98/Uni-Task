package comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_16;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12.TaskListAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Assignment;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.IdUtil;

public class CreateAssignmentActivity_8 extends AppCompatActivity {
    private TextView assignmentName;
    private TextView description;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RecyclerView taskView;
    private Button create_assignment;
    private Button create_task;
    private FirebaseFirestore firestore;
    private Group group;
    private TaskListAdapter showTaskAdapter;
    private ArrayList<Tasks> taskList;
    private ArrayList<User> userList;
    private String assignmentId;
    private String dateVal;
    private String groupId;
    private String groupName;
    private Assignment assignment;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_create_assignment);
        assignmentName = findViewById(R.id.assignmentName);
        description = findViewById(R.id.assignmentDes);
        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);
        timePicker.setIs24HourView(true);
        taskView = findViewById(R.id.tasks);
        create_assignment = findViewById(R.id.create_assignment);
        create_task = findViewById(R.id.add_task);
        firestore = FirebaseFirestore.getInstance();
        Intent intent = this.getIntent();
        //初始化数据，判断如果assignment不为空说明是从AssignmentListAdapter传过来的，获得assignment对象，初始化数据
        //如果为空说明是从AssignmentListActivity点击加号过来的，则需要从intent获取数据
        assignment =(Assignment) intent.getSerializableExtra("assignment");
        position = intent.getIntExtra("position", 0);

        if(assignment != null) {
            System.out.println("-----" + position + "------" + assignment.toString());
            groupId = assignment.getGroupId();
            groupName = assignment.getGroupName();
            assignmentId = assignment.getAssignmentId();
            taskList = assignment.getTaskList();
            userList = assignment.getUserList();
            assignmentName.setText(assignment.getAssignmentName());
            description.setText(assignment.getDescription());
        } else {
            taskList = new ArrayList<>();
            groupName = intent.getStringExtra("name");
            groupId = intent.getStringExtra("gpId");
            assignmentId = IdUtil.getUUID("a");
            userList = (ArrayList<User>) intent.getSerializableExtra("userList");
        }


        taskView.setHasFixedSize(true);
        taskView.setLayoutManager(new LinearLayoutManager(CreateAssignmentActivity_8.this));
        showTaskAdapter = new TaskListAdapter(this, taskList, CreateAssignmentActivity_8.this);
        taskView.setAdapter(showTaskAdapter);

        setListListener();
        setButtonListeners();
    }

    private void setListListener() {
        ActivityResultLauncher<Intent> xLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Tasks a = (Tasks) data.getSerializableExtra("task");
                        int i = data.getIntExtra("position", 0);
                        System.out.println("ABC   " + a.getTaskName() + "    " + i);
                        taskList.set(i, a);
                        showTaskAdapter.notifyItemChanged(i);
                    }
                }
        );

        showTaskAdapter.setOnItemClickListener(position1 -> {
            Intent intent = new Intent(CreateAssignmentActivity_8.this, CreateTaskActivity_9.class);
            intent.putExtra("task", taskList.get(position1));
            intent.putExtra("position", position1);
            xLauncher.launch(intent);
        });
    }



    private void setButtonListeners() {

        String assignment_name = assignmentName.getText().toString();
        String des = description.getText().toString();
        Intent intent_1 = new Intent(this, Activity_16.class);

        create_assignment.setOnClickListener(view -> {
            dateVal = formatDate();
            String aName = assignmentName.getText().toString();
            System.out.println("Assignment:     "+ aName);
            String aDes = description.getText().toString();
            System.out.println("DES!!!!!!!!!!"+ aDes);
            System.out.println("CREATE TASK 2 RUNNING");
            if (aName.isEmpty() || aDes.isEmpty()) {
                Toast.makeText(CreateAssignmentActivity_8.this, "Empty task or Empty description are not Allowed", Toast.LENGTH_SHORT).show();
            }

            if(assignment != null) {
                DocumentReference dr =  firestore.collection("assignments").document(assignment.getAssignmentId());
                dr.update("assignmentName", aName);
                dr.update("description", aDes);
                dr.update("taskList", taskList);

                dr.get().addOnSuccessListener(documentSnapshot -> {
                    Assignment a = documentSnapshot.toObject(Assignment.class);
                    System.out.println("-----   " + a.getAssignmentName() + "   " + a.getDescription());
                    intent_1.putExtra("assignment", a);
                    intent_1.putExtra("position", 0);
                    Toast.makeText(CreateAssignmentActivity_8.this, "Assignment Updated", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent_1);
                    finish();
                });
            } else {
                //创建assignment对象，添加到firebase
                Assignment single = new Assignment(assignmentId, aName, aDes, dateVal, groupName, groupId, 0, taskList, userList);
                firestore.collection("assignments").document(single.getAssignmentId()).set(single);
                Toast.makeText(CreateAssignmentActivity_8.this, "Assignment Saved", Toast.LENGTH_SHORT).show();
                intent_1.putExtra("assignment", single);
                setResult(RESULT_OK, intent_1);
                finish();

            }
        });

        ActivityResultLauncher<Intent> tLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Tasks a = (Tasks) data.getSerializableExtra("task");
                        taskList.add(a);
                        showTaskAdapter.notifyDataSetChanged();
                    }
                }
        );

        //创建task按钮
        create_task.setOnClickListener(view -> {
            dateVal = formatDate();
            Intent intent_2 = new Intent(CreateAssignmentActivity_8.this, CreateTaskActivity_9.class);
            intent_2.putExtra("groupName", groupName);
            intent_2.putExtra("userList", userList);
            intent_2.putExtra("dateVal", dateVal);
            intent_2.putExtra("id", assignmentId);
            intent_2.putExtra("groupId", groupId);
            tLauncher.launch(intent_2);
        });
    }

    //格式化日期
    private String formatDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String date = String.format("%4d-%2d-%2d %02d:%02d", year, month, day, hour, minute);
        return date;
    }
}