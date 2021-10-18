package comp5216.sydney.edu.au.firebaseapp.classtype;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String commentID;
    private int userID;
    private String body;
    private @ServerTimestamp
    Date timestamp;

    public Comment(String commentID, int userID, String body, Date timestamp) {
        this.commentID = commentID;
        this.userID = userID;
        this.body = body;
        this.timestamp = timestamp;
    }

    public Comment() {
    }

    public Comment(String commentID, int userID, String body) {
        this.commentID = commentID;
        this.userID = userID;
        this.body = body;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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


//1. tang lin
//2.a 问卷调查, Lo
//2.b Huang
//3. Tan ziyi
//4. barry