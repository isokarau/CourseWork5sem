package modules.core.models;

import java.io.Serializable;

public class Training implements Serializable {
    private String name;
    private String date;
    private int places;

    public Training(String name, String date, int places) {
        this.name = name;
        this.date = date;
        this.places = places;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getPlaces() {
        return places;
    }
    public void setPlaces(int places) {
        this.places = places;
    }
}
