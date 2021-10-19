package comp5216.sydney.edu.au.finalproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import comp5216.sydney.edu.au.finalproject.CreateAssignmentActivity;
import comp5216.sydney.edu.au.finalproject.CreateTaskActivity;
import comp5216.sydney.edu.au.finalproject.barryModel.TaskModel;
import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private List<Task> taskList;
    private CreateAssignmentActivity createAssignmentActivity;
    private FirebaseFirestore firestore;

    public TaskAdapter(List<Task> taskList, CreateAssignmentActivity createAssignmentActivity) {
        this.taskList = taskList;
        this.createAssignmentActivity = createAssignmentActivity;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(createAssignmentActivity).inflate(R.layout.taskview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println("TASKLIST**************START***********************");
        for(Task i : taskList){
            System.out.println(i);
        }
        System.out.println("TASKLIST**************END***********************");
        Context context = createAssignmentActivity.getApplicationContext();
        Task taskModel = taskList.get(position);

        holder.taskName.setText(taskModel.getTaskName());
        holder.toDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createAssignmentActivity,CreateTaskActivity.class);
                intent.putExtra("task",taskModel);
                createAssignmentActivity.startActivity(intent);
                Toast.makeText(context, taskModel.getTaskName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.taskName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(createAssignmentActivity);
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
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                taskList.remove(location);
                                                notifyItemRemoved(location);
                                                Log.d("Remove", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Remove", "Error deleting document", e);
                                            }
                                        });
                                // Remove item from the ArrayList
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface
                                .OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog
                                // Nothing happens
                            }
                        });
                builder.create().show();
                return true;
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