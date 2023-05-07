package Reservation.MS.ReservationBack.commonapi.events;


import Reservation.MS.ReservationBack.commonapi.enums.ReservationStatus;
import lombok.Getter;

public class ReservationActivatedEvent extends BaseEvent<String>{
    @Getter private ReservationStatus status ;
    public ReservationActivatedEvent (String id,ReservationStatus status){
        super(id);
        this.status = status;
    }
}
