package olimpiadastokyo.services;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.exceptions.EntityNotFoundException;
import olimpiadastokyo.exceptions.RuleErrorMessagesEnum;
import olimpiadastokyo.exceptions.RuleBrokenException;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lnsr on 11/8/2017.
 */

@Service
public class CompeticaoService {

    @Autowired
    private CompeticaoRepository competicaoRepository;

    public List<Competicao> getCompeticoes(String modalidade) throws EntityNotFoundException {
        List<Competicao> list = new ArrayList<Competicao>();

        if(modalidade.isEmpty())
            list = competicaoRepository.findAll();
        else
            list = competicaoRepository.findByModalidade(modalidade);

        if(list.size() <= 0) {
            throw new EntityNotFoundException(Competicao.class, "modalidade", modalidade);
        } else {
            Collections.sort(list);
            return list;
        }
    }

    public void create(Competicao competicao) throws RuleBrokenException {
        List<Competicao> list = competicaoRepository.find(competicao.getModalidade(), competicao.getLocal(), competicao.getInicio());

        System.out.println(String.format("Interval in minutes: %d",new Interval(competicao.getInicio(), competicao.getTermino()).toDuration().getStandardMinutes()));

        if(list.size() > 0)
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_0);
        else if (false)
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_1);
        else
            competicaoRepository.save(competicao);




    }
}
