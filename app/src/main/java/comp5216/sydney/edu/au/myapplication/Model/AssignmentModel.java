package comp5216.sydney.edu.au.myapplication.Model;

import com.google.android.gms.tasks.Task;

import java.util.List;

public class AssignmentModel extends AssignmentId{
    private String user;

    private String due;

    private List<TaskModel> tasks;

    private int status;

    public AssignmentModel(String user, String due, List<TaskModel> tasks, int status) {
        this.user = user;
        this.due = due;
        this.tasks = tasks;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public String getDue() {
        return due;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AssignmentModel{" +
                "AssignmentId='" + AssignmentId + '\'' +
                ", user='" + user + '\'' +
                ", due='" + due + '\'' +
                ", tasks=" + tasks +
                ", status=" + status +
                '}';
    }
}
