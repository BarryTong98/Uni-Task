package comp5216.sydney.edu.au.firebaseapp.classtype;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String commentID;
    private String userID;
    private String userEmail;
    private String body;
    private @ServerTimestamp
    Date timestamp;

    public Comment(String commentID, String userID, String userEmail, String body, Date timestamp) {
        this.commentID = commentID;
        this.userID = userID;
        this.userEmail = userEmail;
        this.body = body;
        this.timestamp = timestamp;
    }

    public Comment() {
    }

    public Comment(String commentID, String userID, String userEmail, String body) {
        this.commentID = commentID;
        this.userID = userID;
        this.userEmail = userEmail;
        this.body = body;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", userID=" + userID +
                ", body='" + body + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}