package edu.dadaev.greenpoint.entity;

import edu.dadaev.greenpoint.enumerated.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "app_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String firstName;

    @Column()
    private String lastName;

    @ColumnDefault("0")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Transaction> transactions;


}
