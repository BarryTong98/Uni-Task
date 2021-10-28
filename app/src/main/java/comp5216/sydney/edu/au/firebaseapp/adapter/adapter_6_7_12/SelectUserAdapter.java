package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.ViewHolder> {
    private ArrayList<User> userArrayList;
    private Map<User, Boolean> stateMap;
    private final ArrayList<User> selectedValues = new ArrayList<>();

    /**
     * Constructor for the SelectedAdapter
     */
    public SelectUserAdapter(ArrayList<User> userArrayList, Map<User, Boolean> stateMap) {
        this.userArrayList = userArrayList;
        this.stateMap = stateMap;
    }

    /**
     * initialize the values
     *
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_user
                , parent, false);
        return new ViewHolder(v);
    }

    /**
     * Receive the data from the adapter and set the status value which is changing with checkbox
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        User user = userArrayList.get(position);
        holder.name.setText(user.getUserName());
        holder.email.setText(user.getEmail());
        holder.checkBox.setChecked(stateMap.get(user));

        //set listener on checkbox
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            stateMap.put(user, isChecked);
            // if the box is clicked, add the user into list
            if(isChecked) {
                selectedValues.add(user);
            } else {
                selectedValues.remove(user);
            }
        });



    }


    /**
     * Get the size of adapter
     *
     */
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

    // filter method, show the filterlist
    public void filterList(ArrayList<User> filterlist) {
        userArrayList = filterlist;
        notifyDataSetChanged();
    }

    public ArrayList<User> returnData() {return selectedValues;}

}

