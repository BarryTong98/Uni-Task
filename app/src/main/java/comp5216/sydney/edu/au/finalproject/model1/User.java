package comp5216.sydney.edu.au.finalproject.model1;

import java.io.Serializable;
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
}
