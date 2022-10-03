package mockito.example.service;

import mockito.example.exceptions.PlaneNotFoundException;
import mockito.example.pojos.Plane;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PlaneService {

    public enum FlightType {SHORT_FLIGHT, LONG_FLIGHT}

    public PlaneService() {
        this.counterShortFlightPlanes = 0;
        this.counterLongFlightPlanes = 0;
    }

    private final List<Plane> shortFlightPlaneList = Arrays.asList(
            new Plane(1, "model A", null, null),
            new Plane(2, "model B", null, null),
            new Plane(100, "model C", null, null),
            new Plane(4,"model D", null, null)
    );

    private final List<Plane> longFlightPlaneList = Arrays.asList(
            new Plane(5, "model E", null, null),
            new Plane(6, "model F", null, null),
            new Plane(7, "model G", null, null),
            new Plane(8, "model H", null, null)
    );

    private int counterShortFlightPlanes ;
    private int counterLongFlightPlanes;

    public Plane getPlaneForFlightType(FlightType flightType) throws PlaneNotFoundException {
        if(counterLongFlightPlanes > longFlightPlaneList.size()-1 && flightType == FlightType.LONG_FLIGHT) {
            throw new PlaneNotFoundException("Long flight plane not available!");
        }
        if(flightType == FlightType.SHORT_FLIGHT && counterShortFlightPlanes > shortFlightPlaneList.size()-1){
            throw new PlaneNotFoundException("Short flight plane not available!");
        }
        if(flightType == FlightType.LONG_FLIGHT){
            return getLongFlightPlane();
        } else {
            return getShortFlightPlane();
        }
    }

    public Plane getShortFlightPlane() {
        Plane plane = shortFlightPlaneList.get(counterShortFlightPlanes);
        counterShortFlightPlanes++;
        return plane;
    }

    public Plane getLongFlightPlane() {
        Plane plane = longFlightPlaneList.get(counterLongFlightPlanes);
        counterLongFlightPlanes++;
        return plane;
    }

    public void rollbackForFlightType(FlightType flightType){
        if(flightType == FlightType.SHORT_FLIGHT){
            counterShortFlightPlanes = counterShortFlightPlanes - 1;
        }
        if(flightType == FlightType.LONG_FLIGHT){
            counterLongFlightPlanes = counterLongFlightPlanes - 1;
        }
    }
}
