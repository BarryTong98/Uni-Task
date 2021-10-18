package comp5216.sydney.edu.au.finalproject;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import comp5216.sydney.edu.au.finalproject.adapter.PersonRecyclerAdapter;
import comp5216.sydney.edu.au.finalproject.model.Person;


public class NoUseActivity extends AppCompatActivity {
    private static final String TAG = "FirestoreSearchActivity";
    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private RecyclerView mPersonsRecycler;
    private PersonRecyclerAdapter adapter;
    private static final String PEOPLE = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_search);

        mPersonsRecycler = findViewById(R.id.firestore_list);
        mPersonsRecycler.setHasFixedSize(true);
        mPersonsRecycler.setLayoutManager(new LinearLayoutManager(this));

        //Query
        Query query = mFirestore.collection(PEOPLE);
        //RecyclerOptions
        FirestoreRecyclerOptions<Person> options = new FirestoreRecyclerOptions.Builder<Person>()
                .setQuery(query, Person.class)
                .build();

        adapter = new PersonRecyclerAdapter(options, new PersonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Person person = adapter.getSnapshots().getSnapshot(position).toObject(Person.class);
                String id = adapter.getSnapshots().getSnapshot(position).getId();
                //mFirestore.collection(PEOPLE).document(id).delete();

                assert person != null;
                Toast.makeText(getApplicationContext(),"Deleting " + person.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        mPersonsRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        ArrayList<Person> personList = new ArrayList<>();
        mFirestore.collection(PEOPLE)
                .get()
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Person p = document.toObject(Person.class);
                            personList.add(p);
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }

                        ArrayList<Person> filteredlist = new ArrayList<>();
                        for(Person item : personList) {

                            if(item.getName().toLowerCase().contains(text.toLowerCase())) {
                                System.out.println("111");
                                filteredlist.add(item);
                            }
                        }

                        if(filteredlist.isEmpty()) {
                            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.filterList(filteredlist);
                            mPersonsRecycler.setAdapter(adapter);
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }


    public void onSubmit(View view) {
    }
}
