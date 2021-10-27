package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.classtype.Assignment;
import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;

public class RecycleAdapter_task_11 extends RecyclerView.Adapter<RecycleAdapter_task_11.ViewHolder> {
    private final String[] taskName;
    private final String[] assignList;
    private final String[] descriptionList;
    private final List<Tasks> tasksList;
    Context context;


    public RecycleAdapter_task_11(Context context, Assignment assignment) {
        this.context = context;
        tasksList =assignment.getTaskList();
        int length= tasksList.size();

        taskName = new String[length];
        assignList = new String[length];
        descriptionList = new String[length];
        for (int i = 0; i < length; i++) {
            Tasks temp = tasksList.get(i);
            taskName[i] = temp.getTaskName();
            assignList[i] = temp.getMemberName();
            descriptionList[i] = temp.getDescription();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_3_10_11_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(taskName[position]);
        holder.assign.setText("Assigned to: "+assignList[position]);
        holder.taskItem.setText(descriptionList[position]);
        holder.iv.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, assign, taskItem;
        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            assign = itemView.findViewById(R.id.itemBrief);
            taskItem = itemView.findViewById(R.id.itemContent);
            iv = itemView.findViewById(R.id.iv_task);
        }
    }
}
