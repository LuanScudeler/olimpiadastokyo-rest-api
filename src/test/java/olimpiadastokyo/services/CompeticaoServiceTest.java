package olimpiadastokyo.services;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private CompeticaoService competicaoService;

    @MockBean
    private CompeticaoRepository competicaoRepository;

    @Before
    public void setUp() {
        Competicao competicao = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build();
        List<Competicao> list = new ArrayList<>();
        list.add(competicao);

        Mockito.when(competicaoRepository.findTimeOverlap(competicao.getModalidade(), competicao.getLocal(),
                competicao.getDataInicio(), competicao.getDataTermino(),
                competicao.getHoraInicio(), competicao.getHoraTermino())).
                thenReturn(list);
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
    public void checkForTimeOverlap_checkShouldFail() {
        Competicao competicao = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Brasil").adversarioDois("Japão").etapa("Semifinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime()).horaTermino(new LocalTime().plusHours(1)).build();

        Assert.assertFalse(competicaoService.checkForTimeOverlap(competicao));
    }
}
