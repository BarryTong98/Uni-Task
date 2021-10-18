package comp5216.sydney.edu.au.finalproject.model;

import java.io.Serializable;

public class Person implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String description;
    private String degree;

    public Person() {}

    public Person(String userId, String name, String email, String description, String degree) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.description = description;
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getDegree() {
        return degree;
    }
}
