package comp5216.sydney.edu.au.firebaseapp.classtype;

import java.io.Serializable;
import java.util.ArrayList;

public class Tasks implements Serializable {
    private String taskId;
    private String taskName;

    private String description;
    private String assignmentId;

    private String groupName;
    private String groupId;
    private String dueDate;
    private ArrayList<User> userList;
    private int states;
    private ArrayList<User> selectedList;

    public Tasks(String taskId, String assignmentId, String groupId, String taskName, String description, String dueDate, String groupName, ArrayList<User> userList, ArrayList<User> selectedList) {
        this.taskId = taskId;
        this.assignmentId = assignmentId;
        this.groupId = groupId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.userList = userList;
        this.groupName = groupName;
        this.selectedList = selectedList;
        this.states = 0;
    }

    public Tasks(String taskId, String assignmentId, String groupId, String taskName, String description, String dueDate, String groupName, ArrayList<User> userList) {
        this.taskId = taskId;
        this.assignmentId = assignmentId;
        this.groupId = groupId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.userList = userList;
        this.groupName = groupName;
        this.states = 0;
    }

    public Tasks() {
    }


    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<User> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(ArrayList<User> selectedList) {
        this.selectedList = selectedList;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMemberName() {
        String memberName="";
        ArrayList<User> memberList=this.getUserList();
        for (User i:memberList){
            memberName += i.getUserName()+" ";
        }
        return memberName;
    }
}
