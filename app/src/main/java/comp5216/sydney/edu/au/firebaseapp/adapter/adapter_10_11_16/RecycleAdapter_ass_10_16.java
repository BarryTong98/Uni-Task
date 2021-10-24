package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_11;
import comp5216.sydney.edu.au.firebaseapp.classtype.Assignment;
import comp5216.sydney.edu.au.firebaseapp.R;

public class RecycleAdapter_ass_10_16 extends RecyclerView.Adapter<RecycleAdapter_ass_10_16.ViewHolder> {
    private final List<Assignment> assignmentList;
    private final String[] assignmentName;
    private final String[] dueDate;
    private final String[] percentage;
    private final String activity;
    Context context;


    public RecycleAdapter_ass_10_16(Context context, @NonNull List<Assignment> assignmentList, String activity) {
        this.context = context;
        this.assignmentList = assignmentList;
        this.activity=activity;
        int length = assignmentList.size();
        assignmentName = new String[length];
        dueDate = new String[length];
        percentage = new String[length];
        for (int i = 0; i < length; i++) {
            Assignment temp = assignmentList.get(i);
            assignmentName[i] = temp.getAssignmentName();
            dueDate[i] = temp.getDueDate();
            percentage[i] = temp.getPercentage();
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
        holder.title.setText(assignmentName[position]);
        holder.dueDate.setText("Due: "+dueDate[position]);
        holder.percent.setText(percentage[position]);
        int location= holder.getAdapterPosition();

        holder.act10_ass_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Assignment intentAssignment=assignmentList.get(location);

                Intent intent=new Intent(context, Activity_11.class);
                intent.putExtra("Assignment",intentAssignment);
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
                                    assignmentList.remove(location);
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
        return assignmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueDate, percent;
        LinearLayout act10_ass_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            dueDate = itemView.findViewById(R.id.itemBrief);
            percent = itemView.findViewById(R.id.itemContent);
            act10_ass_item=itemView.findViewById(R.id.act_3_10_11_item);
        }
    }
}
