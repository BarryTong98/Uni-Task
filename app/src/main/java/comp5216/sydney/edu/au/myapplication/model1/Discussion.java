package comp5216.sydney.edu.au.myapplication.model1;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Discussion {
    private String discussionID; //discussion id
    private String userID; //creator id
    private List<String> assignmentList;
    private String title;
    private String description;
    private @ServerTimestamp
    Date timestamp;
    private List<Comment> comments;
}
