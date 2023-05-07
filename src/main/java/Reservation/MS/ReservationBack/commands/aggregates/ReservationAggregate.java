package Reservation.MS.ReservationBack.commands.aggregates;


import Reservation.MS.ReservationBack.commonapi.commands.CreateReservationCommand;
import Reservation.MS.ReservationBack.commonapi.commands.DeleteReservationCommand;
import Reservation.MS.ReservationBack.commonapi.enums.ReservationStatus;
import Reservation.MS.ReservationBack.commonapi.events.CancelReservationEvent;
import Reservation.MS.ReservationBack.commonapi.events.ReservationActivatedEvent;
import Reservation.MS.ReservationBack.commonapi.events.ReservationCreatedEvent;
import Reservation.MS.ReservationBack.queries.repository.ReservationRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;

@Aggregate
public class ReservationAggregate {
    @AggregateIdentifier
    private String reservationId;
    private LocalDate dateReservation;
    private String lieuDep;
    private String lieuArr;
    private String reference;
    private ReservationStatus status;

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationAggregate(){
        //Required by AXON
    }
    @CommandHandler
    public ReservationAggregate(CreateReservationCommand command) {
        //Required by Axon
        AggregateLifecycle.apply(new ReservationCreatedEvent(
                command.getId(),
                command.getReference(),
                command.getDateReservation(),
                command.getLieuDep(),
                command.getLieuArr(),

                ReservationStatus.CREATED                ));
    }
    @EventSourcingHandler
    public void on(ReservationCreatedEvent event)
    {
        this.reservationId=event.getId();
        this.reference=event.getReference();
        this.dateReservation=event.getDateReservation();
        this.lieuDep= event.getLieuDep();
        this.lieuArr=event.getLieuArr();


        this.status=ReservationStatus.CREATED;
        AggregateLifecycle.apply(new ReservationActivatedEvent(
                event.getId(),
                ReservationStatus.CREATED
        ));
    }
    @EventSourcingHandler
    public void on(ReservationActivatedEvent event){
        this.status=event.getStatus();
    }





    @CommandHandler
    public void handle(DeleteReservationCommand command) {
        if (this.status == ReservationStatus.CANCELED) {
            throw new IllegalStateException(" Reservation is already deleted");
        }
        AggregateLifecycle.apply(new CancelReservationEvent.getId());
    }
    @EventSourcingHandler
    public void on(CancelReservationEvent event) {
        AggregateLifecycle.markDeleted();
    }
}

