package modules.core.tables;

import java.io.Serializable;

public class MyReview implements Serializable {
    private String managerFIO;
    private int mark;
    private String comment;

    public MyReview(String managerFIO, int mark, String comment) {
        this.managerFIO = managerFIO;
        this.mark = mark;
        this.comment = comment;
    }

    public String getManagerFIO() {
        return managerFIO;
    }
    public void setManagerFIO(String managerFIO) {
        this.managerFIO = managerFIO;
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
