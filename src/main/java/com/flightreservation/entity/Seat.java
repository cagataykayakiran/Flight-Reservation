package com.flightreservation.entity;

import com.flightreservation.common.BaseEntity;
import com.flightreservation.enums.SeatClass;
import com.flightreservation.enums.SeatStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seatPosition", "flight_id"})
)
public class Seat extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private String seatNumber;

    @Column(nullable = false, updatable = false)

    private Integer seatPosition;

    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    private BigDecimal price;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus = SeatStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
