package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.io.Serializable;
import java.util.List;


public class Group implements Serializable {
    //primary key
    private String groupId;//uuid

    public Group(String groupId, String groupName, String introduction, List<Assignment> assignmentList, List<User> userList) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.introduction = introduction;
        this.assignmentList = assignmentList;
        this.userList = userList;
    }

    //attribute
    private String groupName;
    private String introduction;

    //List
    private List<Assignment> assignmentList;
    private List<User> userList;
    private List<Discussion> discussionList;


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
}
