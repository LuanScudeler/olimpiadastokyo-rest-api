package olimpiadastokyo;

import com.fasterxml.jackson.databind.ObjectMapper;
import olimpiadastokyo.controllers.CompeticaoController;
import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.services.CompeticaoService;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@WebMvcTest(CompeticaoController.class)
@ComponentScan
public class CompeticaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompeticaoService competicaoService;

    @Test
    public void whenEtapaArgNotValid_thenStatus400() throws Exception {
        Competicao c = Competicao.builder().modalidade("Boxe").local("Tokyo").
                adversarioUm("Inglaterra").adversarioDois("México").etapa("QuartasDeFinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().minusHours(1)).horaTermino(new LocalTime()).build();
        String json = objectMapper.writeValueAsString(c);

        given(competicaoService.create(any(Competicao.class))).willReturn(c);

        mvc.perform(MockMvcRequestBuilders.post("/competicoes").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenRequiredArgIsEmpty_thenStatus400() throws Exception {
        Competicao c = Competicao.builder().modalidade("").local("Tokyo").
                adversarioUm("Inglaterra").adversarioDois("México").etapa("QuartasDeFinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().minusHours(1)).horaTermino(new LocalTime()).build();
        String json = objectMapper.writeValueAsString(c);

        given(competicaoService.create(any(Competicao.class))).willReturn(c);

        mvc.perform(MockMvcRequestBuilders.post("/competicoes").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenRequiredArgIsNull_thenStatus400() throws Exception {
        Competicao c = Competicao.builder().modalidade(null).local("Tokyo").
                adversarioUm("Inglaterra").adversarioDois("México").etapa("QuartasDeFinal").
                dataInicio(new LocalDate()).dataTermino(new LocalDate()).
                horaInicio(new LocalTime().minusHours(1)).horaTermino(new LocalTime()).build();
        String json = objectMapper.writeValueAsString(c);

        given(competicaoService.create(any(Competicao.class))).willReturn(c);

        mvc.perform(MockMvcRequestBuilders.post("/competicoes").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
