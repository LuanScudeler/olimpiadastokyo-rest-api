package olimpiadastokyo;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class OlimpiadasTokyoApplication {

    private static final Logger log = LoggerFactory.getLogger(OlimpiadasTokyoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OlimpiadasTokyoApplication.class);
    }

    @Bean
    public CommandLineRunner demo(CompeticaoRepository repository) {
        return (args) -> {
            repository.save(new Competicao("Boxe", "Tokyo", "Brasil", "Japão", "Semifinal", LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2L))));
            repository.save(new Competicao("Boxe", "Tokyo", "USA", "Argentina", "Final", LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2L))));
            repository.save(new Competicao("Futebol", "Tokyo", "Itália", "China", "Oitavas de Final", LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2L))));
            repository.save(new Competicao("Basquete", "Tokyo", "Inglaterra", "México", "Quartas de Final", LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2L))));

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
        };
    }

}
