package comp5216.sydney.edu.au.groupassignment2.classtype;

import java.io.Serializable;

import comp5216.sydney.edu.au.groupassignment2.R;

public class Member implements Serializable {
    private String id;
    private String name;
    int imageId= R.drawable.image;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getimageId() {
        return imageId;
    }

    public void setimageId(int imageId) {
        this.imageId = imageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
