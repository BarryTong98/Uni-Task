package comp5216.sydney.edu.au.firebaseapp.classtype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Assignment implements Serializable {
    private String assignmentId;
    private String groupId;

    private String assignmentName;
    private String description;
    private String dueDate;

    private String groupName;
    private int states;
    private ArrayList<Tasks> taskList;
    private ArrayList<User> userList;

    public Assignment(String assignmentId, String assignmentName, String description, String dueDate, String groupName, String groupId, int status, ArrayList<Tasks> taskList, ArrayList<User> userList) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.description = description;
        this.dueDate = dueDate;
        this.groupId = groupId;
        this.groupName = groupName;
        this.states = status;
        this.taskList = taskList;
        this.userList = userList;
    }

    public Assignment(String assignmentId, String assignmentName, String description, String dueDate, String groupId, ArrayList<Tasks> taskList) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.description = description;
        this.dueDate = dueDate;
        this.groupId = groupId;
        this.states = 0;
        this.taskList = taskList;
    }

    public Assignment() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getStatus() {
        return states;
    }

    public ArrayList<Tasks> getTaskList() {
        return taskList;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getPercentage(){
        float percentage=0;
        if (taskList!=null&&taskList.size()>0) {
            for (Tasks i : taskList) {
                if (i.getStates() == 1) {
                    percentage++;
                }
            }
            percentage /= taskList.size();

            String s = String.format("The assignment has been completed");
            if (percentage == 0.0) {
                s += " 0%";
            } else {
                s += String.format(" %.1f%", Math.round(percentage * 1000) / 10.0);
            }
            return s;
        }else {
            return "This assignment don't have any tasks.";
        }
    }

}
