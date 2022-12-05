package modules.core.models;

import java.io.Serializable;

public class Review implements Serializable {
    private int managerId;
    private int employeeId;
    private int mark;
    private String comment;
    
    public Review(int managerId, int employeeId, int mark, String comment) {
        this.managerId = managerId;
        this.employeeId = employeeId;
        this.mark = mark;
        this.comment = comment;
    }

    public int getManagerId() {
        return managerId;
    }
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
