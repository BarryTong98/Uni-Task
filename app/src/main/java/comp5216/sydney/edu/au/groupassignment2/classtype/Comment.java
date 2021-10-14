package comp5216.sydney.edu.au.groupassignment2.classtype;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {
    //primary key
    private String commentId;

    //foreign key
    private String userId;

    //attribute
    private String body;
    private @ServerTimestamp
    Date timestamp;

    public Comment(String commentId, String userId, String body, Date timestamp) {
        this.commentId = commentId;
        this.userId = userId;
        this.body = body;
        this.timestamp = timestamp;
    }
}



//1. tang lin
//2.a 问卷调查, Lo
//2.b Huang
//3. Tan ziyi
//4. barry