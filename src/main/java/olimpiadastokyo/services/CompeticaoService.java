package olimpiadastokyo.services;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.exceptions.EntityNotFoundException;
import olimpiadastokyo.exceptions.RuleBrokenException;
import olimpiadastokyo.exceptions.RuleErrorMessagesEnum;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Competicao> list;

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

        if(!checkForTimeOverlap(competicao))
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_0);
        else if (!checkForMatchDuration(competicao))
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_1);
        else
            competicaoRepository.save(competicao);
    }

    private boolean checkForMatchDuration(Competicao competicao) {
        long duration = new Interval(competicao.getInicio(), competicao.getTermino()).toDuration().getStandardMinutes();
        if (duration < 30)
            return false;

        return true;
    }

    public boolean checkForTimeOverlap(Competicao competicao) {
        List<Competicao> list = competicaoRepository.find(competicao.getModalidade(), competicao.getLocal(), competicao.getInicio());
        for (Competicao c : list) {
            if(c.getTermino().getYear() == competicao.getTermino().getYear() &&
                    c.getTermino().getMonthOfYear() == competicao.getTermino().getMonthOfYear() &&
                    c.getTermino().getDayOfMonth() == competicao.getTermino().getDayOfMonth()) {
                return false;
            }
        }
        return true;
    }
}
