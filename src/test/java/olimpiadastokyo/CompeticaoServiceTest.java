package olimpiadastokyo;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import olimpiadastokyo.services.CompeticaoService;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CompeticaoServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CompeticaoServiceTest.class);

    @TestConfiguration
    static class CompeticaoServiceTestContextConfiguration {

        @Bean
        public CompeticaoService competicaoService() {
            return new CompeticaoService();
        }
    }

    @MockBean
    private CompeticaoRepository competicaoRepository;

    @Autowired
    private CompeticaoService competicaoService;

    private List<Competicao> listForTimeOverlapCheck = new ArrayList<>();
    private List<Competicao> listForMaxMatchesPerDayCheck = new ArrayList<>();
    private List<Competicao> listForDuplicatedMatchesCheck = new ArrayList<>();

    @Before
    public void setUp() {
        Competicao c1 = Competicao.builder().modalidade("Boxe").local("Tokyo").
            adversarioUm("Inglaterra").adversarioDois("México").etapa("Quartas de Final").
            dataInicio(new LocalDate()).dataTermino(new LocalDate()).
            horaInicio(new LocalTime().minusHours(1)).horaTermino(new LocalTime()).build();

        Competicao c2 = Competicao.builder().modalidade("Boxe").local("Tokyo").
            adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
            dataInicio(new LocalDate()).dataTermino(new LocalDate()).
            horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build();

        Competicao c3 = Competicao.builder().modalidade("Boxe").local("Tokyo").
            adversarioUm("USA").adversarioDois("Argentina").etapa("Final").
            dataInicio(new LocalDate()).dataTermino(new LocalDate()).
            horaInicio(new LocalTime().plusHours(1)).horaTermino(new LocalTime().plusHours(2)).build();

        Competicao c4 = Competicao.builder().modalidade("Boxe").local("Tokyo").
            adversarioUm("Itália").adversarioDois("China").etapa("Oitavas de Final").
            dataInicio(new LocalDate()).dataTermino(new LocalDate()).
            horaInicio(new LocalTime().plusHours(2)).horaTermino(new LocalTime().plusHours(3)).build();

        Competicao c5 = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Itália").adversarioDois("China").etapa("Oitavas de Final").
                dataInicio(new LocalDate()).dataTermino(new LocalDate().plusDays(1)).
                horaInicio(new LocalTime().withHourOfDay(23)).horaTermino(new LocalTime().withHourOfDay(2)).build();

        listForTimeOverlapCheck.add(c1);
        listForTimeOverlapCheck.add(c5);

        listForMaxMatchesPerDayCheck.add(c1);
        listForMaxMatchesPerDayCheck.add(c2);
        listForMaxMatchesPerDayCheck.add(c3);
        listForMaxMatchesPerDayCheck.add(c4);

        listForDuplicatedMatchesCheck.add(c1);
    }

    @Test
    public void checkForMinMatchDuration_checkShouldFail() {
        Competicao comp = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusMinutes(20)).build();

        Assert.assertFalse(competicaoService.checkForMinMatchDuration(comp));
    }

    @Test
    public void checkForMinMatchDuration_checkShouldPass() {
        Competicao comp = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusMinutes(50)).build();

        Assert.assertTrue(competicaoService.checkForMinMatchDuration(comp));
    }

    @Test
    public void checkForTimeOverlap_checkShouldFail() {

        Competicao c1 = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().minusHours(1)).horaTermino(new LocalTime().plusHours(1)).build();

        when(competicaoRepository.findCompeticaoForTimeOverlapCheck(c1.getModalidade(), c1.getLocal(),
                c1.getDataInicio(), c1.getDataTermino()
        )).
                thenReturn(listForTimeOverlapCheck);

        Assert.assertFalse(competicaoService.checkForTimeOverlap(c1));

        // Teste para casos em que as competições sendo comparadas não iniciam e terminam no mesmo dia
        Competicao c2 = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate().plusDays(1)).
                horaInicio(new LocalTime().withHourOfDay(22)).horaTermino(new LocalTime().withHourOfDay(1)).build();

        when(competicaoRepository.findCompeticaoForTimeOverlapCheck(c2.getModalidade(), c2.getLocal(),
                c2.getDataInicio(), c2.getDataTermino()
        )).
                thenReturn(listForTimeOverlapCheck);

        Assert.assertFalse(competicaoService.checkForTimeOverlap(c2));
    }

    @Test
    public void checkForTimeOverlap_checkShouldPass() {
        Competicao c = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build();

        when(competicaoRepository.findCompeticaoForTimeOverlapCheck(c.getModalidade(), c.getLocal(),
                c.getDataInicio(), c.getDataTermino()
        )).
                thenReturn(new ArrayList<Competicao>());

        Assert.assertTrue(competicaoService.checkForTimeOverlap(c));
    }

    @Test
    public void checkForMaxMatchesPerDay_checkShouldFail() {
        Competicao c = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().plusHours(1)).horaTermino(new LocalTime().plusHours(3)).build();

        when(competicaoRepository.findMatchesByDataInicioAndLocal(c.getDataInicio(), c.getLocal())).
                thenReturn(listForMaxMatchesPerDayCheck);

        Assert.assertFalse(competicaoService.checkForMaxMatchesPerDay(c));
    }

    @Test
    public void checkForMaxMatchesPerDay_checkShouldPass() {
        Competicao c = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate().plusDays(1)).dataTermino(new LocalDate().plusDays(1)).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build();

        listForMaxMatchesPerDayCheck.remove(0);
        when(competicaoRepository.findMatchesByDataInicioAndLocal(c.getDataInicio(), c.getLocal())).
                thenReturn(listForMaxMatchesPerDayCheck);

        Assert.assertTrue(competicaoService.checkForMaxMatchesPerDay(c));
    }

    @Test
    public void checkForDuplicatedMatches_checkShouldFail() {
        Competicao c = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Inglaterra").adversarioDois("México").etapa("Oitavas de Final").
                dataInicio(new LocalDate()).dataTermino(new LocalDate().plusDays(1)).
                horaInicio(new LocalTime().plusHours(1)).horaTermino(new LocalTime().plusHours(3)).build();

        when(competicaoRepository.findDuplicatedMatches(c.getModalidade(), c.getAdversarioUm(), c.getAdversarioDois())).
                thenReturn(listForDuplicatedMatchesCheck);

        Assert.assertFalse(competicaoService.checkForDuplicatedMatches(c));
    }

    @Test
    public void checkForDuplicatedMatches_checkShouldPass() {
        Competicao c1 = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Inglaterra").adversarioDois("México").etapa("Final").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().plusHours(1)).horaTermino(new LocalTime().plusHours(3)).build();

        Assert.assertTrue(competicaoService.checkForDuplicatedMatches(c1));
    }
}
