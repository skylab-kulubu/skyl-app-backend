package com.skylab.skyl_app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
