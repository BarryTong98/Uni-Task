package comp5216.sydney.edu.au.groupassignment2.classtype;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;


public class Discussion {
    //primary key
    private String discussionId; //discussion id

    //foreign key
    private String userId; //creator id

    //attribute
    private String title;
    private String description;
    private @ServerTimestamp
    Date timestamp;

    //List
    private List<String> assignmentList;
    private List<Comment> comments;

    public Discussion(String discussionId, String userId, List<String> assignmentList, String title, String description, Date timestamp, List<Comment> comments) {
        this.discussionId = discussionId;
        this.userId = userId;
        this.assignmentList = assignmentList;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.comments = comments;
    }
}
