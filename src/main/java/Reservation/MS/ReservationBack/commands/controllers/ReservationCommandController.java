package Reservation.MS.ReservationBack.commands.controllers;

import Reservation.MS.ReservationBack.commonapi.commands.CreateReservationCommand;
import Reservation.MS.ReservationBack.commonapi.dtos.CreateReservationRequestDTO;
import Reservation.MS.ReservationBack.commonapi.events.CancelReservationEvent;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@CrossOrigin (origins = "http://localhost:4200/")
@RequestMapping(path="/commands/reservation")
@AllArgsConstructor
public class ReservationCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path="/create")
    public CompletableFuture<String> createReservation(@RequestBody CreateReservationRequestDTO request){
        CompletableFuture<String> commandeResponse = commandGateway.send(new CreateReservationCommand(
                UUID.randomUUID().toString(),
                request.getReference(),
                request.getLieuDep(),
                request.getLieuArr(),
                request.getDateReservation()
        ));
        return commandeResponse;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity= new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    @GetMapping("/eventStore/{reservationId}")
    public Stream eventStore(@PathVariable String reservationId){
        return eventStore.readEvents(reservationId).asStream();

    }


    @DeleteMapping(path = "/delete{reservationId}")
    public ResponseEntity<String> Delete(@PathVariable  String resevationId) {
        try {
            commandGateway.send(new CancelReservationEvent(resevationId));
            return ResponseEntity.ok("Car with id " + resevationId + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car with id " + resevationId + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete car with id " + resevationId + ": " + e.getMessage());
        }
    }
}
