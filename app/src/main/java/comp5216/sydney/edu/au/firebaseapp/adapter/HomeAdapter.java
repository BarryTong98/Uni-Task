package comp5216.sydney.edu.au.firebaseapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.model.HomeItem;
import comp5216.sydney.edu.au.firebaseapp.model.User;

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    List<HomeItem> itemList;
    LayoutInflater inflater;



    public HomeAdapter(List<HomeItem> itemList, Context context) {
        this.mContext = context;
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(context);//Initialize the reflector
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public HomeItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.home_item, null);

        TextView tv_group = view.findViewById(R.id.item_group_name);
        TextView tv_course = view.findViewById(R.id.item_course_name);
        TextView tv_introduction = view.findViewById(R.id.item_introduction);

        HomeItem item = itemList.get(position);
        tv_group.setText(item.groupname);
        tv_course.setText(item.coursename);
        tv_introduction.setText(item.introduction);

        return view;
    }
}
