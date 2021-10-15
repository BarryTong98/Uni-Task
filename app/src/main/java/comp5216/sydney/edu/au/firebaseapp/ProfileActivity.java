package comp5216.sydney.edu.au.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import comp5216.sydney.edu.au.firebaseapp.model.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvName, tvEmail;
    private ImageView imageView;
    private Button btnSignout,btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = findViewById(R.id.tv_profile_username);
        tvEmail = findViewById(R.id.tv_profile_email);
        imageView = findViewById(R.id.iv_profile_photo);
        btnSignout = findViewById(R.id.btn_profile_signout);
        btnEdit = findViewById(R.id.btn_profile_edit);

        //访问用户信息
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            System.out.println("user display name:" + name);
            String email = user.getEmail();

            // Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + user.getEmail());

            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(this /* context */)
                    .load(storageReference)
                    .into(imageView);

            tvName.setText(name);
            tvEmail.setText(email);
        }

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditInfoActivity.class);
                startActivity(intent);
            }
        });
    }

}