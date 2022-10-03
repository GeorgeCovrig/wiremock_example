package mockito.example;

import mockito.example.pojos.Flight;
import mockito.example.pojos.Pilot;
import mockito.example.pojos.Plane;
import mockito.example.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class MockitoApplicationTests {

	@Spy
	private NotificationService notificationService;

	@Spy
	private PlaneService planeService;

	@Mock
	private PilotService pilotService;

	@Spy
	private ControlTowerService controlTowerService;

	@InjectMocks
	private CommandCenterService commandCenterService;

	@Test
	public void testNormalFlowShortFlight() throws Exception {
		this.pilotService.getPilotsForFlight();
		Mockito.reset(this.pilotService);
		List<Pilot> pilotsToReturn = Arrays.asList(new Pilot(1, "AAAA", "AAAA@something.com"),
				new Pilot(2, "BBBB", "BBBB@something.com"));
		when(pilotService.getPilotsForFlight()).thenReturn(pilotsToReturn);
		when(controlTowerService.verifyIfFlightCanTakePlace(1)).thenReturn(true);
		Flight flight = this.commandCenterService.prepareFlight(PlaneService.FlightType.SHORT_FLIGHT, 1);
		verify(this.pilotService, times(1)).getPilotsForFlight();
		verify(this.planeService, times(1)).getPlaneForFlightType(any());
		verify(this.controlTowerService, times(1)).verifyIfFlightCanTakePlace(1);
		verify(this.controlTowerService, times(0)).rollbackFlight(1, true);
		verify(this.pilotService, times(0)).rollbackLastPilots();
		verify(this.planeService, times(0)).rollbackForFlightType(any());
		verify(this.planeService, times(0)).getLongFlightPlane();
		verify(this.planeService, times(1)).getShortFlightPlane();
		assertEquals(pilotsToReturn, flight.getPlane().getPilots());
		assertTrue(flight.isConfirmedTakeOff());
		assertEquals(new Plane(1, "model A", null, pilotsToReturn), flight.getPlane());
	}

	@Test
	public void testNormalFlowLongFlight() throws Exception {
		List<Pilot> pilotsToReturn = Arrays.asList(new Pilot(1, "AAAA", "AAAA@something.com"),
				new Pilot(2, "BBBB", "BBBB@something.com"));
		when(pilotService.getPilotsForFlight()).thenReturn(pilotsToReturn);
		when(controlTowerService.verifyIfFlightCanTakePlace(1)).thenReturn(true);

		Flight flight = this.commandCenterService.prepareFlight(PlaneService.FlightType.LONG_FLIGHT, 1);

		verify(this.pilotService, times(1)).getPilotsForFlight();
		verify(this.planeService, times(1)).getPlaneForFlightType(any());
		verify(this.planeService, times(1)).getPlaneForFlightType(any());
		verify(this.planeService, times(1)).getLongFlightPlane();
		verify(this.planeService, times(0)).getShortFlightPlane();
		verify(this.controlTowerService, times(1)).verifyIfFlightCanTakePlace(1);
		verify(this.controlTowerService, times(0)).rollbackFlight(1, true);
		verify(this.pilotService, times(0)).rollbackLastPilots();
		verify(this.planeService, times(0)).rollbackForFlightType(any());
		assertEquals(pilotsToReturn, flight.getPlane().getPilots());
		assertTrue(flight.isConfirmedTakeOff());
		assertEquals(new Plane(5, "model E", null, pilotsToReturn), flight.getPlane());
	}
}
