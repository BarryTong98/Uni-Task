package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_5_8_9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.TaskListActivity_5;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;

//RecyclerView For the TaskListActivity_5 to show the tasks for current user
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {
    private List<Tasks> assignmentModelList;
    private TaskListActivity_5 activity;
    private FirebaseFirestore firestore;

    /**
     * Constructor for the TaskListAdapter
     */
    public TaskListAdapter(List<Tasks> assignmentModelList, TaskListActivity_5 activity) {
        this.assignmentModelList = assignmentModelList;
        this.activity = activity;
    }

    /**
     * initialize the values
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.activity_5_task, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    /**
     * Receive the data from the adapter and set the status value which is changing with checkbox
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Get the Specific Tasks Model from the Recycler View
        Tasks tasksModel = assignmentModelList.get(position);

        //Set the default value for each component
        holder.due.setText(tasksModel.getTaskName() + " Due: " + tasksModel.getDueDate());
        holder.name.setChecked(toBoolean(tasksModel.getStates()));
        holder.name.setText(tasksModel.getGroupName());
        holder.desceiption.setText(tasksModel.getDescription());

        //set the status value which is changing with checkbox
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if the check box is clicked
                if (isChecked) {
                    tasksModel.setStates(tasksModel.getStates() + 1);
                    //upload the status to firebase
                    if (tasksModel.getStates() == tasksModel.getSelectedList().size()) {
                        tasksModel.setComplete(1);
                        firestore.collection("tasks").
                                document(tasksModel.getTaskId()).update("complete", 1);
                    }
                    firestore.collection("tasks").
                            document(tasksModel.getTaskId()).update("states", tasksModel.getStates());
                }
                //if the check box is not clicked
                else {
                    tasksModel.setStates(tasksModel.getStates() - 1);
                    firestore.collection("tasks").
                            document(tasksModel.getTaskId()).update("states", tasksModel.getStates());
                    //upload the status to firebase
                    if (tasksModel.getStates() < tasksModel.getSelectedList().size()) {
                        firestore.collection("tasks").
                                document(tasksModel.getTaskId()).update("complete", 0);
                    }
                }
            }
        });
    }

    /**
     * check the value is clicked or not
     *
     * @param status
     * @return
     */
    private boolean toBoolean(int status) {
        return status != 0;
    }

    /**
     * Get the size of adapter
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return assignmentModelList.size();
    }

    /**
     * Connect the ViewHolder with component
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView desceiption;
        TextView due;
        CheckBox name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desceiption = itemView.findViewById(R.id.description);
            name = itemView.findViewById(R.id.name);
            due = itemView.findViewById(R.id.due);
        }
    }
}