package Reservation.MS.ReservationBack.commonapi.dtos;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateReservationRequestDTO {

    private String reference;
    private LocalDate dateReservation;
    private String lieuDep;
    private String lieuArr;

}