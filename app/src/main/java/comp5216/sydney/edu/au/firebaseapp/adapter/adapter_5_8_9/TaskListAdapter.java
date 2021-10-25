package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_5_8_9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.TaskListActivity_5;
import comp5216.sydney.edu.au.firebaseapp.classtype.Assignment;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {
    private List<Tasks> assignmentModelList;
    private TaskListActivity_5 activity;
    private FirebaseFirestore firestore;


    public TaskListAdapter(List<Tasks> assignmentModelList, TaskListActivity_5 activity) {
        this.assignmentModelList = assignmentModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.activity_5_task, parent, false);
        firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Tasks tasksModel = assignmentModelList.get(position);
        System.out.println("tasksModel.getGroupName(): "+tasksModel.getGroupName());
        System.out.println("tasksModel.getStates(): "+tasksModel.getStates());
        //List<TaskOfAssignmentModel> taskList = assignmentModel.getTasks();
        holder.due.setText(tasksModel.getTaskName() +" Due: "+ tasksModel.getDueDate());
        holder.name.setChecked(toBoolean(tasksModel.getStates()));
        holder.name.setText(tasksModel.getGroupName());
        holder.desceiption.setText(tasksModel.getDescription());
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tasksModel.setStates(tasksModel.getStates()+1);
                    System.out.println(tasksModel.getTaskName()+"  taskModel.setStatus: "+tasksModel.getStates());
                    if (tasksModel.getStates()==tasksModel.getSelectedList().size()){
                        tasksModel.setComplete(1);
                        firestore.collection("tasks").
                                document(tasksModel.getTaskId()).update("complete",1);

                    }

                    firestore.collection("tasks").
                            document(tasksModel.getTaskId()).update("states",tasksModel.getStates());
                }else {
                    tasksModel.setStates(tasksModel.getStates()-1);
                    System.out.println(tasksModel.getTaskName()+"  taskModel.setStatus: "+tasksModel.getStates());
                    firestore.collection("tasks").
                            document(tasksModel.getTaskId()).update("states",tasksModel.getStates());
                    if (tasksModel.getStates()<tasksModel.getSelectedList().size()){
                        firestore.collection("tasks").
                                document(tasksModel.getTaskId()).update("complete",0);
                    }
                }
            }
        });
    }
    private boolean toBoolean(int status) {
        return status != 0;
    }


    @Override
    public int getItemCount() {
        return assignmentModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView desceiption;
        TextView due;
        CheckBox name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desceiption = itemView.findViewById(R.id.description);
            name  = itemView.findViewById(R.id.name);
            due  = itemView.findViewById(R.id.due);
        }
    }
}