package olimpiadastokyo;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.joda.time.DateTime;
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
        repository.save(new Competicao(null, "Boxe", "Tokyo", "Brasil", "Japão", "Semifinal", new DateTime(), new DateTime(), null));
        repository.save(new Competicao(null, "Boxe", "Tokyo", "USA", "Argentina", "Final", new DateTime(), new DateTime(), null));
        repository.save(new Competicao(null, "Futebol", "Tokyo", "Itália", "China", "Oitavas de Final", new DateTime(), new DateTime(), null));
        repository.save(new Competicao(null, "Basquete", "Tokyo", "Inglaterra", "México", "Quartas de Final", new DateTime(), new DateTime(), null));

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

    }
}
