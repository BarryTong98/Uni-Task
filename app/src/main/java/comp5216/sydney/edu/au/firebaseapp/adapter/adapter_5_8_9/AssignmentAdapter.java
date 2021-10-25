package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_5_8_9;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.AssignmentActivity_5;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    private List<Tasks> assignmentModelList;
    private AssignmentActivity_5 activity;
    private FirebaseFirestore firestore;


    public AssignmentAdapter(List<Tasks> assignmentModelList, AssignmentActivity_5 activity) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Please confirm that you have completed this task")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tasksModel.setStates(1);
                                    System.out.println(tasksModel.getTaskName() + "  taskModel.setStatus: " + tasksModel.getStates());
                                    firestore.collection("tasks").document(tasksModel.getTaskId()).update("states", 1);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.name.setChecked(false);
                        }
                    });
                    builder.create().show();
                    tasksModel.setStates(1);
                    System.out.println(tasksModel.getTaskName()+"  taskModel.setStatus: "+tasksModel.getStates());
                    firestore.collection("tasks").document(tasksModel.getTaskId()).update("states",1);
                }else {
                    tasksModel.setStates(0);
                    System.out.println(tasksModel.getTaskName()+"  taskModel.setStatus: "+tasksModel.getStates());
                    firestore.collection("tasks").document(tasksModel.getTaskId()).update("states",0);
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