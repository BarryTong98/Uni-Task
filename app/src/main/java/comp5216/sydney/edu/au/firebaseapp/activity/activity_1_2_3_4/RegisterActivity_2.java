package comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;
import comp5216.sydney.edu.au.firebaseapp.util.Compressor;

public class RegisterActivity_2 extends AppCompatActivity {
    private EditText etUsername,etEmail,etPassword,etConfirm;
    private ImageView ivProfile;
    private TextView tv;
    private Button btRegister;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;

    //profile uri
    Uri profileUri;

    private static final int MY_PERMISSIONS_REQUEST_PICK_PHOTO = 101;
    final String TAG="REGISTER";

    //缓存
    ACache mCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_register_activity);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_reg_password);
        etConfirm = findViewById(R.id.et_reg_password_confirm);
        ivProfile = findViewById(R.id.iv_profile);
        btRegister = findViewById(R.id.bt_reg_register);
        tv = findViewById(R.id.tv_register_signin);
        mCache = ACache.get(this);

        //connect with Firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfile();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity_2.this,LoginActivity_1.class);
                startActivity(intent);
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirm.getText().toString();

                if (username.length() < 1){
                    etUsername.setError("Please input username!");
                }else if (email.length() < 10){
                    etEmail.setError("Email format error!");
                }else if (password.length() < 6){
                    etPassword.setError("Please set a password longer than 6 digits!");
                }else if (!confirmPassword.equals(password)){
                    etConfirm.setError("Those passwords don’t match. Try again.");
                }
                else {
                    if (profileUri == null){
                        Toast.makeText(RegisterActivity_2.this, "Please upload a avatar!", Toast.LENGTH_SHORT).show();
                    }else {
                        register(username,email,password);
                    }
                }

            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    //上传头像到firebase storage
    private void changeProfile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,MY_PERMISSIONS_REQUEST_PICK_PHOTO);

    }

    //注册方法
    private void register(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Register", "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            //set Firebase user display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();

                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });


                            //把头像上传到storage
                            StorageReference ref = storageReference.child("profile/" + firebaseUser.getEmail());
                            String path=Compressor.getRealFilePath(RegisterActivity_2.this,profileUri);
                            Bitmap bitmap= Compressor.getSmallBitmap(path);
                            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
                            ref.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            String imgUrl = task.toString();
                                            String uid = firebaseUser.getUid();

                                            User user = new User();
                                            user.setUserId(uid);
                                            user.setImageURL(imgUrl);
                                            user.setEmail(email);
                                            user.setUserName(username);

                                            //放在缓存中
                                            mCache.put(uid, user);

                                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                                            //上传用户信息
                                            db.collection("Users").document(firebaseUser.getUid())
                                                    .set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(RegisterActivity_2.this, "Register successfully!", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(RegisterActivity_2.this, HomeActivity_3.class);
                                                            startActivity(intent);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error writing document", e);
                                                        }
                                                    });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity_2.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity_2.this, "The email address is already in use by another account.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_PICK_PHOTO){
            if (resultCode == RESULT_OK){
                ivProfile.setImageURI(data.getData());
                profileUri = data.getData();
            }
        }
    }


}