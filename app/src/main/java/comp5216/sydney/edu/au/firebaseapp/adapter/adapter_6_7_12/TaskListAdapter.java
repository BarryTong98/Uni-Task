package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.CreateAssignmentActivity_8;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {
    private List<Tasks> taskList;
    private CreateAssignmentActivity_8 createAssignmentActivity8;
    private FirebaseFirestore firestore;
    public static final int Request = 1002;
    private Context context;
    private ItemListener itemListener;

    public TaskListAdapter(Context context, List<Tasks> taskList, CreateAssignmentActivity_8 createAssignmentActivity8) {
        this.context = context;
        this.taskList = taskList;
        this.createAssignmentActivity8 = createAssignmentActivity8;
        firestore = FirebaseFirestore.getInstance();
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(createAssignmentActivity8).inflate(R.layout.item_taskview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tasks taskModel = taskList.get(position);
        holder.taskName.setText(taskModel.getTaskName());

        holder.toDetail.setOnClickListener(view -> {
            int p = holder.getLayoutPosition();
            itemListener.onItemClick(p);
        });

        holder.taskName.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(createAssignmentActivity8);
            builder.setTitle("Delete Task")
                    .setMessage("Do You Want To Delete This Task?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int location= holder.getLayoutPosition();
                            System.out.println("TASKLIST**************LOCATION:   "+location+"***********************");
                            System.out.println("********location: "+location);
                            System.out.println("********i: "+i);
                            System.out.println(taskList.get(location).getTaskName());
                            firestore.collection("tasks").document(taskList.get(location).getTaskId())
                                    .delete()
                                    .addOnSuccessListener(aVoid -> {
                                        taskList.remove(location);
                                        notifyItemRemoved(location);
                                        Log.d("Remove", "DocumentSnapshot successfully deleted!");
                                    })
                                    .addOnFailureListener(e -> Log.w("Remove", "Error deleting document", e));
                            // Remove item from the ArrayList
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        // User cancelled the dialog
                        // Nothing happens
                    });
            builder.create().show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface ItemListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

}