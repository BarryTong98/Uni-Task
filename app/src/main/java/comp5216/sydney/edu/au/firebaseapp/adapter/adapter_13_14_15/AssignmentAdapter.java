package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_13_14_15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import comp5216.sydney.edu.au.firebaseapp.R;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private List<String> assignments;
    private Map<Integer, Boolean> map;
    private Context activity;
    private int selectedPosition = -1;

    public AssignmentAdapter(List<String> assignments, Context activity,HashMap<Integer,Boolean> map) {
        this.assignments = assignments;
        this.activity = activity;
        this.map=map;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    public Boolean checked(int i){
        if(map.get(i)==null||map.get(i)==false){
            return false;
        }
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        boolean flag = false;
        String name = assignments.get(position);
        holder.checkBox.setText(name);
        holder.checkBox.setChecked(checked(position));
        holder.description.setText("description.....");
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedPosition=holder.getAdapterPosition();
                if(isChecked==true){
                    map.put(selectedPosition,true);
                }else {
                    map.remove(selectedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }


    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        CheckBox checkBox;


        public AssignmentViewHolder(View view) {
            super(view);
            this.description = view.findViewById(R.id.aDescription);
            this.checkBox = view.findViewById(R.id.aName);
        }
    }
}

