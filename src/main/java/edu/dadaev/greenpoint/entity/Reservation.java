package edu.dadaev.greenpoint.entity;

import edu.dadaev.greenpoint.enumerated.ReservationPaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private User user;

    private LocalDate startDate;

    private LocalDate endDate;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private ReservationPaymentStatus status;

    @OneToMany(mappedBy = "reservation")
    private List<Transaction> transactions;
}
