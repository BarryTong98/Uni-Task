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
import comp5216.sydney.edu.au.finalproject.model.Person;

public class AddGroupMemberAdapter extends RecyclerView.Adapter<AddGroupMemberAdapter.ViewHolder> {
    private ArrayList<Person> personArrayList;
    private final ArrayList<Person> selectedValues = new ArrayList<>();

    public AddGroupMemberAdapter(ArrayList<Person> personArrayList) {
        this.personArrayList = personArrayList;

    }



    @NonNull
    @Override
    public AddGroupMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_person_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddGroupMemberAdapter.ViewHolder holder, int position) {
        Person person = personArrayList.get(position);
        holder.name.setText(person.getName());
        holder.email.setText(person.getEmail());

        holder.checkBox.setOnClickListener(view -> {
            if(holder.checkBox.isChecked()) {
                System.out.println("yes");
                selectedValues.add(person);
            } else {
                System.out.println("no");
                selectedValues.add(person);
            }
        });

    }

    @Override
    public int getItemCount() {
        return personArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView email;
        private final CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.person_item_name);
            email = itemView.findViewById(R.id.person_item_email);
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }

    public void filterList(ArrayList<Person> filterllist) {
        personArrayList = filterllist;
        notifyDataSetChanged();
    }

    public ArrayList<Person> returnData() {return selectedValues;}

}

