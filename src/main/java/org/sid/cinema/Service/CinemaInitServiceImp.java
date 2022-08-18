package org.sid.cinema.Service;

import org.sid.cinema.entities.*;
import org.sid.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImp implements  ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public void initVille() {
        Stream.of("Casablanca","Tanger","Rabat","Marrakech").forEach(nameVille->{
            Ville ville=new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinema() {
        villeRepository.findAll().forEach(ville -> {
            Stream.of("Megarama","IMAX","RIF","DAOULIZ")
                    .forEach(nameCinema->{
                        Cinema cinema=new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setVille(ville);
                        cinema.setNombreSalles(3+(int)(Math.random()*7));
                        cinemaRepository.save(cinema);
                    });
        });
    }

    @Override
    public void initSalle() {
        cinemaRepository.findAll().forEach(cinema->{
            for (int i=0;i<cinema.getNombreSalles();i++){
                Salle salle=new Salle();
                salle.setName("Salle"+(i+1));
                salle.setCinema(cinema);
                salle.setNombrePlace(15+(int) (Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place=new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);

            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(seance->{
            Seance seance1=new Seance();
            try {
                seance1.setHeureDebut(dateFormat.parse(seance));
                seanceRepository.save(seance1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("Histroire","Actions","Fictions","Drama").forEach(cat->{
            Categorie categorie=new Categorie();
            categorie.setName(cat);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double []duree=new double[]{1,1.5,2,2.5,3};
        List<Categorie>categories=categorieRepository.findAll();
        Stream.of("vikings","Gameofthrones","seigneurdesanneaux","spiderman","IRONMan")
                .forEach(Titrefilm->{
                    Film film1=new Film();
                    film1.setTitle(Titrefilm);
                    film1.setDuree(duree[new Random().nextInt(duree.length)]);
                    film1.setPhoto(Titrefilm+".jpg");
                    film1.setCategorie(categories.get(new Random().nextInt(categories.size())));
                    filmRepository.save(film1);
                });
    }

    @Override
    public void initProjections() {
        double []prix=new double[]{30,50,70,90,100};
        List<Film> films=filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    Film f =films.get(index);
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection=new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(f);
                            projection.setPrix(prix[new Random().nextInt(prix.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });

                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
         projection.getSalle().getPlaces().forEach(place -> {
             Ticket ticket=new Ticket();
             ticket.setPlace(place);
             ticket.setPrixAchat(projection.getPrix());
             ticket.setProjection(projection);
             ticket.setReserve(false);
             ticketRepository.save(ticket);
         });


        });
    }
}
