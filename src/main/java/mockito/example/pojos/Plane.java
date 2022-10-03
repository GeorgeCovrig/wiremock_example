package mockito.example.pojos;

import java.util.List;
import java.util.Objects;

public class Plane {

    private int id;
    private String model;
    private List<String> passengers;
    private List<Pilot> pilots;

    public Plane(int id, String model, List<String> passengers, List<Pilot> pilots) {
        this.id = id;
        this.model = model;
        this.passengers = passengers;
        this.pilots = pilots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }

    public List<Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(List<Pilot> pilots) {
        this.pilots = pilots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return id == plane.id && Objects.equals(model, plane.model) && Objects.equals(passengers, plane.passengers) && Objects.equals(pilots, plane.pilots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, passengers, pilots);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
