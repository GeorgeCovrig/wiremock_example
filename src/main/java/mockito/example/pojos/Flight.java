package mockito.example.pojos;

import java.util.Objects;

public class Flight {
    private boolean confirmedTakeOff;
    private Plane plane;

    public Flight(boolean confirmedTakeOff, Plane plane) {
        this.confirmedTakeOff = confirmedTakeOff;
        this.plane = plane;
    }

    public Flight() {
    }

    public boolean isConfirmedTakeOff() {
        return confirmedTakeOff;
    }

    public void setConfirmedTakeOff(boolean confirmedTakeOff) {
        this.confirmedTakeOff = confirmedTakeOff;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return confirmedTakeOff == flight.confirmedTakeOff && Objects.equals(plane, flight.plane);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmedTakeOff, plane);
    }
}
