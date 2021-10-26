package comp5216.sydney.edu.au.firebaseapp.activity.activity_13_14_15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import comp5216.sydney.edu.au.firebaseapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_13_14_15.AssignmentAdapter;


public class CreateDiscussionActivity_14 extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private Button btnPost;
    private String groupID;
    private String userID;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_14_create_discussion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnPost = findViewById(R.id.btnPost);
        groupID = getIntent().getStringExtra("groupID");
        userID = getIntent().getStringExtra("userName");
        initFirestore();
    }

    /**
     * connect with the firebase
     */
    private void initFirestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    /**
     * when user click post button, jump to DiscussionListActivity
     *
     * @param view
     */
    public void clickPost(View view) {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        Intent intent = new Intent(CreateDiscussionActivity_14.this
                , DiscussionListActivity_13.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        setResult(RESULT_OK, intent);
        finish();
    }
}