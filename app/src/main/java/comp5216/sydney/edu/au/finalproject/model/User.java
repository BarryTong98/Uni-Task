package comp5216.sydney.edu.au.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String userId; //uuid
    private String name;
    private String email;
    private String degree;
    private String description;
    private String imageURL;
    private ArrayList<Task> taskList;
    private ArrayList<Group> groupList;
    private int status;

    public User() {
    }

    public User(String userId, String name, String email, String degree, String description, String imageURL, ArrayList<Task> taskList, ArrayList<Group> groupList) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.degree = degree;
        this.description = description;
        this.imageURL = imageURL;
        this.taskList = taskList;
        this.groupList = groupList;
        status = 0;
    }


    public User(String userId, String name, String degree, String description) {
        this.userId = userId;
        this.name = name;
        this.degree = degree;
        this.description = description;
        taskList = new ArrayList<>();
        groupList = new ArrayList<>();

    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDegree() {
        return degree;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    public int getStatus() {
        return status;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", degree='" + degree + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", taskList=" + taskList +
                ", groupList=" + groupList +
                '}';
    }
}
