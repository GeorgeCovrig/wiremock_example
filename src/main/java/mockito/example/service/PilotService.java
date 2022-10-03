package mockito.example.service;

import mockito.example.exceptions.PilotNotAvailableException;
import mockito.example.pojos.Pilot;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PilotService {

    private List<Pilot> pilots;

    private Set<Integer> occupiedPilots;
    private int counterPilots;

    public PilotService() {
        pilots = Arrays.asList(new Pilot(1, "AAAA", "AAAA@something.com"),
                new Pilot(2, "BBBB", "BBBB@something.com"),
                new Pilot(3, "CCCC", "CCCC@something.com"),
                new Pilot(4, "DDDD", "DDDD@something.com"),
                new Pilot(5, "EEEE", "EEEE@something.com"),
                new Pilot(6, "FFFF", "FFFF@something.com"),
                new Pilot(7, "GGGG", "GGGG@something.com"));
        this.occupiedPilots = new HashSet<>();
        this.counterPilots = 0;
    }

    public List<Pilot> getPilotsForFlight() throws PilotNotAvailableException {
       List<Pilot> freePilotsToReturn = new ArrayList<>();
       if(counterPilots > pilots.size()-2){
           throw new PilotNotAvailableException("NO FREE PILOTS AVAILABLE");
       }
       freePilotsToReturn.add(pilots.get(counterPilots));
       occupiedPilots.add(counterPilots);
       counterPilots++;
       freePilotsToReturn.add(pilots.get(counterPilots));
       occupiedPilots.add(counterPilots);
       counterPilots++;
       return freePilotsToReturn;
    }

    public boolean verifyIfPilotWithIdIsOccupied(int id){
        return occupiedPilots.contains(id);
    }

    public void rollbackLastPilots() {
        counterPilots = counterPilots-2;
        occupiedPilots.remove(counterPilots);
        occupiedPilots.remove(counterPilots+1);
    }
}
