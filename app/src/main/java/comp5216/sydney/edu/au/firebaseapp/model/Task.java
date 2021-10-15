package comp5216.sydney.edu.au.firebaseapp.model;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    private String taskId;
    private String taskName;
    private String description;
    private String assignmentId;
    private String groupName;
    private String dueDate;
    private List<User> userList;
}
