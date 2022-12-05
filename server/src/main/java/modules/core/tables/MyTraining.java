package modules.core.tables;

import java.io.Serializable;

public class MyTraining implements Serializable {
    private String trainingName;
    private String trainingDate;
    private String comment;

    public MyTraining(String trainingName, String trainingDate, String comment) {
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.comment = comment;
    }

    public String getTrainingName() {
        return trainingName;
    }
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }
    public String getTrainingDate() {
        return trainingDate;
    }
    public void setTrainingDate(String trainingDate) {
        this.trainingDate = trainingDate;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
