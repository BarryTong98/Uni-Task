package comp5216.sydney.edu.au.firebaseapp.activity.activity_6_7_12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_16;
import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_6_7_12.SelectUserAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class AddGroupMemsActivity_7 extends AppCompatActivity {
    private RecyclerView userRV;
    private ArrayList<User> userArrayList;
    private Map<User, Boolean> stateMap;
    private ArrayList<User> passArrayList;
    private SelectUserAdapter userAdapter;
    private Button btn;
    private Intent data;
    FirebaseFirestore db;
    private SharedPreferences sp;

    private final static int ADDMEMBER = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7_member_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setUpRecyclerView
        userRV = findViewById(R.id.firestore_list);
        btn = findViewById(R.id.submitBtn);
        userArrayList = new ArrayList<>();
        stateMap = new HashMap<>();
        setSubmitListeners();
        buildRecycleView();
    }

    private void buildRecycleView() {
        db = FirebaseFirestore.getInstance();
        //get all the user object from the firebase.
        userAdapter = new SelectUserAdapter(userArrayList, stateMap);
        userRV.setHasFixedSize(true);
        userRV.setLayoutManager(new LinearLayoutManager(this));
        db.collection("Users").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            User p = d.toObject(User.class);
                            userArrayList.add(p);
                            stateMap.put(p, false);
                        }
                        userAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddGroupMemsActivity_7.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(AddGroupMemsActivity_7.this, "Fail to get the data.", Toast.LENGTH_SHORT).show());

        userRV.setAdapter(userAdapter);
    }

    //active the search bar and set the listen on the searchview.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint_text));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });
        return true;
    }

    //filter the text
    private void filter(String text) {
        ArrayList<User> filteredlist = new ArrayList<>();
        for (User item : userArrayList) {
            if (item.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            userAdapter.filterList(filteredlist);
        }
    }

    //submit button
    private void setSubmitListeners() {
        btn.setOnClickListener(
                (view) -> {
                    passArrayList = userAdapter.returnData();
                    data = new Intent(AddGroupMemsActivity_7.this, Activity_16.class);
                    data.putExtra("memberReturn", passArrayList);
                    setResult(ADDMEMBER, data);
                    finish();
                });
    }

}