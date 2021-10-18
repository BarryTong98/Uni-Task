package comp5216.sydney.edu.au.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.finalproject.adapter.AddGroupMemberAdapter;
import comp5216.sydney.edu.au.finalproject.model.Person;

public class AddGroupMemsActivity extends AppCompatActivity {
    private RecyclerView personRV;
    private ArrayList<Person> personArrayList;
    private ArrayList<Person> passArrayList;
    private AddGroupMemberAdapter personAdapter;
    private Button btn;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_search);
        //setUpRecyclerView
        personRV = findViewById(R.id.firestore_list);
        btn = findViewById(R.id.submitBtn);
//        data = getIntent();
//        passArrayList = (ArrayList<Person>) data.getSerializableExtra("l");
        setSubmitListeners();
        buildRecycleView();
    }

    private void buildRecycleView() {
        personArrayList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        personAdapter = new AddGroupMemberAdapter(personArrayList);
        personRV.setHasFixedSize(true);
        personRV.setLayoutManager(new LinearLayoutManager(this));
        //get data
        db.collection("Users").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d: list) {
                            Person p = d.toObject(Person.class);
                            personArrayList.add(p);
                        }
                        personAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddGroupMemsActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(AddGroupMemsActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show());
        System.out.println("tzy   " + personArrayList.size());
        personRV.setAdapter(personAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        ArrayList<Person> filteredlist = new ArrayList<>();

        for (Person item : personArrayList) {
            if (item.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            personAdapter.filterList(filteredlist);
        }
    }

    private void setSubmitListeners() {

        btn.setOnClickListener(
                (view) -> {

                    passArrayList = personAdapter.returnData();
                    System.out.println(passArrayList.get(0).getName());
                    System.out.println(passArrayList.size());
                    data = new Intent(AddGroupMemsActivity.this, CreateGroupActivity.class);
                    data.putExtra("ml", passArrayList);
                    setResult(RESULT_OK, data);
                    finish();
        });
    }

//    public void onSubmit(View v) {
//        ArrayList<Person> list = personAdapter.returnData();
//        System.out.println(list.size());
//        data.putExtra("memberList", list);
//        setResult(RESULT_OK, data);
//        finish();
//    }

}