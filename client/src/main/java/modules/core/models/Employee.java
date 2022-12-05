package modules.core.models;

public class Employee extends Manager {
    private String level;
    private int completedTrainings;

    public Employee() {
        super();
    }

    public Employee(int id, String login, String password, String surname, String name, String patronymic, String email, String level, int trainings) {
        super(id, login, password, surname, name, patronymic, email);
        this.level = level;
        this.completedTrainings = trainings;
    }

    @Override
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public int getCompletedTrainings() {
        return completedTrainings;
    }

    public void setCompletedTrainings(int completedTrainings) {
        this.completedTrainings = completedTrainings;
    }
}
