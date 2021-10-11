package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.io.Serializable;
import java.util.List;

import comp5216.sydney.edu.au.groupassignment2.classtype.Member;

public abstract class Mission implements Serializable {
     private String id;
     private String description;
     private String dateTime;
     private List<Member> memberList;
     private boolean isCompleted;

     public Mission(String id, String description, String dateTime, List<Member> memberList) {
          this.id = id;
          this.description = description;
          this.dateTime = dateTime;
          this.memberList = memberList;
          isCompleted=false;
     }

     public Mission() {
          super();
     }

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public String getDateTime() {
          return dateTime;
     }

     public void setDateTime(String dateTime) {
          this.dateTime = dateTime;
     }

     public List<Member> getMemberList() {
          return memberList;
     }

     public void setMemberList(List<Member> memberList) {
          this.memberList = memberList;
     }

     public boolean isCompleted() {
          return isCompleted;
     }

     public void setCompleted(boolean completed) {
          isCompleted = completed;
     }

}
