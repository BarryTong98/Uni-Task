package comp5216.sydney.edu.au.myapplication.model1;

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
    private List<Task> taskList;
    private List<Group> groupList;
    private int status;

    public User() {
    }

    public User(String userId, String name, String email, String degree, String description, String imageURL, List<Task> taskList, List<Group> groupList) {
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
        taskList = new ArrayList<Task>();
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

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public int getStatus() {
        return status;
    }

    public void setTaskList(List<Task> taskList) {
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
