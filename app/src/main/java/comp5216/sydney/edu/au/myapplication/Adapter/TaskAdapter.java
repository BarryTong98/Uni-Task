package comp5216.sydney.edu.au.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp5216.sydney.edu.au.myapplication.CreateAssignmentActivity;
import comp5216.sydney.edu.au.myapplication.MainActivity;
import comp5216.sydney.edu.au.myapplication.R;
import comp5216.sydney.edu.au.myapplication.model1.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private List<Task> taskList;
    private CreateAssignmentActivity createAssignmentActivity;

    public TaskAdapter(List<Task> taskList, CreateAssignmentActivity createAssignmentActivity) {
        this.taskList = taskList;
        this.createAssignmentActivity = createAssignmentActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(createAssignmentActivity).inflate(R.layout.taskview, parent, false);
        //firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Context context = createAssignmentActivity.getApplicationContext();
        Task taskModel = taskList.get(position);
        holder.taskName.setText(taskModel.getTaskName());
        holder.toDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createAssignmentActivity,MainActivity.class);
                intent.putExtra("task",taskModel);
                createAssignmentActivity.startActivity(intent);
                Toast.makeText(context, taskModel.getTaskName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        ImageView toDetail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            toDetail = itemView.findViewById(R.id.toDetail);
        }
    }
}
