package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    private final ArrayList<User> selectedValues = new ArrayList<>();

    public SelectUserAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        User user = userArrayList.get(position);
        holder.name.setText(user.getUserName());
        holder.email.setText(user.getEmail());

        holder.checkBox.setOnClickListener(view -> {
            if(holder.checkBox.isChecked()) {
                selectedValues.add(user);
            } else {
                selectedValues.remove(user);
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

    public boolean Contain(ArrayList<User> a, User b) {
        for(User c : a) {
            if (c.getEmail() == b.getEmail()) return true;
        }
        return false;
    }

}

