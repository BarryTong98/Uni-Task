package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private ArrayList<Member> memberList;
    private ArrayList<Assignment> assignmentList;

    public Group(List<Member> memberList, List<Assignment> assignmentList) {
        this.memberList = (ArrayList<Member>) memberList;
        this.assignmentList = (ArrayList<Assignment>) assignmentList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList =(ArrayList<Member>) memberList;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = (ArrayList<Assignment>) assignmentList;
    }

}
