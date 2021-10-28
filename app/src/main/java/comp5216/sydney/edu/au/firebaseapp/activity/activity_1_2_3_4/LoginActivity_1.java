package comp5216.sydney.edu.au.firebaseapp.activity.activity_1_2_3_4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;


public class LoginActivity_1 extends AppCompatActivity {

    private EditText etUsername, etPass;
    private TextView tv;
    private Button login, register;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User currentUser;
    ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_login);

        etUsername = findViewById(R.id.et_log_username);
        etPass = findViewById(R.id.et_log_password);
        register = findViewById(R.id.btn_login_register);
        login = findViewById(R.id.btn_login_login);
        tv = findViewById(R.id.tv_login_findpwd);
        mCache = ACache.get(this);
        //firebase auth
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity_1.this, RegisterActivity_2.class);
                startActivity(intent);
            }
        });

        //reset the password
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder pwdResetDialog = new AlertDialog.Builder(v.getContext());
                pwdResetDialog.setTitle("Reset Password ?")
                        .setMessage("Enter Your Email To Received Rest Link.")
                        .setView(resetMail)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String mail = resetMail.getText().toString();
                                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(LoginActivity_1.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity_1.this, "Error! Reset Link Not Sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });

        //check for user exsit, user needn't login every time: save current user
        if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity_1.this, HomeActivity_3.class);
            startActivity(intent);

            DocumentReference docRef = db.collection("Users").document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            currentUser = document.toObject(User.class);
                            mCache.put(firebaseUser.getUid(), currentUser);

                            Log.d("Login Page", "DocumentSnapshot data: " + currentUser.getUserName());
                        } else {
                            Log.d("Login Page", "No such document");
                        }
                    } else {
                        Log.d("Login Page", "get failed with ", task.getException());
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
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity_1.this, "Please input email and pass", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity_1.this, HomeActivity_3.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity_1.this, "login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}