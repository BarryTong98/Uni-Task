package comp5216.sydney.edu.au.groupassignment2.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import comp5216.sydney.edu.au.groupassignment2.classtype.Member;
import comp5216.sydney.edu.au.groupassignment2.R;
import comp5216.sydney.edu.au.groupassignment2.classtype.User;

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
    public RecycleAdapter_mem_10.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_10_mem_item, parent, false);

        return new RecycleAdapter_mem_10.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter_mem_10.ViewHolder holder, int position) {
        if (imgList[position]!=null) {
            holder.image.setImageURI(Uri.parse(imgList[position]));
        }else {
            holder.image.setImageURI(Uri.parse("R.drawable.image"));
        }

        holder.name.setText(nameList[position]);
        int location=position;

        holder.act_10_mem_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_assignment_delete_msg)
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
