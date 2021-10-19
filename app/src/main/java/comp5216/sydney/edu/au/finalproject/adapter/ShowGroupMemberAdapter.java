package comp5216.sydney.edu.au.finalproject.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.finalproject.CreateGroupActivity;
import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.User;

public class ShowGroupMemberAdapter extends RecyclerView.Adapter<ShowGroupMemberAdapter.ViewHolder>{
    private ArrayList<User> showArrayList;
    private CreateGroupActivity createGroupActivity;

    public ShowGroupMemberAdapter(ArrayList<User> list,CreateGroupActivity createGroupActivity) {
        this.showArrayList = list;
        this.createGroupActivity = createGroupActivity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.show_item_name);
            email = itemView.findViewById(R.id.show_item_email);
        }
    }

    @NonNull
    @Override
    public ShowGroupMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowGroupMemberAdapter.ViewHolder holder, int position) {
        User person = showArrayList.get(position);
        holder.name.setText(person.getName());
        holder.email.setText(person.getEmail());
        holder.itemView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(createGroupActivity);
            builder.setTitle("Delete User")
                    .setMessage("Do You Want To Delete This User From Group?")
                    .setPositiveButton("Delete", (dialogInterface, i) -> {
                        int location = holder.getLayoutPosition();
                        User p = showArrayList.get(location);
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
