package comp5216.sydney.edu.au.finalproject.adapter;

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

import comp5216.sydney.edu.au.finalproject.CreateTaskActivity;
import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> taskModelList;
    private CreateTaskActivity activity;
    private FirebaseFirestore firestore;

    public UserAdapter(CreateTaskActivity mainActivity, List<User> taskModelList) {
        this.activity = mainActivity;
        this.taskModelList = taskModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tasks, parent, false);
        firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User taskModel = taskModelList.get(position);
        holder.checkBox.setText(taskModel.getName());
        holder.degree.setText(taskModel.getDegree());
        holder.desceiption.setText(taskModel.getDescription());
        holder.checkBox.setChecked(toBoolean(taskModel.getStatus()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    taskModel.setStatus(1);
                    System.out.println(taskModel.getName()+"  taskModel.setStatus: "+taskModel.getStatus());
                    //firestore.collection("task").document(taskModel.getUserId()).update("status",1);
                }else {
                    taskModel.setStatus(0);
                    System.out.println(taskModel.getName()+"  taskModel.setStatus: "+taskModel.getStatus());
                    //firestore.collection("task").document(taskModel.getUserId()).update("status",0);
                }
            }
        });
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
