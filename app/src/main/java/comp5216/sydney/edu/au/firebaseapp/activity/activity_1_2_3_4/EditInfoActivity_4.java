package comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;

public class EditInfoActivity_4 extends AppCompatActivity {
    EditText name, description, degree;
    Button update;
    ImageView photo;
    final String TAG = "Edit info";
    ACache mCache;
    Uri profileUri;
    private static final int MY_PERMISSIONS_REQUEST_PICK_PHOTO = 101;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_edit_info);
        name = findViewById(R.id.et_edit_username);
        degree = findViewById(R.id.et_edit_degree);
        description = findViewById(R.id.et_edit_description);
        update = findViewById(R.id.btn_edit_update);
        photo = findViewById(R.id.iv_edit_profile);

        mCache = ACache.get(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + user.getEmail());
            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(this /* context */)
                    .load(storageReference)
                    .signature(new ObjectKey(user.getEmail()))
                    .placeholder(R.drawable.image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(photo);

            User cacheUser = (User) mCache.getAsObject(user.getUid());
            if (cacheUser == null) {
                // Name, email address, and profile photo Url
                String userName = user.getDisplayName();

                name.setText(userName);
            } else {
                name.setText(cacheUser.getUserName());
                degree.setText(cacheUser.getDegree());
                description.setText(cacheUser.getDescription());
            }
        }

        //update user information and save into firebase database
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateName = name.getText().toString();
                String updateDegree = degree.getText().toString();
                String updateDes = description.getText().toString();
                DocumentReference updateUser = db.collection("Users").document(user.getUid());
                updateUser
                        .update(
                                "name", updateName,
                                "degree", updateDegree,
                                "description", updateDes
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(updateName)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(EditInfoActivity_4.this, ProfileActivity_4.class);
                                                    startActivity(intent);
                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });

                                User upUser = (User) mCache.getAsObject(user.getUid());
                                upUser.setUserName(updateName);
                                upUser.setDegree(updateDegree);
                                upUser.setDescription(updateDes);
                                mCache.put(user.getUid(),upUser);

                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });
    }
}