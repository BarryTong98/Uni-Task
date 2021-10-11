package comp5216.sydney.edu.au.finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.finalproject.adapter.DiscussionAdapter;
import comp5216.sydney.edu.au.finalproject.model.Discussion;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Final Project";

    Button btnDiscussion;
    ActivityResultLauncher<Intent> launcher;
    private FirebaseFirestore firebaseFirestore;
    private int groupID = 1;
    private ArrayList<Discussion> discussions;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDiscussion = findViewById(R.id.btnDiscussion);
        getDiscussions();

    }

    private void getDiscussions() {
        discussions = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("discussions")
                .whereEqualTo("groupID", groupID)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Discussion discussion = document.toObject(Discussion.class);
                                discussions.add(discussion);
                            }
                            intent = new Intent(MainActivity.this, DiscussionListActivity.class);
                            if (intent != null) {
                                intent.putExtra("discussions", discussions);
                                intent.putExtra("groupID", 1);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void click(View view) {
        startActivity(intent);
    }
}