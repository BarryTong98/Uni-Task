package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class Activity_10_memberProfile extends AppCompatActivity {

    private User user;
    private User firebaseUser;
    ImageView imageView;
    TextView userName, userDegree, userEmail, userDescription;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_member_profile);
        imageView=findViewById(R.id.memberProfileImg);
        imageView.setVisibility(View.INVISIBLE);
        userName = findViewById(R.id.memberProfileName);
        userName.setVisibility(View.INVISIBLE);
        userDegree = findViewById(R.id.memberProfileDegree);
        userDegree.setVisibility(View.INVISIBLE);
        userEmail = findViewById(R.id.memberProfileEmail);
        userEmail.setVisibility(View.INVISIBLE);
        userDescription=findViewById(R.id.memberProfileDescription);
        userDescription.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
        if (user != null) {
            display();
        }
    }

    /**
     * get data from firebase and show the user profile
     */
    private void display() {
        firebaseFirestore.collection("Users").document(user.getUserId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                firebaseUser=documentSnapshot.toObject(User.class);
                if (firebaseUser.getImageURL()!=null&&firebaseUser.getEmail()!=null) {
                    StorageReference storageReference = FirebaseStorage.getInstance()
                            .getReference().child("profile/" + firebaseUser.getEmail());
                    if (storageReference!=null) {
                        imageView.setVisibility(View.VISIBLE);
                        Glide.with(Activity_10_memberProfile.this /* context */)
                                .load(storageReference)
                                .signature(new ObjectKey(firebaseUser.getEmail())) //update cache
                                .placeholder(R.drawable.image)//show picture before loading
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// transfer picture size
                                .into(imageView);

                    }
                }

                if (user.getUserName()!=null) {
                    userName.setVisibility(View.VISIBLE);
                    userName.setText(firebaseUser.getUserName());
                }

                if (user.getDegree()!=null) {
                    userDegree.setVisibility(View.VISIBLE);
                    userDegree.setText(firebaseUser.getDegree());
                }
                else {
                    userDegree.setVisibility(View.VISIBLE);
                    userDegree.setText("Secret");
                }
                if (user.getEmail()!=null) {
                    userEmail.setVisibility(View.VISIBLE);
                    userEmail.setText(firebaseUser.getEmail());
                }

                if (user.getDescription()!=null) {
                    userDescription.setVisibility(View.VISIBLE);
                    userDescription.setText(firebaseUser.getDescription());
                }

            }
        });


    }

}
