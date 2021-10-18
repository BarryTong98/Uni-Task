package comp5216.sydney.edu.au.finalproject.model;

import java.util.List;

public class Assignment {
    private String groupName;

    private String assignmentName;

    private String due;

    private List<Task> tasks;

    private int status;

    public Assignment(String groupName, String assignmentName, String due, List<Task> tasks, int status) {
        this.groupName = groupName;
        this.assignmentName = assignmentName;
        this.due = due;
        this.tasks = tasks;
        this.status = status;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDue() {
        return due;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AssignmentModel{" +
                ", groupName='" + groupName + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                ", due='" + due + '\'' +
                ", tasks=" + tasks +
                ", status=" + status +
                '}';
    }
}
