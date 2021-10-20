package comp5216.sydney.edu.au.firebaseapp.activity.activity_13_14_15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import comp5216.sydney.edu.au.firebaseapp.R;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.adapter.adapter_13_14_15.CommentAdapter;
import comp5216.sydney.edu.au.firebaseapp.classtype.Comment;
import comp5216.sydney.edu.au.firebaseapp.classtype.Discussion;
import comp5216.sydney.edu.au.firebaseapp.util.IdUtil;


public class DiscussionActivity extends AppCompatActivity {
    public final String TAG = "FinalProject";

    private TextView tvTitle;
    private TextView tvDescription;
    private ListView lvCommends;
    private EditText etInput;
    private Button btnSubmit;
    private List<Comment> commentList;
    private Discussion discussion;
    private String discussionID;
    private String userName;
    private String userEmail;
    private CommentAdapter commentAdapter;
    private FirebaseAuth mAuth;


    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        lvCommends = findViewById(R.id.lvCommends);
        etInput = findViewById(R.id.etInput);
        btnSubmit = findViewById(R.id.btnSubmit);
        discussionID = getIntent().getStringExtra("discussionID");
        userName = firebaseUser.getDisplayName();
        userEmail = firebaseUser.getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getComments(discussionID);

    }

    private void getComments(String discussionID) {
        DocumentReference documentReference = firebaseFirestore.collection("discussions").document(discussionID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                discussion = documentSnapshot.toObject(Discussion.class);
                System.out.println("discussion" + discussion.toString());
                commentList = discussion.getComments();
                commentAdapter = new CommentAdapter(commentList, DiscussionActivity.this);
                lvCommends.setAdapter(commentAdapter);
                tvTitle.setText(discussion.getTitle());
                tvDescription.setText(discussion.getDescription());
            }
        });
    }

    private void updateComments() {
        DocumentReference documentReference = firebaseFirestore.collection("discussions").document(discussion.getDiscussionID());
        documentReference.update("comments", commentList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error updating document", e);
            }
        });
    }

    public void clickPost(View view) {
        String content = etInput.getText().toString();
        if (!content.isEmpty()) {
            Comment comment = new Comment(IdUtil.getUUID("C"), userName,userEmail , content, new Date());
            commentList.add(comment);
            discussion.setComments(commentList);
            updateComments();
            commentAdapter.notifyDataSetChanged();
            etInput.setText("");
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}