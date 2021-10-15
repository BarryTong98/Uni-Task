package comp5216.sydney.edu.au.myapplication.model1;

import java.io.Serializable;
import java.util.List;

public class Assignment implements Serializable {
    private String assignmentId;
    private String name;
    private String description;
    private String dueDate;
    private String groupId;
    private int status;
    private List<Task> taskList;

    public Assignment(String assignmentId, String name, String description, String dueDate, String groupId, int status, List<Task> taskList) {
        this.assignmentId = assignmentId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.groupId = groupId;
        this.status = status;
        this.taskList = taskList;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getName() {
        return name;
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
        return status;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId='" + assignmentId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", groupId='" + groupId + '\'' +
                ", status=" + status +
                ", taskList=" + taskList +
                '}';
    }
}
