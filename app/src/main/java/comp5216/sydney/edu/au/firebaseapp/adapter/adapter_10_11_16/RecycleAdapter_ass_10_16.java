package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_11;
import comp5216.sydney.edu.au.firebaseapp.classtype.Assignment;
import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.Tasks;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class RecycleAdapter_ass_10_16 extends RecyclerView.Adapter<RecycleAdapter_ass_10_16.ViewHolder> {
    private List<Assignment> firebaseAssignmentList;
    private List<Tasks> firebaseTaskList;
    private final String activity;
    Context context;
    private CardView cv;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    public RecycleAdapter_ass_10_16(Context context, @NonNull List<Assignment> assignmentList, String activity) {
        this.context = context;
        this.activity = activity;
        this.firebaseAssignmentList = assignmentList;

        firebaseFirestore.collection("assignments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Assignment> list= queryDocumentSnapshots.toObjects(Assignment.class);
                Map<String,Assignment> map=new TreeMap<>();
                for(Assignment i:assignmentList){
                    map.put(i.getAssignmentId(),i);
                }

                for (Assignment j:list){
                    if (map.containsKey(j.getAssignmentId())){
                        map.put(j.getAssignmentId(),j);
                    }
                }
                firebaseAssignmentList=new ArrayList<>(map.values());
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_3_10_11_item, parent, false);
        cv = (CardView)view.findViewById(R.id.cardView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int location=position;
        holder.title.setText(firebaseAssignmentList.get(location).getAssignmentName() );
        holder.dueDate.setText("Due: " + firebaseAssignmentList.get(location).getDueDate());
        holder.iv.setVisibility(View.VISIBLE);

        firebaseFirestore.collection("tasks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                firebaseTaskList=queryDocumentSnapshots.toObjects(Tasks.class);

                holder.percent.setText(getPercentage(firebaseAssignmentList.get(location).getAssignmentId()));

            }
        });


//        int location = holder.getAdapterPosition();

        holder.act10_ass_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Assignment intentAssignment = firebaseAssignmentList.get(location);

                Intent intent = new Intent(context, Activity_11.class);
                intent.putExtra("Assignment", intentAssignment);
                context.startActivity(intent);
            }
        });

        if (activity.equalsIgnoreCase("activity16")) {

            holder.act10_ass_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.dialog_delete_title)
                            .setMessage(R.string.dialog_delete_msg)
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    firebaseAssignmentList.remove(location);
                                    notifyItemRemoved(location);
                                    // Remove item from the ArrayList
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface
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

    }

    @Override
    public int getItemCount() {
        return firebaseAssignmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueDate, percent;
        ImageView iv;
        LinearLayout act10_ass_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            dueDate = itemView.findViewById(R.id.itemBrief);
            percent = itemView.findViewById(R.id.itemContent);
            act10_ass_item = itemView.findViewById(R.id.act_3_10_11_item);
            iv = itemView.findViewById(R.id.iv_ass);
        }
    }

    private String getPercentage(String assignmentId){
        float percentage=0;
        List<Tasks>tempTaskList=new ArrayList<>();
        if (firebaseTaskList!=null) {
            for (Tasks tasks : firebaseTaskList) {
                if (tasks.getAssignmentId().equalsIgnoreCase(assignmentId)) {
                    tempTaskList.add(tasks);
                }
            }
        }

        if (tempTaskList!=null&&tempTaskList.size()>0) {
            for (Tasks i : tempTaskList) {
                if (i.getComplete() == 1) {
                    percentage++;
                }
            }
            percentage /= tempTaskList.size();

            String s = String.format("Completed:");
            if (percentage == 0.0) {
                s += " 0%";
            } else {
                s += String.format(" %.1f%%", Math.round(percentage * 1000) / 10.0);
            }
            return s;
        }else {
            return "Don't have any tasks.";
        }
    }

}
