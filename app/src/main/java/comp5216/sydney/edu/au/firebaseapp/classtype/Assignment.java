package comp5216.sydney.edu.au.firebaseapp.classtype;


import java.io.Serializable;
import java.util.List;

public class Assignment implements Serializable {
    //primary key
    private String assignmentId;

    //foreign key
    private String groupId;

    //attribute
    private String assignmentName;
    private String description;
    private String dueDate;
    private int states;

    //List
    private List<Tasks> tasksList;

    public Assignment(String assignmentId, String assignmentName, String description, String dueDate, String groupId, List<Tasks> tasksList) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.description = description;
        this.dueDate = dueDate;
        this.groupId = groupId;
        this.states = 0;
        this.tasksList = tasksList;
    }

    public Assignment(){

    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public List<Tasks> getTaskList() {
        return tasksList;
    }

    public void setTaskList(List<Tasks> tasksList) {
        this.tasksList = tasksList;
    }

    public String getPercentage(){
        float percentage=0;
        for(Tasks i: tasksList){
            if (i.getStates()==1){
                percentage++;
            }
        }
        percentage/= tasksList.size();

        String s=String.format("The assignment has been completed");
        if (percentage==0.0){
            s+=" 0%";
        }else {
            s+=String.format(" %.1f%",Math.round(percentage*1000)/10.0);
        }
        return s;
    }


}
