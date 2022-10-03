package mockito.example.service;

import mockito.example.exceptions.ControlTowerException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ControlTowerService {

    public Map<Integer, Boolean> takeoffApprovals;

    public ControlTowerService() {
        this.takeoffApprovals = new HashMap<>();
        this.takeoffApprovals.put(1, false);
        this.takeoffApprovals.put(2, false);
        this.takeoffApprovals.put(3, false);
        this.takeoffApprovals.put(4, true);
        this.takeoffApprovals.put(100, true);
    }

    public boolean verifyIfFlightCanTakePlace(int idFlight) throws ControlTowerException {
        if(this.takeoffApprovals.get(idFlight) == null){
            throw new ControlTowerException("Id of the flight not found");
        }
        boolean approved = this.takeoffApprovals.get(idFlight);
        if(approved){
            this.takeoffApprovals.put(idFlight, false);
        }
        return approved;
    }

    public void rollbackFlight(int idFlight, boolean approved){
        if(approved) {
            this.takeoffApprovals.put(idFlight, true);
        }
    }
}
