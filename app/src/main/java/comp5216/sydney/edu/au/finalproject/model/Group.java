package comp5216.sydney.edu.au.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Group implements Serializable {
    private String groupId;//uuid
    private String name;
    private String introduction;
    private ArrayList<Assignment> assignmentList;
    private ArrayList<User> userList;
    private ArrayList<Discussion> discussionList;

    public Group() {}

    public Group(String groupId, String name, String introduction, ArrayList<Assignment> assignmentList, ArrayList<User> userList) {
        this.groupId = groupId;
        this.name = name;
        this.introduction = introduction;
        this.assignmentList = assignmentList;
        this.userList = userList;
    }

    public Group(String groupId, String name, String introduction, ArrayList<Assignment> assignmentList, ArrayList<User> userList, ArrayList<Discussion> discussionList) {
        this.groupId = groupId;
        this.name = name;
        this.introduction = introduction;
        this.assignmentList = assignmentList;
        this.userList = userList;
        this.discussionList = discussionList;
    }


    public String getGroupId() {
        return groupId;
    }

    public String getName() {return name;}

    public String getIntroduction() {
        return introduction;
    }

    public ArrayList<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<Discussion> getDiscussionList() {
        return discussionList;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setAssignmentList(ArrayList<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public void setDiscussionList(ArrayList<Discussion> discussionList) {
        this.discussionList = discussionList;
    }
}
