package comp5216.sydney.edu.au.myapplication.Model;

import java.util.List;

public class TaskOfAssignmentModel extends TaskOfAssignmentId {
    private String taskName;

    private String description;

    //Group Member
    private List<TaskModel> groupMember;

    private String groupName;

    private String due;

    private int status;

    public TaskOfAssignmentModel(String taskName, String description, List<TaskModel> groupMember, String groupName, String due, int status) {
        this.taskName = taskName;
        this.description = description;
        this.groupMember = groupMember;
        this.groupName = groupName;
        this.due = due;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public List<TaskModel> getGroupMember() {
        return groupMember;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDue() {
        return due;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TaskOfAssignmentModel{" +
                "TaskId='" + TaskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", groupMember=" + groupMember +
                ", groupName='" + groupName + '\'' +
                ", due='" + due + '\'' +
                ", status=" + status +
                '}';
    }
}
