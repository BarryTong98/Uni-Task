package comp5216.sydney.edu.au.groupassignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp5216.sydney.edu.au.groupassignment2.classtype.Assignment;
import comp5216.sydney.edu.au.groupassignment2.R;
import comp5216.sydney.edu.au.groupassignment2.classtype.Task;

public class RecycleAdapter_task_11 extends RecyclerView.Adapter<RecycleAdapter_task_11.ViewHolder> {
    private final String[] taskId;
    private final String[] assignList;
    private final String[] descriptionList;
    private final List<Task>taskList;
    Context context;


    public RecycleAdapter_task_11(Context context, Assignment assignment) {
        this.context = context;
        taskList=assignment.getTaskList();
        int length= taskList.size();

        taskId = new String[length];
        assignList = new String[length];
        descriptionList = new String[length];
        for (int i = 0; i < length; i++) {
            Task temp = taskList.get(i);
            taskId[i] = temp.getTaskId();
            assignList[i] = temp.getMemberName();
            descriptionList[i] = temp.getDescription();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_10_11_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(taskId[position]);
        holder.assign.setText("Assigned to: "+assignList[position]);
        holder.taskItem.setText(descriptionList[position]);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, assign, taskItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            assign = itemView.findViewById(R.id.itemBrief);
            taskItem = itemView.findViewById(R.id.itemContent);
        }
    }
}
