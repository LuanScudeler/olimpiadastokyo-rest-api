package olimpiadastokyo.services;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.joda.time.DateTime;
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
        Competicao competicao = new Competicao(null, "Boxe", "Tokyo", "Brasil", "Japão", "Semifinal", new DateTime(), new DateTime().plusHours(1), null);
        List<Competicao> list = new ArrayList<>();
        list.add(competicao);

        Mockito.when(competicaoRepository.findTimeOverlap(competicao.getModalidade(), competicao.getLocal(), competicao.getInicio()))
                .thenReturn(list);
    }

    @Test
    public void checkForMinMatchDuration_checkShouldFail() {
        Competicao comp = new Competicao(null, "Boxe", "Tokyo", "Brasil", "Japão", "Semifinal", new DateTime(), new DateTime().plusMinutes(20), null);
        Assert.assertFalse(competicaoService.checkForMinMatchDuration(comp));
    }

    @Test
    public void checkForTimeOverlap_checkShouldFail() {
        Competicao competicao = new Competicao(null, "Boxe", "Tokyo", "Brasil", "Japão", "Semifinal", new DateTime(), new DateTime().plusHours(1), null);
        Assert.assertFalse(competicaoService.checkForTimeOverlap(competicao));
    }
}
