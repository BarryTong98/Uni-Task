package comp5216.sydney.edu.au.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.User;

public class AddGroupMemberAdapter extends RecyclerView.Adapter<AddGroupMemberAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    private final ArrayList<User> selectedValues = new ArrayList<>();

    public AddGroupMemberAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;

    }



    @NonNull
    @Override
    public AddGroupMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddGroupMemberAdapter.ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());

        holder.checkBox.setOnClickListener(view -> {
            if(holder.checkBox.isChecked()) {
                System.out.println("yes");
                selectedValues.add(user);
            } else {
                System.out.println("no");
                selectedValues.add(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView email;
        private final CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_item_name);
            email = itemView.findViewById(R.id.user_item_email);
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }

    public void filterList(ArrayList<User> filterllist) {
        userArrayList = filterllist;
        notifyDataSetChanged();
    }

    public ArrayList<User> returnData() {return selectedValues;}

}

