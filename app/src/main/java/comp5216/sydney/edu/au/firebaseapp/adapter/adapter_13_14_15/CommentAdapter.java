package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_13_14_15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.R;
import comp5216.sydney.edu.au.firebaseapp.classtype.Comment;
import comp5216.sydney.edu.au.firebaseapp.util.DateUtil;


public class CommentAdapter extends BaseAdapter {
    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (commentList==null||commentList.isEmpty()) {
            return 0;
        } else {
            return commentList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderOfComment viewHolderOfComment;
        if (convertView == null) {
            viewHolderOfComment = new ViewHolderOfComment();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            viewHolderOfComment.author = convertView.findViewById(R.id.tvAuthor1);
            viewHolderOfComment.content = convertView.findViewById(R.id.tvContent1);
            viewHolderOfComment.time = convertView.findViewById(R.id.tvTime1);
            viewHolderOfComment.profile=convertView.findViewById(R.id.profile);
            convertView.setTag(viewHolderOfComment);
        } else {
            viewHolderOfComment = (ViewHolderOfComment) convertView.getTag();
        }
        Comment comment = commentList.get(position);
        viewHolderOfComment.author.setText(comment.getUserID()+"");
        viewHolderOfComment.content.setText(comment.getBody());
        viewHolderOfComment.time.setText(DateUtil.dateToString(comment.getTimestamp(),false));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile/" + "123@qq.com");
        Glide.with(context /* context */)
                .load(storageReference)
                .signature(new ObjectKey(comment.getUserEmail())) //为了图片更新之后，缓存也更新
                .placeholder(R.drawable.image)//图片加载出来前，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)// 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
                .into(viewHolderOfComment.profile);
        return convertView;
    }
}

class ViewHolderOfComment {
    TextView author;
    TextView content;
    TextView time;
    ImageView profile;
}


