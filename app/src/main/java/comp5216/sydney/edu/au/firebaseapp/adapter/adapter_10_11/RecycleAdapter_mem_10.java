package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_10_11;

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

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11.Activity_10_memberProfile;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;

public class RecycleAdapter_mem_10 extends RecyclerView.Adapter<RecycleAdapter_mem_10.ViewHolder> {
    private final List<User> userList;
    private final String[] nameList;
    private final String[] imgList;
    Context context;


    public RecycleAdapter_mem_10(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
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
            holder.image.setImageResource(R.drawable.image);
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

        holder.act_10_mem_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
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
