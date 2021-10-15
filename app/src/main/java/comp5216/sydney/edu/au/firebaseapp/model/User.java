package comp5216.sydney.edu.au.firebaseapp.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String userId; //uuid
    private String name;
    private String email;
    private String degree;
    private String description;
    private String imageURL;
    private List<Integer> taskList; //放的是task id
    private List<Integer> groupList; //放的是group id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public List<Integer> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Integer> taskList) {
        this.taskList = taskList;
    }

    public List<Integer> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Integer> groupList) {
        this.groupList = groupList;
    }
}
