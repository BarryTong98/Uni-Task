package comp5216.sydney.edu.au.finalproject.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.finalproject.AssignmentListActivity;
import comp5216.sydney.edu.au.finalproject.CreateGroupActivity;
import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.Assignment;
import comp5216.sydney.edu.au.finalproject.model.User;

public class ShowAssignmentAdapter extends RecyclerView.Adapter<ShowAssignmentAdapter.ViewHolder> {
    private ArrayList<Assignment> showArrayList;
    private AssignmentListActivity assignmentListActivity;

    public ShowAssignmentAdapter(ArrayList<Assignment> list,AssignmentListActivity assignmentListActivity) {
        this.showArrayList = list;
        this.assignmentListActivity = assignmentListActivity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView toDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.assignment_name);
            toDetail = itemView.findViewById(R.id.todetail);
        }
    }

    @NonNull
    @Override
    public ShowAssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_aaignment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAssignmentAdapter.ViewHolder holder, int position) {
        Assignment assignment = showArrayList.get(position);
        holder.name.setText(assignment.getName());


        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(assignmentListActivity);
            builder.setTitle("Delete User")
                    .setMessage("Do You Want To Delete This Assignment ?")
                    .setPositiveButton("Delete", (dialogInterface, i) -> {
                        int location = holder.getLayoutPosition();
                        Assignment a = showArrayList.get(location);
                        showArrayList.remove(location);
                        notifyItemRemoved(location);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {

                    });
            builder.create().show();
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return showArrayList.size();
    }


}
