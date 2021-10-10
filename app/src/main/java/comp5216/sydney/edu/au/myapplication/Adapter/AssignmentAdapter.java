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
import comp5216.sydney.edu.au.myapplication.R;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    private List<AssignmentModel> assignmentModelList;
    private AssignmentActivity activity;
    //private FirebaseFirestore firestore;


    public AssignmentAdapter(List<AssignmentModel> assignmentModelList, AssignmentActivity activity) {
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
        AssignmentModel assignmentModel = assignmentModelList.get(position);
        List<TextView> descriptionList = new ArrayList<>();
        holder.due.setText("Due: "+assignmentModel.getDue());
        holder.name.setChecked(toBoolean(assignmentModel.getStatus()));
        holder.name.setText(assignmentModel.getUser());
//        List<TaskModel> val = assignmentModel.getTasks();
//        holder.desceiption.setText(val.get(0).getName());
//        holder.desceiption2.setText(val.get(0).getName());
//        holder.desceiption3.setText(val.get(0).getName());
        descriptionList.add(holder.desceiption);
        descriptionList.add(holder.desceiption2);
        descriptionList.add(holder.desceiption3);
        int count = 0;
        for(TaskModel i :assignmentModel.getTasks()){
            if(count == 3){
                break;
            }
            descriptionList.get(count).setText(i.getName());
            count++;
        }
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
            desceiption2 = itemView.findViewById(R.id.description2);
            desceiption3 = itemView.findViewById(R.id.description3);
            name  = itemView.findViewById(R.id.name);
            due  = itemView.findViewById(R.id.due);
        }
    }
}