package comp5216.sydney.edu.au.firebaseapp.model;

import java.util.List;

public class Group {
    private String groupId;//uuid
    private String introduction;
    private List<Assignment> assignmentList;
    private List<User> userList;
    private List<Discussion> discussionList;
}
