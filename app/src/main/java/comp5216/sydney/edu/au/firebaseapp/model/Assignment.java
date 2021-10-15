package comp5216.sydney.edu.au.firebaseapp.model;

import java.io.Serializable;
import java.util.List;

public class Assignment implements Serializable {
    private String assignmentId;
    private String name;
    private String description;
    private String dueDate;
    private String groupId;
    private int states;
    private List<Task> taskList;
}
