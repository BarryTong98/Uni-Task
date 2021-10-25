package comp5216.sydney.edu.au.firebaseapp.classtype;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {
    //primary key
    private String groupId;//uuid

    //attribute
    private String groupName;
    private String introduction;

    //List
    private List<Assignment> assignmentList;
    private List<User> userList;
    private List<Discussion> discussionList;


    public Group(String groupId, String groupName, String introduction, List<Assignment> assignmentList, List<User> userList) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.introduction = introduction;
        this.assignmentList = assignmentList;
        this.userList = userList;
    }

    public Group() {

    }

    public Group(String groupId) {
        this.groupId = groupId;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Discussion> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(List<Discussion> discussionList) {
        this.discussionList = discussionList;
    }
}
