package Reservation.MS.ReservationBack.commonapi.query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetReservationByIdQuery {
    private String id;
}
