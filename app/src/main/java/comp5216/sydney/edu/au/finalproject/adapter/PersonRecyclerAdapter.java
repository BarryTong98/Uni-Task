package comp5216.sydney.edu.au.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.Person;

public class PersonRecyclerAdapter extends FirestoreRecyclerAdapter<Person, PersonRecyclerAdapter.PersonViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy", Locale.US);
    private final OnItemClickListener listener;
    private ArrayList<Person> personArrayList;
    private Context context;

    public PersonRecyclerAdapter(FirestoreRecyclerOptions<Person> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    public PersonRecyclerAdapter(FirestoreRecyclerOptions<Person> options) {
        super(options);
        this.listener = null;
    }

    public void filterList(ArrayList<Person> filterlist) {
        personArrayList = filterlist;
        notifyDataSetChanged();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView price;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.person_item_name);
            price = itemView.findViewById(R.id.person_item_email);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final PersonViewHolder holder, @NonNull int position, @NonNull final Person person) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(person.getName());
        holder.price.setText(person.getEmail());
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(holder.getAdapterPosition()));
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_person_item, parent, false);
        return new PersonViewHolder(view);
    }

}
