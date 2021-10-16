package comp5216.sydney.edu.au.myapplication.Adapter;

import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.myapplication.AssignmentActivity;
import comp5216.sydney.edu.au.myapplication.MainActivity;
import comp5216.sydney.edu.au.myapplication.Model.AssignmentModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.Model.TaskOfAssignmentModel;
import comp5216.sydney.edu.au.myapplication.R;
import comp5216.sydney.edu.au.myapplication.model1.Task;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    private List<Task> assignmentModelList;
    private AssignmentActivity activity;
    //private FirebaseFirestore firestore;


    public AssignmentAdapter(List<Task> assignmentModelList, AssignmentActivity activity) {
        this.assignmentModelList = assignmentModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.assignment, parent, false);
        //firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task taskModel = assignmentModelList.get(position);
        //List<TaskOfAssignmentModel> taskList = assignmentModel.getTasks();
        holder.due.setText(taskModel.getTaskName() +" Due: "+taskModel.getDueDate());
        holder.name.setChecked(toBoolean(taskModel.getStatus()));
        holder.name.setText(taskModel.getGroupName());
//        StringBuilder des = new StringBuilder();
//        int count = 1;
//        for (TaskOfAssignmentModel i : taskList){
//            des.append(count+"."+ i.getTaskName()+"\n");
//            count++;
//        }
//        holder.desceiption.setText(des.toString());
        holder.desceiption.setText(taskModel.getDescription());
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
        TextView desceiption2;
        TextView desceiption3;
        TextView due;
        CheckBox name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desceiption = itemView.findViewById(R.id.description);
//            desceiption2 = itemView.findViewById(R.id.description2);
//            desceiption3 = itemView.findViewById(R.id.description3);
            name  = itemView.findViewById(R.id.name);
            due  = itemView.findViewById(R.id.due);
        }
    }
}