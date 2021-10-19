package comp5216.sydney.edu.au.finalproject.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {
    private String commentID;
    private String userID;
    private String body;
    private @ServerTimestamp
    Date timestamp;
}