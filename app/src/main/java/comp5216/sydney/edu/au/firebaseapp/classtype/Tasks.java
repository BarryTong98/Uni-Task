package comp5216.sydney.edu.au.firebaseapp.classtype;

import java.io.Serializable;
import java.util.List;

public class Tasks implements Serializable {
    //primary key
    private String taskId;

    //foreign key
    private String assignmentId;
    private String groupId;

    //attribute
    private String taskName;
    private String description;
    private String dueDate;
    private int states;
    private String groupName;



    //List
    private List<User> userList;

    public Tasks(String taskId, String assignmentId, String groupId, String taskName, String description,
                 String dueDate, String groupName, List<User> userList) {
        this.taskId = taskId;
        this.assignmentId = assignmentId;
        this.groupId = groupId;
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.groupName = groupName;
        this.userList = userList;
        states=0;
    }

    public Tasks(){

    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getMemberName() {
        String memberName="";
        List<User> memberList=this.getUserList();
        for (User i:memberList){
            memberName+=i.getUserName()+" ";
        }
        return memberName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }
}
