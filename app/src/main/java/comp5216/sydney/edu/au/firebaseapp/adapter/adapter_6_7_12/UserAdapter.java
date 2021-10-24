package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.CreateTaskActivity_9;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> taskModelList;
    private CreateTaskActivity_9 activity;
    private FirebaseFirestore firestore;

    public UserAdapter(CreateTaskActivity_9 mainActivity, List<User> taskModelList) {
        this.activity = mainActivity;
        this.taskModelList = taskModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_tasks, parent, false);
        firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User taskModel = taskModelList.get(position);
        holder.checkBox.setText(taskModel.getUserName());
        holder.degree.setText(taskModel.getDegree());
        holder.desceiption.setText(taskModel.getDescription());
        holder.checkBox.setChecked(toBoolean(taskModel.getStatus()));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                taskModel.setStatus(1);
                System.out.println(taskModel.getUserName()+"  taskModel.setStatus: "+taskModel.getStatus());
                //firestore.collection("task").document(taskModel.getUserId()).update("status",1);
            }else {
                taskModel.setStatus(0);
                System.out.println(taskModel.getUserName()+"  taskModel.setStatus: "+taskModel.getStatus());
                //firestore.collection("task").document(taskModel.getUserId()).update("status",0);
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
