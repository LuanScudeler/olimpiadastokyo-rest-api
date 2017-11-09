package olimpiadastokyo.controllers;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.exceptions.EntityNotFoundException;
import olimpiadastokyo.exceptions.RuleBrokenException;
import olimpiadastokyo.services.CompeticaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("competicoes")
public class CompeticaoController {

    private static final Logger log = LoggerFactory.getLogger(CompeticaoController.class);

    @Autowired
    private CompeticaoService competicaoService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Competicao> getCompeticoes(@RequestParam(value="modalidade", defaultValue = "") String modalidade) throws EntityNotFoundException {
        return competicaoService.getCompeticoes(modalidade);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public void create(@RequestBody Competicao competicao) throws RuleBrokenException {
        competicaoService.create(competicao);
    }
}
