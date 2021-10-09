package comp5216.sydney.edu.au.myapplication.Adapter;

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

import comp5216.sydney.edu.au.myapplication.MainActivity;
import comp5216.sydney.edu.au.myapplication.Model.TaskModel;
import comp5216.sydney.edu.au.myapplication.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<TaskModel> taskModelList;
    private MainActivity activity;
    //private FirebaseFirestore firestore;

    public TaskAdapter(MainActivity mainActivity, List<TaskModel> taskModelList) {
        this.activity = mainActivity;
        this.taskModelList = taskModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tasks, parent, false);
        //firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskModel taskModel = taskModelList.get(position);
        holder.checkBox.setText(taskModel.getName());
        holder.degree.setText(taskModel.getDegree());
        holder.desceiption.setText(taskModel.getDescription());
        holder.checkBox.setChecked(toBoolean(taskModel.getStatus()));

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    firestore.collection("task").document(taskModel.TaskId).update("status",1);
//                }else {
//                    firestore.collection("task").document(taskModel.TaskId).update("status",0);
//                }
//            }
//        });
    }

    private boolean toBoolean(int status) {
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView desceiption;
        TextView degree;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desceiption = itemView.findViewById(R.id.description);
            degree = itemView.findViewById(R.id.degree);
            checkBox = itemView.findViewById(R.id.name);
        }
    }

}
