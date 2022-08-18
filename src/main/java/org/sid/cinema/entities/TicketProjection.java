package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.ManyToOne;

@Projection(name = "ticketsProj",types = Ticket.class)
public interface TicketProjection {
    public Long getId();
    public String getNomClient();
    public double getPrixAchat();
    public int getCodePaiement();
    public boolean getReserve();
    public Place getPlace();




}
