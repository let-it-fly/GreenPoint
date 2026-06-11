package edu.dadaev.greenpoint.entity;

import edu.dadaev.greenpoint.enumerated.DisputeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    private User renter;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String description;

    @Enumerated(EnumType.STRING)
    private DisputeStatus status;


}
