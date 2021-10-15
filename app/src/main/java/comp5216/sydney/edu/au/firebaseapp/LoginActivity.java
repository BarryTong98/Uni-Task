package comp5216.sydney.edu.au.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import comp5216.sydney.edu.au.firebaseapp.adapter.HomeAdapter;
import comp5216.sydney.edu.au.firebaseapp.model.HomeItem;
import comp5216.sydney.edu.au.firebaseapp.model.User;


public class LoginActivity extends AppCompatActivity {

    private EditText etUsername,etPass;
    private Button login,register;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_log_username);
        etPass = findViewById(R.id.et_log_password);
        register = findViewById(R.id.btn_login_register);
        login = findViewById(R.id.btn_login_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //firebase auth
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check for user exsit, user needn't login every time: save current user
        if (firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);

            //从firebase cloud database拿数据下来
            DocumentReference docRef = db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            currentUser = document.toObject(User.class);
                            //传递用户信息
                            Intent toProfile = new Intent(LoginActivity.this,ProfileActivity.class);
                            toProfile.putExtra("user", currentUser);

                            Log.d("拿数据测试", "DocumentSnapshot data: " + currentUser.getName());
                        } else {
                            Log.d("拿数据测试", "No such document");
                        }
                    } else {
                        Log.d("拿数据测试", "get failed with ", task.getException());
                    }
                }
            });

            finish();
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "Please input email and pass", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}