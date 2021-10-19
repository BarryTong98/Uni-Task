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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String TAG = "Edit info";
    ACache mCache;

    //profile uri
    Uri profileUri;
    private static final int MY_PERMISSIONS_REQUEST_PICK_PHOTO = 101;

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

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePhoto();
            }
        });

        //获取缓存中的用户数据
        //访问用户信息
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Reference to an image file in Cloud Storage
            storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + user.getEmail());

            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            Glide.with(this /* context */)
                    .load(storageReference)
                    .signature(new ObjectKey(System.currentTimeMillis())) //为了图片更新之后，缓存也更新
                    .placeholder(R.drawable.default_profile)//图片加载出来前，显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的信息
                String updateName = name.getText().toString();
                String updateDegree = degree.getText().toString();
                String updateDes = description.getText().toString();

                //更新
                DocumentReference updateUser = db.collection("Users").document(user.getUid());
                List<String> testlist = new ArrayList<>();
                testlist.add("1");
                testlist.add("2");
                updateUser
                        .update(
                                "name", updateName,
                                "degree", updateDegree,
                                "description", updateDes,
                                "groupList", testlist
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //用户信息
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

                                //把登录的用户信息放在缓存中
                                User upUser = (User) mCache.getAsObject(user.getUid());
                                upUser.setUserName(updateName);
                                upUser.setDegree(updateDegree);
                                upUser.setDescription(updateDes);
                                mCache.put(user.getUid(),upUser);

                                //如果用户没有修改头像
                                if (profileUri != null) {
                                    //删除头像存储的原来文件，删除之后再添加
                                    // Delete the file
                                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                            storageReference.putFile(profileUri)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            System.out.println("成功上传头像");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(EditInfoActivity_4.this, "更改头像的时候在重新上传这一步失败", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
                                            Toast.makeText(EditInfoActivity_4.this, "更改头像的时候在删除原来文件这一步失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

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

    //更新firebase storage
    private void updatePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_PICK_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                photo.setImageURI(data.getData());
                profileUri = data.getData();
            }
        }
    }
}