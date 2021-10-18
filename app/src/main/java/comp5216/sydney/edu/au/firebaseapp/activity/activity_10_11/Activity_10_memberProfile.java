package comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        if (user.getImageURL()!=null) {
            imageView.setImageURI(Uri.parse(user.getImageURL()));
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
