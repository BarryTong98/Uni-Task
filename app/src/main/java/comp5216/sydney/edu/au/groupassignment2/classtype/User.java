package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    //primary key
    private String userId; //uuid

    //foreign key
    private String userName;
    private String description;
    private String email;
    private String degree;
    private String imageURL;

    //List
    private List<Task> taskList;
    private List<Group> groupList;

    public User(String userId, String userName, String email, String imageURL) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
