package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_1_2_3_4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_10;
import comp5216.sydney.edu.au.firebaseapp.activity.activity_10_11_16.Activity_16;
import comp5216.sydney.edu.au.firebaseapp.classtype.Group;
import comp5216.sydney.edu.au.firebaseapp.classtype.GroupBrief;
import comp5216.sydney.edu.au.firebaseapp.classtype.User;
import comp5216.sydney.edu.au.firebaseapp.util.ACache;

public class RecycleAdapter_group_3 extends RecyclerView.Adapter<RecycleAdapter_group_3.ViewHolder> {
    private final String[] groupNameList;
    private final String[] introductionList;
    private List<GroupBrief> groupBriefList;
    private Map<String, Group> groupMap;

    private FirebaseFirestore db;
    private String userId;;
    private ACache mCache;
    private ActivityResultLauncher<Intent>mLauncher;

    Context context;

    private CardView cv;


    public RecycleAdapter_group_3(Context context, List<GroupBrief> groupBriefList, Map<String, Group> groupMap,
                                  FirebaseFirestore db, String userId, ACache mCache,ActivityResultLauncher<Intent> mLauncher) {
        this.context = context;
        this.groupBriefList = groupBriefList;
        this.groupMap = groupMap;

        this.db = db;
        this.userId = userId;
        this.mCache = mCache;
        this.mLauncher=mLauncher;

        int length = groupBriefList.size();


        groupNameList = new String[length];
        introductionList = new String[length];
        for (int i = 0; i < length; i++) {
            GroupBrief temp = groupBriefList.get(i);
            groupNameList[i] = temp.getGroupName();
            introductionList[i] = temp.getIntroduction();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_3_10_11_item, parent, false);
        cv = (CardView)view.findViewById(R.id.cardView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int location = holder.getAdapterPosition();
        holder.title.setText(groupNameList[position]);
        holder.assign.setText(introductionList[position]);
        holder.taskItem.setVisibility(View.INVISIBLE);
        holder.iv.setVisibility(View.VISIBLE);

        holder.act3_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupId = groupBriefList.get(location).getGroupId();
                getGroup(groupId,"shortClick");
            }
        });


        holder.act3_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String groupId = groupBriefList.get(location).getGroupId();
                getGroup(groupId,"longClick");

                return true;
            }
        });

    }

    public void change(List<GroupBrief> list){
        groupBriefList.clear();
        groupBriefList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return groupBriefList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, assign, taskItem;
        ImageView iv;
        LinearLayout act3_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            assign = itemView.findViewById(R.id.itemBrief);
            taskItem = itemView.findViewById(R.id.itemContent);
            act3_item = itemView.findViewById(R.id.act_3_10_11_item);
            iv = itemView.findViewById(R.id.iv_group);
        }
    }

    private void getGroup(String groupId,String clicktime) {
        int size = groupMap.size();
            if (!groupMap.containsKey(groupId)) {
                db.collection("groups")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Group group = document.toObject(Group.class);

                                        if (group.getGroupId().equals(groupId)) {
                                            String tempId = group.getGroupId();
                                            groupMap.put(tempId, group);
                                        }

                                    }
                                } else {
                                    Log.d("home item test:", "Error getting documents: ", task.getException());
                                }
                            }
                        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (clicktime.equalsIgnoreCase("shortClick")) {
                            Group groupIntent = groupMap.get(groupId);
                            Intent intent = new Intent(context, Activity_10.class);
                            intent.putExtra("Group", groupIntent);
                            context.startActivity(intent);
                        }else {
//                            Group groupIntent = groupMap.get(groupId);
//                            Intent intent = new Intent(context, Activity_16.class);
//                            intent.putExtra("Group", groupIntent);
//                            context.startActivity(intent);
                            launchActivity16(groupId);
                        }
                    }
                });
            }else {
                if (clicktime.equalsIgnoreCase("shortClick")) {
                    Group groupIntent = groupMap.get(groupId);
                    Intent intent = new Intent(context, Activity_10.class);
                    intent.putExtra("Group", groupIntent);
                    context.startActivity(intent);
                }else{
//                    Group groupIntent = groupMap.get(groupId);
//                    Intent intent = new Intent(context, Activity_16.class);
//                    intent.putExtra("Group", groupIntent);
//                    context.startActivity(intent);
                    launchActivity16(groupId);
                }
            }
    }

   private void launchActivity16(String groupId){
       Group groupIntent = groupMap.get(groupId);
       Intent intent = new Intent(context, Activity_16.class);
       intent.putExtra("Group", groupIntent);
       mLauncher.launch(intent);

   }

}







