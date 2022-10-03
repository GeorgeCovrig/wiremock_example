package mockito.example.service;

import mockito.example.exceptions.NotificationServiceException;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    public void notifySystems(int idFlight) throws NotificationServiceException {
        if(idFlight >100){
            throw new NotificationServiceException("Systems were not notified!");
        }
        notifyStaff(idFlight);
        notifyPassengers(idFlight);
    }

    private void notifyPassengers(int idFlight) {
        System.out.println("Passengers were notified "+ idFlight +"!");
    }

    private void notifyStaff(int idFlight) {
        System.out.println("Staff notified "+ idFlight+"!");
    }
}
