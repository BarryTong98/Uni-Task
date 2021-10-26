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

//Listview for the discussion function to show the comments
public class CommentAdapter extends BaseAdapter {
    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (commentList == null || commentList.isEmpty()) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent
                    , false);
            viewHolderOfComment.author = convertView.findViewById(R.id.tvAuthor1);
            viewHolderOfComment.content = convertView.findViewById(R.id.tvContent1);
            viewHolderOfComment.time = convertView.findViewById(R.id.tvTime1);
            viewHolderOfComment.profile = convertView.findViewById(R.id.profile);
            convertView.setTag(viewHolderOfComment);
        } else {
            viewHolderOfComment = (ViewHolderOfComment) convertView.getTag();
        }
        Comment comment = commentList.get(position);
        viewHolderOfComment.author.setText(comment.getUserID() + "");
        viewHolderOfComment.content.setText(comment.getBody());
        viewHolderOfComment.time.setText(DateUtil.dateToString(comment.getTimestamp(), false));
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("profile/" + comment.getUserEmail());
        // used to display images, including image caching
        Glide.with(context /* context */)
                .load(storageReference)
                .signature(new ObjectKey(comment.getUserEmail()))
                .placeholder(R.drawable.image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
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


