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
        userName = findViewById(R.id.memberProfileName);
        userDegree = findViewById(R.id.memberProfileDegree);
        userEmail = findViewById(R.id.memberProfileEmail);
        userDescription=findViewById(R.id.memberProfileDescription);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
        if (user != null) {
            display();
        }
    }


    private void display() {
        firebaseFirestore.collection("Users").document(user.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                firebaseUser=documentSnapshot.toObject(User.class);

                if (firebaseUser.getImageURL()!=null&&firebaseUser.getEmail()!=null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + firebaseUser.getEmail());
                    if (storageReference!=null) {
//                        Glide.with(Activity_10_memberProfile.this /* context */)
//                                .load(storageReference)
//                                .signature(new ObjectKey(firebaseUser.getEmail())) //为了图片更新之后，缓存也更新
//                                .placeholder(R.drawable.image)//图片加载出来前，显示的图片
//                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
//                                .into(imageView);
//                        imageView.setImageURI(Uri.parse(firebaseUser.getImageURL()));
//                        imageView.setVisibility(View.VISIBLE);

                        final long ONE_MEGABYTE = 1024 * 1024;
                        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageView.setImageBitmap(bmp);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                if (user.getUserName()!=null) {
                    userName.setText(firebaseUser.getUserName());
                }else {
                    userName.setText("Anonymous");
                }
                if (user.getDegree()!=null) {
                    userDegree.setText(firebaseUser.getDegree());
                }else {
                    userDegree.setText("Secret");
                }

                if (user.getEmail()!=null) {
                    userEmail.setText(firebaseUser.getEmail());
                }else {
                    userEmail.setText("Secret");
                }
                if (user.getDescription()!=null) {
                    userDescription.setText(firebaseUser.getDescription());
                }else {
                    userDescription.setText(" ");
                }
            }
        });


    }

}
