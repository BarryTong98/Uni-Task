package comp5216.sydney.edu.au.myapplication.model1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {
    private String taskId;
    private String taskName;
    private String description;
    private String assignmentId;
    private String groupName;
    private String dueDate;
    private List<User> userList;
    private int status;

    public Task(String taskId, String taskName, String description, String assignmentId, String groupName, String dueDate, List<User> userList, int status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.assignmentId = assignmentId;
        this.groupName = groupName;
        this.dueDate = dueDate;
        this.userList = userList;
        this.status = status;
    }

    public Task() {
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

    public String getGroupName() {
        return groupName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public List<User> getUserList() {
        return userList;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", assignmentId='" + assignmentId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", userList=" + userList +
                ", status=" + status +
                '}';
    }
}
