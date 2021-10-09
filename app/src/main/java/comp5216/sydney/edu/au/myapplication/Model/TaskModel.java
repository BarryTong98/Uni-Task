package comp5216.sydney.edu.au.myapplication.Model;

public class TaskModel extends TaskId {

    private String name;

    private String degree;

    private String description;

    private int status;

    public TaskModel(String name, String degree, String description, int status) {
        this.name = name;
        this.degree = degree;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", degree='" + degree + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
