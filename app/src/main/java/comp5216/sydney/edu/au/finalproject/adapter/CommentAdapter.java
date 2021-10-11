package comp5216.sydney.edu.au.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import comp5216.sydney.edu.au.finalproject.R;
import comp5216.sydney.edu.au.finalproject.model.Comment;
import comp5216.sydney.edu.au.finalproject.util.DateUtil;

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
            convertView.setTag(viewHolderOfComment);
        } else {
            viewHolderOfComment = (ViewHolderOfComment) convertView.getTag();
        }
        Comment comment = commentList.get(position);
        viewHolderOfComment.author.setText(comment.getUserID()+"");
        viewHolderOfComment.content.setText(comment.getBody());
        viewHolderOfComment.time.setText(DateUtil.dateToString(comment.getTimestamp(),false));
        return convertView;
    }
}

class ViewHolderOfComment {
    TextView author;
    TextView content;
    TextView time;
}


