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
import java.util.TreeMap;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_10_memberProfile;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class RecycleAdapter_mem_10_16 extends RecyclerView.Adapter<RecycleAdapter_mem_10_16.ViewHolder> {
    private List<User> firebaseUserList;
    private final String activity;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    Context context;

    /**
     * get data from firebase
     * @param context
     * @param userList
     * @param activity
     */
    public RecycleAdapter_mem_10_16(Context context, @NonNull List<User> userList, String activity) {
        this.context = context;
        this.activity = activity;
        this.firebaseUserList=userList;

//        firebaseFirestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<User> list= queryDocumentSnapshots.toObjects(User.class);
//                Map<String,User>map=new TreeMap<>();
//                for(User j:userList){
//                    map.put(j.getUserId(),j);
//                }
//
//                for (User i:list){
//                    if (map.containsKey(i.getUserId())){
//                        map.put(i.getUserId(),i);
//                    }
//                }
//
//                firebaseUserList=new ArrayList<>(map.values());
//            }
//        });
    }

    /**
     * create member item view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_10_mem_item, parent, false);

        return new ViewHolder(view);
    }

    /**
     * update item view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        int location = holder.getAdapterPosition();
        if (firebaseUserList.get(location).getImageURL() != null&&firebaseUserList.get(location)
                .getEmail()!=null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("profile/" + firebaseUserList.get(location).getEmail());
            Glide.with(context /* context */)
                    .load(storageReference)
                    .signature(new ObjectKey(firebaseUserList.get(location).getEmail()))
                    .placeholder(R.drawable.image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.image);
        } else {
            //之后改成userEmail
            holder.image.setImageResource(R.drawable.image);
            //
        }

        holder.name.setText(firebaseUserList.get(location).getUserName());
/**
 * if member item is clicked, jump to member profile page.
 */
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
/**
 * if member item is long clicked, pop up delete item window.
 */
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
    public long getItemId(int position) {
        return position;
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