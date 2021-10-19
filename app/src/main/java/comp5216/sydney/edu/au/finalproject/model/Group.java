package comp5216.sydney.edu.au.finalproject.model;

import java.util.List;


public class Group {
    private String groupId;//uuid
    private String introduction;
    private List<Assignment> assignmentList;
    private List<User> userList;
    private List<Discussion> discussionList;

    public Group() {}

    public Group(String groupId, String introduction, List<Assignment> assignmentList, List<User> userList, List<Discussion> discussionList) {
        this.groupId = groupId;
        this.introduction = introduction;
        this.assignmentList = assignmentList;
        this.userList = userList;
        this.discussionList = discussionList;
    }


    public String getGroupId() {
        return groupId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Discussion> getDiscussionList() {
        return discussionList;
    }
}
