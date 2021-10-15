package comp5216.sydney.edu.au.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.firebaseapp.adapter.HomeAdapter;
import comp5216.sydney.edu.au.firebaseapp.model.HomeItem;
import comp5216.sydney.edu.au.firebaseapp.model.User;

public class MainActivity extends AppCompatActivity {
    private Button register,login,profile,data;
    private ListView listView;
    List<HomeItem> itemList = new ArrayList<HomeItem>();
    HomeAdapter homeAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置主页的listview
        listView = findViewById(R.id.lv_home);

        register=findViewById(R.id.bt_home_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login = findViewById(R.id.bt_home_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        profile = findViewById(R.id.btn_main_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        //用本地数据库
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
            //从firebase cloud base拿数据下来
            db.collection("homeData")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //先清空一下list
                                itemList.clear();

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    HomeItem item = document.toObject(HomeItem.class);
                                    System.out.println("检查有没有这个item:"+item.getCoursename());
                                    itemList.add(item);
                                    Log.d("home item test:", document.getId() + " => " + document.getData());
                                }

                                for (HomeItem item:itemList){
                                    System.out.println("在这里查查："+item.getCoursename());
                                }

                                // Create an adapter for the list view using Android's built-in item layout
                                homeAdapter = new HomeAdapter(itemList, MainActivity.this);

                                // Connect the listView and the adapter
                                listView.setAdapter(homeAdapter);
                            } else {
                                Log.d("home item test:", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    //造一些假数据
    public void setData(View view){
        //先把测试数据放上去
        CollectionReference items = db.collection("homeData");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Score");
        ref.child(userId).child("result").setValue(1);

        Map<String, Object> data1 = new HashMap<>();
        data1.put("groupname", "COMP5216 Group7");
        data1.put("coursename", "course test2");
        data1.put("introduction", "very good course!");
        items.document("item1").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("groupname", "INFO5992 Group1");
        data2.put("coursename", "IT Innovation");
        data2.put("introduction", "interesting course");
        items.document("item2").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("groupname", "INFO5990 Group8");
        data3.put("coursename", "Professional Practice in IT");
        data3.put("introduction", "hard course!");
        items.document("item3").set(data3);
    }

}