package mockito.example.controller;

import mockito.example.pojos.Flight;
import mockito.example.service.CommandCenterService;
import mockito.example.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementSystemController {

    @Autowired
    CommandCenterService commandCenterService;

    @GetMapping("/{idFlight}/{flightType}")
    public ResponseEntity<Flight> prepareFlight(@PathVariable(value = "idFlight") int idFlight, @PathVariable(value = "flightType") String flightType){
        try {
            Flight flight = this.commandCenterService.prepareFlight(PlaneService.FlightType.valueOf(flightType), idFlight);
            return ResponseEntity.ok().body(flight);
        } catch (Exception e) {
           return ResponseEntity.internalServerError().build();
        }
    }
}
