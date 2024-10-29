package com.skylab.skyl_app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "alias")
    private String alias;

    @Column(name = "click_count")
    private int clickCount;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    @JsonManagedReference
    private User createdBy;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;


}
