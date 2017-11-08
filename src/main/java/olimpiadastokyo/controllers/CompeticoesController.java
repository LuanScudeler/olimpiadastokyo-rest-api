package olimpiadastokyo.controllers;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("competicoes")
public class CompeticoesController {

    private static final Logger log = LoggerFactory.getLogger(CompeticoesController.class);

    @Autowired
    CompeticaoRepository competicaoRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Competicao> getCompeticoes(@RequestParam(value="modalidade", defaultValue = "") String modalidade) {
        if(modalidade.isEmpty())
            return competicaoRepository.findAll();
        else
            return competicaoRepository.findByModalidade(modalidade);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public void create(@RequestBody Competicao competicao) {
        competicaoRepository.save(competicao);
    }
}
