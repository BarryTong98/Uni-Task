package comp5216.sydney.edu.au.firebaseapp.adapter.adapter_13_14_15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import comp5216.sydney.edu.au.firebaseapp.R;

import java.util.List;

import comp5216.sydney.edu.au.firebaseapp.classtype.Discussion;
import comp5216.sydney.edu.au.firebaseapp.util.DateUtil;

//Listview for the discussion list function to show the discussion
public class DiscussionAdapter extends BaseAdapter {
    private List<Discussion> discussionList;
    private Context context;

    public DiscussionAdapter(List<Discussion> discussionList, Context context) {
        this.discussionList = discussionList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (discussionList.isEmpty()) {
            return 0;
        } else {
            return discussionList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return discussionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderOfDiscussion viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderOfDiscussion();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discussion, parent
                    , false);
            viewHolder.title = convertView.findViewById(R.id.tvDiscussionTitle);
            viewHolder.date = convertView.findViewById(R.id.tvDiscussionAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderOfDiscussion) convertView.getTag();
        }
        Discussion discussion = discussionList.get(position);
        viewHolder.title.setText(discussion.getTitle());
        viewHolder.date.setText("Created by " + discussion.getUserID() + " "
                + DateUtil.dateToString(discussion.getTimestamp(), true));
        return convertView;
    }
}

class ViewHolderOfDiscussion {
    TextView title;
    TextView date;
}
