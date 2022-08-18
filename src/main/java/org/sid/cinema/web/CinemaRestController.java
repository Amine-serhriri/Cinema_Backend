package org.sid.cinema.web;

import lombok.Data;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.repository.FilmRepository;
import org.sid.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable  Long id) throws IOException {
        Film f=filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/Desktop/Gestion_Cinema/images/"+f.getPhoto()));
  }
    @PostMapping("/payerTicket")
    @Transactional
    public List<Ticket> payerTicket(@RequestBody TicketForm ticketForm){
        List<Ticket>Listtickets=new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            Ticket ticket=ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReserve(true);
            ticket.setCodePaiement(ticketForm.getCodePaiement());
            ticketRepository.save(ticket);
            Listtickets.add(ticket);
        });
        return  Listtickets;
    }
}
@Data
class TicketForm{
    private String nomClient;
    private int codePaiement ;
    private List<Long>tickets=new ArrayList<>();
}
