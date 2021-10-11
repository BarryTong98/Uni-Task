package comp5216.sydney.edu.au.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import comp5216.sydney.edu.au.finalproject.adapter.CommentAdapter;
import comp5216.sydney.edu.au.finalproject.model.Comment;
import comp5216.sydney.edu.au.finalproject.model.Discussion;
import comp5216.sydney.edu.au.finalproject.util.IdUtil;

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
    private CommentAdapter commentAdapter;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        lvCommends = findViewById(R.id.lvCommends);
        etInput = findViewById(R.id.etInput);
        btnSubmit = findViewById(R.id.btnSubmit);
        discussionID = getIntent().getStringExtra("discussionID");
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
            Comment comment = new Comment(IdUtil.getUUID("C"), 1, content, new Date());
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