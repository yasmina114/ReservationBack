package Reservation.MS.ReservationBack.commonapi.commands;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;


public class CreateReservationCommand extends BaseCommand<String>{
    @Getter
    private String reference;
    @Getter
    private LocalDate dateReservation;

    @Getter private String lieuDep;
    @Getter private String lieuArr;

    public CreateReservationCommand(String id, String reference, String lieuDep, String lieuArr,LocalDate dateReservation) {
        super(id);
        this.reference = reference;
        this.lieuDep = lieuDep;
        this.lieuArr = lieuArr;
        this.dateReservation = dateReservation;

    }

}
