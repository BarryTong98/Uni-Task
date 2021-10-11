package comp5216.sydney.edu.au.myapplication.Model;

import com.google.android.gms.tasks.Task;

import java.util.List;

public class AssignmentModel extends AssignmentId{
    private String groupName;

    private String assignmentName;

    private String due;

    private List<TaskOfAssignmentModel> tasks;

    private int status;

    public AssignmentModel(String groupName, String assignmentName, String due, List<TaskOfAssignmentModel> tasks, int status) {
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

    public List<TaskOfAssignmentModel> getTasks() {
        return tasks;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AssignmentModel{" +
                "AssignmentId='" + AssignmentId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", assignmentName='" + assignmentName + '\'' +
                ", due='" + due + '\'' +
                ", tasks=" + tasks +
                ", status=" + status +
                '}';
    }
}
