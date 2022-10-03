package mockito.example.service;

import mockito.example.exceptions.ControlTowerException;
import mockito.example.exceptions.NotificationServiceException;
import mockito.example.exceptions.PilotNotAvailableException;
import mockito.example.exceptions.PlaneNotFoundException;
import mockito.example.pojos.Flight;
import mockito.example.pojos.Pilot;
import mockito.example.pojos.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandCenterService {

    @Autowired
    private PilotService pilotService;

    @Autowired
    private PlaneService planeService;

    @Autowired
    private ControlTowerService controlTowerService;

    @Autowired
    private NotificationService notificationService;

    public Flight prepareFlight(PlaneService.FlightType flightType, int idFlight) throws Exception {
        Flight flight = new Flight();
        boolean flightCanTakeOff = false;
        try {
            List<Pilot> pilotList = pilotService.getPilotsForFlight();
            Plane plane = planeService.getPlaneForFlightType(flightType);
            flightCanTakeOff = controlTowerService.verifyIfFlightCanTakePlace(idFlight);
            notificationService.notifySystems(idFlight);
            plane.setPilots(pilotList);
            flight.setPlane(plane);
            flight.setConfirmedTakeOff(flightCanTakeOff);
            return flight;
        } catch (Exception e) {
            rollbackServices(e, flightType, flightCanTakeOff, idFlight);
        }
        return null;
    }

    public void rollbackServices(Exception e, PlaneService.FlightType flightType, boolean flightCanTakeOff, int idFlight) throws Exception {
        if (e instanceof PilotNotAvailableException) {
            System.out.println("Couldn't find pilot to allocate! ");
        }
        if (e instanceof PlaneNotFoundException) {
            pilotService.rollbackLastPilots();
        }
        if (e instanceof ControlTowerException) {
            pilotService.rollbackLastPilots();
            planeService.rollbackForFlightType(flightType);
        }
        if (e instanceof NotificationServiceException) {
            pilotService.rollbackLastPilots();
            planeService.rollbackForFlightType(flightType);
            controlTowerService.rollbackFlight(idFlight, flightCanTakeOff);
        }
        throw e;
    }

    public PilotService getPilotService() {
        return pilotService;
    }

    public void setPilotService(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    public PlaneService getPlaneService() {
        return planeService;
    }

    public void setPlaneService(PlaneService planeService) {
        this.planeService = planeService;
    }

    public ControlTowerService getControlTowerService() {
        return controlTowerService;
    }

    public void setControlTowerService(ControlTowerService controlTowerService) {
        this.controlTowerService = controlTowerService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
