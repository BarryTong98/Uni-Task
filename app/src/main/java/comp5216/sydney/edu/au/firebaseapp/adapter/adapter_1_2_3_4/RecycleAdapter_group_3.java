package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_1_2_3_4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11.Activity_10;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.GroupBrief;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;

public class RecycleAdapter_group_3 extends RecyclerView.Adapter<RecycleAdapter_group_3.ViewHolder> {
    private String[] groupIdList;
    private String[] groupNameList;
    private String[] introductionList;
    private List<GroupBrief> groupBriefList;
    private Map<String, Group> groupMap;

    private FirebaseFirestore db;
    private User currentUser;
    private ACache mCache;

    Context context;


    public RecycleAdapter_group_3(Context context, List<GroupBrief> groupBriefList, Map<String, Group> groupMap,
                                  FirebaseFirestore db, User currentUser, ACache mCache) {
        this.context = context;
        this.groupBriefList = groupBriefList;
        this.groupMap = groupMap;

        this.db = db;
        this.currentUser = currentUser;
        this.mCache = mCache;

        int length = groupBriefList.size();

        groupIdList = new String[length];
        groupNameList = new String[length];
        introductionList = new String[length];
        for (int i = 0; i < length; i++) {
            GroupBrief temp = groupBriefList.get(i);
            groupIdList[i] = temp.getGroupId();
            groupNameList[i] = temp.getGroupName();
            introductionList[i] = temp.getIntroduction();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_3_10_11_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int location = holder.getAdapterPosition();
        holder.title.setText(groupIdList[position]);
        holder.assign.setText(groupNameList[position]);
        holder.taskItem.setText(introductionList[position]);

        holder.act3_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupId = groupBriefList.get(location).getGroupId();
                getGroup(groupId);
            }
        });


        holder.act3_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                groupBriefList.remove(location);
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
        return groupBriefList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, assign, taskItem;
        LinearLayout act3_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            assign = itemView.findViewById(R.id.itemBrief);
            taskItem = itemView.findViewById(R.id.itemContent);
            act3_item = itemView.findViewById(R.id.act_3_10_11_item);
        }
    }

    public void change(List<GroupBrief> list){
        groupBriefList.clear();
        groupBriefList.addAll(list);
        notifyDataSetChanged();
    }

    private void getGroup(String groupId) {
        int size = groupMap.size();
            if (!groupMap.containsKey(groupId)) {
                //从firebase cloud 的Groups中拿数据下来
                db.collection("GroupValues")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Group group = document.toObject(Group.class);

                                        if (group.getGroupId().equals(groupId)) {
                                            String tempId = group.getGroupId();
                                            System.out.println("检查有没有这个item:" + tempId);

                                            groupMap.put(tempId, group);
//
//                                        //放在缓存中
//                                        mCache.put(tempId, group);
//                                        Log.d("group item test:", document.getId() + " => " + document.getData());

                                        }

                                    }
                                } else {
                                    Log.d("home item test:", "Error getting documents: ", task.getException());
                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Group groupIntent=groupMap.get(groupId);
                        Intent intent = new Intent(context, Activity_10.class);
                        intent.putExtra("Group", groupIntent);
                        context.startActivity(intent);
                    }
                });
            }else {
                Group groupIntent=groupMap.get(groupId);
                Intent intent = new Intent(context, Activity_10.class);
                intent.putExtra("Group", groupIntent);
                context.startActivity(intent);
            }
    }
}







