package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class Activity_10_memberProfile extends AppCompatActivity {

    private User user;
    ImageView imageView;
    TextView userName, userDegree, userEmail, userDescription;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_10_member_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
        if (user != null) {
            display();
        }

    }

    private void display() {
        imageView=findViewById(R.id.memberProfileImg);
        userName = findViewById(R.id.memberProfileName);
        userDegree = findViewById(R.id.memberProfileDegree);
        userEmail = findViewById(R.id.memberProfileEmail);
        userDescription=findViewById(R.id.memberProfileDescription);

        if (user.getImageURL()!=null||true) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + "123@qq.com");
            Glide.with(this /* context */)
                    .load(storageReference)
                    .signature(new ObjectKey(user.getEmail())) //为了图片更新之后，缓存也更新
                    .placeholder(R.drawable.image)//图片加载出来前，显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
                    .into(imageView);
//            imageView.setImageURI(Uri.parse(user.getImageURL()));
        }
        if (user.getUserName()!=null) {
            userName.setText(user.getUserName());
        }else {
            userName.setText("Anonymous");
        }
        if (user.getDegree()!=null) {
            userDegree.setText(user.getDegree());
        }else {
            userDegree.setText("Secret");
        }

        if (user.getEmail()!=null) {
            userEmail.setText(user.getEmail());
        }else {
            userEmail.setText("Secret");
        }
        if (user.getDescription()!=null) {
            userDescription.setText(user.getDescription());
        }else {
            userDescription.setText(" ");
        }
    }

}
