package com.itgreenzone.patterns.behavioral.mediator;

import com.itgreenzone.patterns.behavioral.mediator.after.*;
import com.itgreenzone.patterns.behavioral.mediator.exercise.FuelTruck;
import com.itgreenzone.patterns.behavioral.mediator.modern.EventCoordinator;
import com.itgreenzone.patterns.behavioral.mediator.modern.OpsEvent;
import com.itgreenzone.patterns.behavioral.mediator.solution.FuelAwareCoordinator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MediatorTest {

    private List<String> log;
    private Gate gate;
    private GroundCrew crew;
    private Catering catering;
    private PassengerNotifier pax;
    private Flight flight;

    @BeforeEach
    void setUp() {
        log = new ArrayList<>();
        gate = new Gate(log);
        crew = new GroundCrew(log);
        catering = new Catering(log);
        pax = new PassengerNotifier(log);
        flight = new Flight("SQ322", log);
    }

    @Test
    void shortDelayCoordinatesEveryoneButKeepsTheGate() {
        TurnaroundCoordinator ops = new TurnaroundCoordinator(gate, crew, catering, pax);
        ops.register(gate, crew, catering, pax, flight);
        flight.delay(30);
        assertEquals(List.of("FLIGHT: SQ322 delayed 30 min", "CREW: SQ322 +30 min", "CATERING: SQ322 +30 min",
                "PAX: SQ322 delayed 30 min"), log);
    }

    @Test
    void longDelayCascadesIntoAGateChange() {
        TurnaroundCoordinator ops = new TurnaroundCoordinator(gate, crew, catering, pax);
        ops.register(gate, crew, catering, pax, flight);
        flight.delay(90);
        assertTrue(log.contains("GATE: SQ322 -> C9"));
        assertTrue(log.contains("CREW: SQ322 moves to C9"));
        assertEquals("PAX: SQ322 now boards at C9", log.get(log.size() - 1));
    }

    @Test
    void sameReactionsAsTheTightlyCoupledVersion() {
        List<String> beforeLog = new ArrayList<>();
        var bCrew = new com.itgreenzone.patterns.behavioral.mediator.before.GroundCrew(beforeLog);
        var bGate = new com.itgreenzone.patterns.behavioral.mediator.before.Gate(bCrew, beforeLog);
        var bFlight = new com.itgreenzone.patterns.behavioral.mediator.before.Flight("SQ322", bGate, bCrew,
                new com.itgreenzone.patterns.behavioral.mediator.before.Catering(beforeLog), beforeLog);
        bFlight.delay(90);

        TurnaroundCoordinator ops = new TurnaroundCoordinator(gate, crew, catering, pax);
        ops.register(gate, crew, catering, pax, flight);
        flight.delay(90);
        assertEquals(beforeLog, log.subList(1, log.size()), "same reactions, minus the new FLIGHT log line");
    }

    @Test
    void colleagueWithoutAMediatorFailsFast() {
        assertThrows(NullPointerException.class, () -> flight.delay(10));
    }

    @Test
    void eventRecordsAndSealedSwitch() {
        EventCoordinator hub = new EventCoordinator();
        hub.publish(new OpsEvent.FlightDelayed("SQ322", 70));
        assertEquals(List.of("CREW +70", "CATERING +70", "PAX delayed 70", "CREW -> C7", "PAX -> C7"), hub.actions());
    }

    @Test
    void fuelDelaySolutionChangesOnlyTheMediator() {
        FuelAwareCoordinator ops = new FuelAwareCoordinator(gate, crew, catering, pax);
        ops.register(gate, crew, catering, pax, flight);
        FuelTruck truck = new FuelTruck(log);
        truck.setControl(ops);
        truck.reportDelay("SQ322", 20);
        assertTrue(log.contains("CREW: SQ322 +20 min"));
        assertEquals("PAX: SQ322 refuelling, please stay near the gate", log.get(log.size() - 1));
    }
}
