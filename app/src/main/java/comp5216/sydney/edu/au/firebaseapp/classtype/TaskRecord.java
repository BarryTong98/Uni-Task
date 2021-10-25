package comp5216.sydney.edu.au.firebaseapp.classtype;

public class TaskRecord {
    private String taskID;
    private boolean isChecked;

    public TaskRecord(String taskID, boolean isChecked) {
        this.taskID = taskID;
        this.isChecked = isChecked;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
