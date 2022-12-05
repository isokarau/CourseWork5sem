package modules.core.tables;

import java.io.Serializable;

public class EmployeeTable implements Serializable {
    private int id;
    private String fio;
    private String email;
    private String level;
    private int trainings;

    public EmployeeTable(int id, String fio, String email, String level, int trainings) {
        this.id = id;
        this.fio = fio;
        this.email = email;
        this.level = level;
        this.trainings = trainings;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public int getTrainings() {
        return trainings;
    }
    public void setTrainings(int trainings) {
        this.trainings = trainings;
    }
    
}
