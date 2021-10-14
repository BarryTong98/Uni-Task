package comp5216.sydney.edu.au.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp5216.sydney.edu.au.myapplication.CreateAssignmentActivity;
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
        View view = LayoutInflater.from(createAssignmentActivity).inflate(R.layout.task_of_assignment, parent, false);
        //firestore  = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task taskModel = taskList.get(position);
        holder.taskName.setText(taskModel.getTaskName());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        Button toDetail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            toDetail = itemView.findViewById(R.id.toDetail);
        }
    }
}
