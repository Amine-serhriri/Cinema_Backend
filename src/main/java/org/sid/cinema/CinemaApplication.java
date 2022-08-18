package org.sid.cinema;

import org.sid.cinema.Service.ICinemaInitService;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Ticket;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication {


    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }
@Bean
  CommandLineRunner start(ICinemaInitService iCinemaInitService,
                           RepositoryRestConfiguration restConfiguration){
    restConfiguration.exposeIdsFor(Film.class,Salle.class, Ticket.class);

        return args -> {
            iCinemaInitService.initVille();
            iCinemaInitService.initCinema();
            iCinemaInitService.initSalle();
            iCinemaInitService.initPlaces();
            iCinemaInitService.initSeances();
            iCinemaInitService.initCategories();
            iCinemaInitService.initFilms();
            iCinemaInitService.initProjections();
            iCinemaInitService.initTickets();
        };
  }

}
