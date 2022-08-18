package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Projection {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateProjection;
    private double prix;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Film film;
    @OneToMany(mappedBy = "projection")
    private Collection<Ticket>tickets;
    @ManyToOne
    private Seance seance;
}
