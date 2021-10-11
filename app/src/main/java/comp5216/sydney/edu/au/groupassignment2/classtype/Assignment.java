package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.io.Serializable;
import java.util.List;

public class Assignment extends Mission implements Serializable {
    private List<Task> taskList;



    public Assignment(String AssignmentId, String description, String dateTime, List<Member> memberList, List<Task> taskList) {
        super(AssignmentId, description, dateTime, memberList);
        this.taskList = taskList;

    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public String getPercentage(){
        float percentage=0;
        for(Task i: taskList){
            if (i.isCompleted()){
                percentage++;
            }
        }
        percentage/=taskList.size();

        String s=String.format("The assignment has been completed");
        if (percentage==0.0){
            s+=" 0%";
        }else {
            s+=String.format(" %.1f%",Math.round(percentage*1000)/10.0);
        }
         return s;
    }



}
