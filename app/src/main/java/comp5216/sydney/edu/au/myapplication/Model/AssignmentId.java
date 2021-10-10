package comp5216.sydney.edu.au.myapplication.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class AssignmentId {
    @Exclude
    public String AssignmentId;

    public <T extends  AssignmentId> T withId(@NonNull final String id){
        this.AssignmentId = id;
        return (T) this;
    }
}
