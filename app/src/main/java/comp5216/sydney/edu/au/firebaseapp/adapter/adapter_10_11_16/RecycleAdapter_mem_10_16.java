package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_10_memberProfile;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class RecycleAdapter_mem_10_16 extends RecyclerView.Adapter<RecycleAdapter_mem_10_16.ViewHolder> {
    private List<User> firebaseUserList;
    private final String activity;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    Context context;


    public RecycleAdapter_mem_10_16(Context context, @NonNull List<User> userList, String activity) {
        this.context = context;
        this.activity = activity;
        this.firebaseUserList=userList;

        firebaseFirestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<User> list= queryDocumentSnapshots.toObjects(User.class);
                Map<String,User>map=new HashMap<>();
                for(User j:userList){
                    map.put(j.getUserId(),j);
                }

                for (User i:list){
                    if (map.containsKey(i.getUserId())){
                        map.put(i.getUserId(),i);
                    }
                }

                firebaseUserList=new ArrayList<>(map.values());
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_10_mem_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int location = holder.getAdapterPosition();
//        if (imgList[location] != null) {
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + userList.get(location));
//            Glide.with(context /* context */)
//                    .load(storageReference)
//                    .signature(new ObjectKey(userList.get(position).getEmail())) //为了图片更新之后，缓存也更新
//                    .placeholder(R.drawable.image)//图片加载出来前，显示的图片
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
//                    .into(holder.image);
//        } else {
//            //之后改成userEmail
//            holder.image.setImageResource(R.drawable.image);
//            //
//        }

        if (firebaseUserList.get(location).getImageURL()!=null&&firebaseUserList.get(location).getEmail()!=null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + firebaseUserList.get(location).getEmail());
            if (storageReference!=null) {
                final long ONE_MEGABYTE = 1024 * 1024;
                storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.image.setImageBitmap(bmp);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, "No Such file or Path found!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }



        holder.name.setText(firebaseUserList.get(location).getUserName());

        holder.act_10_mem_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User intentUser = firebaseUserList.get(location);
                Intent intent = new Intent(context, Activity_10_memberProfile.class);
                intent.putExtra("User", intentUser);
                context.startActivity(intent);
            }
        });

        if (activity.equalsIgnoreCase("activity16")) {

            holder.act_10_mem_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.dialog_delete_title)
                            .setMessage(R.string.dialog_delete_member)
                            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    firebaseUserList.remove(location);
                                    notifyItemRemoved(location);
                                    // Remove item from the ArrayList
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface
                                    .OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User cancelled the dialog
                                    // Nothing happens
                                }
                            });
                    builder.create().show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return firebaseUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        LinearLayout act_10_mem_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.memberImg);
            name = itemView.findViewById(R.id.memberName);
            act_10_mem_item = itemView.findViewById(R.id.act_10_mem_item);
        }
    }
}
