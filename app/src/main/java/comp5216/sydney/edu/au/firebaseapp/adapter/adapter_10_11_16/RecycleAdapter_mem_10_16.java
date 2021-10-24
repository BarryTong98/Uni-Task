package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11_16;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_10_memberProfile;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class RecycleAdapter_mem_10_16 extends RecyclerView.Adapter<RecycleAdapter_mem_10_16.ViewHolder> {
    private final List<User> userList;
    private final String[] nameList;
    private final String[] imgList;
    private final String activity;
    Context context;


    public RecycleAdapter_mem_10_16(Context context,@NonNull List<User> userList, String activity) {
        this.context = context;
        this.userList = userList;
        this.activity=activity;
        int length = userList.size();
        nameList = new String[length];
        imgList = new String[length];
        for (int i = 0; i < length; i++) {
            User temp = userList.get(i);
            nameList[i] = temp.getUserName();
            imgList[i] = temp.getImageURL();

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_10_mem_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int location =holder.getAdapterPosition();
        if (imgList[location]!=null) {
            holder.image.setImageURI(Uri.parse(imgList[location]));
        }else {
            //之后改成userEmail
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + "123@qq.com");
            Glide.with(context /* context */)
                    .load(storageReference)
                    .signature(new ObjectKey(userList.get(position).getEmail())) //为了图片更新之后，缓存也更新
                    .placeholder(R.drawable.image)//图片加载出来前，显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
                    .into(holder.image);
            //holder.image.setImageResource(R.drawable.image);
        }

        holder.name.setText(nameList[location]);

        holder.act_10_mem_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User intentUser= userList.get(location);
                Intent intent=new Intent(context, Activity_10_memberProfile.class);
                intent.putExtra("User",intentUser);
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
                                    userList.remove(location);
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
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        LinearLayout act_10_mem_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.memberImg);
            name = itemView.findViewById(R.id.memberName);
            act_10_mem_item=itemView.findViewById(R.id.act_10_mem_item);
        }
    }
}
