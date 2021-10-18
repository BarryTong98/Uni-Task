package comp5216.sydney.edu.au.finalproject.model;

import java.util.List;

public class Group {
    private String gourpId;
    private String name;
    private String description;
    private List<Assignment> assignmentList;
    private List<Person> personList;

    public Group(String gourpId, String name, String description, List<Assignment> assignmentList, List<Person> personList) {
        this.gourpId = gourpId;
        this.name = name;
        this.description = description;
        this.assignmentList = assignmentList;
        this.personList = personList;
    }

    public String getGourpId() {
        return gourpId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
