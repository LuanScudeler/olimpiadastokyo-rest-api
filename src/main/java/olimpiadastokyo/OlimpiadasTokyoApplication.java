package olimpiadastokyo;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OlimpiadasTokyoApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OlimpiadasTokyoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OlimpiadasTokyoApplication.class);
    }

    @Autowired
    CompeticaoRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(Competicao.builder().modalidade("Boxe").local("Tokyo").adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build());

        repository.save(Competicao.builder().modalidade("Boxe").local("Tokyo").adversarioUm("USA").adversarioDois("Argentina").etapa("Final").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build());

        repository.save(Competicao.builder().modalidade("Boxe").local("Tokyo").adversarioUm("Itália").adversarioDois("China").etapa("Oitavas de Final").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().plusHours(7)).horaTermino(new LocalTime().plusHours(9)).build());

        repository.save(Competicao.builder().modalidade("Boxe").local("Tokyo").adversarioUm("Inglaterra").adversarioDois("México").etapa("Quartas de Final").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().minusHours(2)).horaTermino(new LocalTime().minusHours(1)).build());

        log.info("Competicao -> findAll():");
        log.info("-------------------------------");
        for (Competicao competicao : repository.findAll()) {
            log.info(competicao.toString());
        }
        log.info("-------------------------------");

        log.info("Competicao -> findByModalidade():");
        log.info("-------------------------------");
        for (Competicao competicao : repository.findByModalidade("Boxe")) {
            log.info(competicao.toString());
        }
        log.info("-------------------------------");

        log.info("Competicao -> findTimeOverlap():");
        log.info("-------------------------------");

        LocalTime inicio = new LocalTime().plusHours(8);
        LocalTime termino = new LocalTime().plusHours(10);

        for (Competicao competicao : repository.findTimeOverlap("Boxe", "Tokyo",
                new LocalDate().plusDays(1), new LocalDate().plusDays(1),
                new LocalTime().plusHours(8), new LocalTime().plusHours(10))) {
            log.info(competicao.toString());
        }
        log.info("-> " + inicio + " - " + termino  + new LocalDate().plusDays(1) + new LocalDate().plusDays(1));
        log.info("-------------------------------");

    }
}
