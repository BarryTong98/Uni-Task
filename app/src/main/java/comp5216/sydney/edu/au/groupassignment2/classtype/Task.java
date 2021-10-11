package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.util.List;

public class Task extends Mission {
    private String assignmentId;
    private String description;

    public Task(String id, String description, String dateTime, List<Member> memberList, String assignmentId) {
        super(id, description, dateTime, memberList);
        this.assignmentId = assignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignment(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getMemberName() {
        String memberName="";
        List<Member> memberList=this.getMemberList();
        for (Member i:memberList){
            memberName+=i.getName()+" ";
        }
        return memberName;
    }

}
