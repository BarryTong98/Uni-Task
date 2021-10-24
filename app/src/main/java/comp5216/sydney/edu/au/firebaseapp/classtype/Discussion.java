package comp5216.sydney.edu.au.firebaseapp.classtype;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Discussion implements Serializable {
    private String discussionID; //discussion id
    private String groupID; //group id
    private String userID; //creator id
    private int assignmentID;
    private String title;
    private String description;
    private @ServerTimestamp
    Date timestamp;
    private List<Comment> comments;

    public Discussion() {
    }

    public Discussion(String discussionID, String groupID, String userID, int assignmentID, String title, String description, Date timestamp, List<Comment> comments) {
        this.discussionID = discussionID;
        this.groupID = groupID;
        this.userID = userID;
        this.assignmentID = assignmentID;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.comments = comments;
    }

    public Discussion(String discussionID, String groupID, String userID, int assignmentID, String title, String description, List<Comment> comments) {
        this.discussionID = discussionID;
        this.groupID = groupID;
        this.userID = userID;
        this.assignmentID = assignmentID;
        this.title = title;
        this.description = description;
        this.comments = comments;
    }

    public String getDiscussionID() {
        return discussionID;
    }

    public void setDiscussionID(String discussionID) {
        this.discussionID = discussionID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "discussionID=" + discussionID +
                ", groupID=" + groupID +
                ", userID=" + userID +
                ", assignmentID=" + assignmentID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", comments=" + comments +
                '}';
    }

}
