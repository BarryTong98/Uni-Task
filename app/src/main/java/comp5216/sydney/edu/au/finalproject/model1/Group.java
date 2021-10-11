package comp5216.sydney.edu.au.finalproject.model1;

import java.util.List;

import comp5216.sydney.edu.au.finalproject.model.Discussion;

public class Group {
    private String groupId;//uuid
    private String introduction;
    private List<Assignment> assignmentList;
    private List<User> userList;
    private List<Discussion> discussionList;
}
