package comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_5_8_9.TaskListActivity_5;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;

public class ProfileActivity_4 extends AppCompatActivity {
    private TextView tvName, tvEmail,tvDegree,tvDescription;
    private ImageView imageView;
    private Button btnSignout,btnEdit,btnHome,btnTask;
    ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_profile);
        tvName = findViewById(R.id.tv_profile_username);
        tvEmail = findViewById(R.id.tv_profile_email);
        tvDegree = findViewById(R.id.tv_profile_degree);
        tvDescription = findViewById(R.id.tv_profile_description);

        imageView = findViewById(R.id.iv_profile_photo);
        btnSignout = findViewById(R.id.btn_profile_signout);
        btnEdit = findViewById(R.id.btn_profile_edit);
        btnHome = findViewById(R.id.btn_profile_home);
        btnTask = findViewById(R.id.btn_profile_task);
        mCache = ACache.get(this);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity_4.this,HomeActivity_3.class);
                startActivity(intent);
            }
        });

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity_4.this, TaskListActivity_5.class);
                startActivity(intent);
            }
        });
        //访问用户信息
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + user.getEmail());

            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(this /* context */)
                    .load(storageReference)
                    .signature(new ObjectKey(user.getEmail())) //为了图片更新之后，缓存也更新
                    .placeholder(R.drawable.image)//图片加载出来前，显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
                    .into(imageView);


            User cacheUser = (User) mCache.getAsObject(user.getUid());
            if (cacheUser == null) {
                // Name, email address, and profile photo Url
                String name = user.getDisplayName();
                System.out.println("user display name:" + name);
                String email = user.getEmail();

                tvName.setText(name);
                tvEmail.setText(email);
            }else {
                tvName.setText(cacheUser.getUserName());
                tvEmail.setText(cacheUser.getEmail());
                tvDegree.setText(cacheUser.getDegree());
                tvDescription.setText(cacheUser.getDescription());

                System.out.println("这个user是在cache中："+cacheUser.getDegree());
            }
        }

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //把缓存清除
                mCache.clear();
                Intent intent = new Intent(ProfileActivity_4.this,LoginActivity_1.class);
                startActivity(intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity_4.this, EditInfoActivity_4.class);
                startActivity(intent);
            }
        });
    }

}