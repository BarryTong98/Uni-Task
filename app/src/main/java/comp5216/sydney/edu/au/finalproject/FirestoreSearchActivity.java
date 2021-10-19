package comp5216.sydney.edu.au.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import comp5216.sydney.edu.au.finalproject.adapter.UserRecyclerAdapter;
import comp5216.sydney.edu.au.finalproject.model.Person;

public class FirestoreSearchActivity extends AppCompatActivity {

    private static final String PEOPLE = "Users";

    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private UserRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_search);

        RecyclerView recyclerView = findViewById(R.id.firestore_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Query query = mDb.collection(PEOPLE);
        //RecyclerOptions
        FirestoreRecyclerOptions<Person> options = new FirestoreRecyclerOptions.Builder<Person>()
                .setQuery(query, Person.class)
                .build();

        mAdapter = new UserRecyclerAdapter(options);
        recyclerView.setAdapter(mAdapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}
