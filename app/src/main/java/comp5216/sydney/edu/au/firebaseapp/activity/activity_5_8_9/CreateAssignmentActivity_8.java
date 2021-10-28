package comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_16;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12.TaskListAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Assignment;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.IdUtil;

public class CreateAssignmentActivity_8 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private TextView assignmentName,description,tv_date,tv_time;
    private RecyclerView taskView;
    private Button create_assignment;
    private Button create_task;
    private Button datePicker,timePicker;
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

    private int pickedYear = 0 , pickedMonth = 0, pickedDay = 0 , pickedHour =0 , pickedMinute =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_create_assignment);
        assignmentName = findViewById(R.id.assignmentName);
        description = findViewById(R.id.assignmentDes);
        taskView = findViewById(R.id.tasks);
        create_assignment = findViewById(R.id.create_assignment);
        create_task = findViewById(R.id.add_task);
        firestore = FirebaseFirestore.getInstance();
        Intent intent = this.getIntent();
        assignment = (Assignment) intent.getSerializableExtra("assignment");
        position = intent.getIntExtra("position", 0);

        //Add a date dialog on btDate button
        datePicker = findViewById(R.id.bt_datepicker);
        tv_date = findViewById(R.id.tv_date_picker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(CreateAssignmentActivity_8.this, CreateAssignmentActivity_8.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //Show this dialog on screen
                dialog.show();
            }
        });

        //Add a time dialog on btTime button
        timePicker = findViewById(R.id.bt_timepicker);
        tv_time = findViewById(R.id.tv_time_picker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(CreateAssignmentActivity_8.this, CreateAssignmentActivity_8.this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true);
                dialog.show();
            }
        });

        //If the assignment is not null, the assignment object is sent from AssignmentListAdapter and the assignment object is initialized
        if (assignment != null) {
            groupId = assignment.getGroupId();
            groupName = assignment.getGroupName();
            assignmentId = assignment.getAssignmentId();
            taskList = assignment.getTaskList();
            userList = assignment.getUserList();
            assignmentName.setText(assignment.getAssignmentName());
            description.setText(assignment.getDescription());
        }
        //If empty, the plus sign was clicked on AssignmentListActivity, and the data needs to be retrieved from the Intent
        else {
            taskList = new ArrayList<>();
            groupName = intent.getStringExtra("name");
            groupId = intent.getStringExtra("gpId");
            assignmentId = IdUtil.getUUID("a");
            userList = (ArrayList<User>) intent.getSerializableExtra("userList");
        }

        //create the adapter
        taskView.setHasFixedSize(true);
        taskView.setLayoutManager(new LinearLayoutManager(CreateAssignmentActivity_8.this));
        showTaskAdapter = new TaskListAdapter(this, taskList, CreateAssignmentActivity_8.this);
        taskView.setAdapter(showTaskAdapter);

        setListListener();
        setButtonListeners();
    }

    /**
     * receive the task object when the new task is created or updated
     */
    private void setListListener() {
        ActivityResultLauncher<Intent> xLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Tasks a = (Tasks) data.getSerializableExtra("task");
                        int i = data.getIntExtra("position", 0);
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

    /**
     * create or update the assignment
     */
    private void setButtonListeners() {

        String assignment_name = assignmentName.getText().toString();
        String des = description.getText().toString();
        Intent intent_1 = new Intent(this, Activity_16.class);

        create_assignment.setOnClickListener(view -> {
            dateVal = formatDate();
            String aName = assignmentName.getText().toString();
            String aDes = description.getText().toString();
            if (aName.isEmpty() || aDes.isEmpty()) {
                Toast.makeText(CreateAssignmentActivity_8.this, "Empty task or Empty description are not Allowed", Toast.LENGTH_SHORT).show();
            }
            //if the assignment is not null ,create the default value for each component
            if (assignment != null) {
                DocumentReference dr = firestore.collection("assignments").document(assignment.getAssignmentId());
                dr.update("assignmentName", aName);
                dr.update("description", aDes);
                dr.update("taskList", taskList);
                dr.get().addOnSuccessListener(documentSnapshot -> {
                    Assignment a = documentSnapshot.toObject(Assignment.class);
                    intent_1.putExtra("assignment", a);
                    intent_1.putExtra("position", 0);
                    Toast.makeText(CreateAssignmentActivity_8.this, "Assignment Updated", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent_1);
                    finish();
                });
            } else {
                //Create assignment object and add it to Firebase
                Assignment single = new Assignment(assignmentId, aName, aDes, dateVal, groupName, groupId, 0, taskList, userList);
                firestore.collection("assignments").document(single.getAssignmentId()).set(single);
                Toast.makeText(CreateAssignmentActivity_8.this, "Assignment Saved", Toast.LENGTH_SHORT).show();
                intent_1.putExtra("assignment", single);
                setResult(RESULT_OK, intent_1);
                finish();

            }
        });

        /**
         * receive the data if the task activity is created or updated
         */
        ActivityResultLauncher<Intent> tLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Tasks a = (Tasks) data.getSerializableExtra("task");
                        taskList.add(a);
                        showTaskAdapter.notifyDataSetChanged();
                    }
                }
        );

        /**
         * create task and put assignment information to the task activity
         */
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

    /**
     * format the data value
     * @return
     */
    private String formatDate() {
        String date = String.format("%4d-%2d-%2d %02d:%02d", pickedYear, pickedMonth, pickedDay, pickedHour, pickedMinute);
        return date;
    }

    /**
     * @desc Get year, month, day from the date dialog
     **/
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        pickedYear = year;
        pickedMonth = month + 1;
        pickedDay = day;
        String desc = String.format("%d-%d-%d", year, month + 1, day);
        tv_date.setText(desc);
    }

    /**
     * @desc Get hours and minutes from the time dialog
     **/
    @Override
    public void onTimeSet(TimePicker timePicker, int HH, int mm) {
        pickedHour = HH;
        pickedMinute = mm;
        String desc = String.format("%d:%d", HH, mm);
        tv_time.setText(desc);
    }

}