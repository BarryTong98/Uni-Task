package comp5216.sydney.edu.au.finalproject.model;

public class Task {

    private String name;

    private String degree;

    private String description;

    private int status;

    public Task(String name, String degree, String description, int status) {
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
