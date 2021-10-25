package comp5216.sydney.edu.au.firebaseapp.classtype;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    //primary key
    private String userId; //uuid

    //foreign key
    private String userName;
    private String email;
    private String degree;
    private String description;
    private String imageURL;

    //List
    private List<String> taskList; //放的是task id
    private List<String> groupList; //放的是group id

    private int status;


    public User(String userId, String userName, String email, String imageURL) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.imageURL = imageURL;
        degree=null;
        description=null;
        taskList=null;
        groupList=null;
        status=0;
    }

    public User(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


    public List<String> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<String> taskList) {
        this.taskList = taskList;
    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
