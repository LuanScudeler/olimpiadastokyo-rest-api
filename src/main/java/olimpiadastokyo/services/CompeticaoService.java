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

    public void create(Competicao c) throws RuleBrokenException {

        if(!checkForTimeOverlap(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_0);
        }
        else if (!checkForMinMatchDuration(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_1);
        }
        else if (!checkForMaxMatchesPerDay(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_2);
        }
        else if (!c.getEtapa().equals(EtapasEnum.SEMIFINAL.getEtapaName()) &&
                !c.getEtapa().equals(EtapasEnum.FINAL.getEtapaName())) {

            if (!checkForDuplicatedMatches(c))
                throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_3);
        }

        competicaoRepository.save(c);
    }

    public boolean checkForMinMatchDuration(Competicao c) {
        long duration = new Interval(c.getHoraInicio().toDateTimeToday(), c.getHoraTermino().toDateTimeToday()).
                toDuration().getStandardMinutes();
        if (duration < 30)
            return false;

        return true;
    }

    private boolean checkForMaxMatchesPerDay(Competicao c) {
        List<Competicao> list = competicaoRepository.findMatchesByDataInicio(c.getDataInicio());
        if (list.size() >= 4)
            return false;

        return true;
    }

    /**
     * A busca por sobreprosição de horários cadastrados está levando em consideração que a competição
     * inicia e termina no mesmo dia.
     *
     * @param c objeto Competicao a ser validado
     * @return Retorna 'false' caso a checagem encontre que a regra sendo validada será quebrada
     */
    public boolean checkForTimeOverlap(Competicao c) {
        List<Competicao> list = competicaoRepository.findTimeOverlap(c.getModalidade(), c.getLocal(),
                c.getDataInicio(), c.getDataTermino(), c.getHoraInicio(), c.getHoraTermino());
        return list.isEmpty();
    }

    private boolean checkForDuplicatedMatches(Competicao c) {
        List<Competicao> list = competicaoRepository.findDuplicatedMatches(c.getModalidade(), c.getAdversarioUm(), c.getAdversarioDois());
        return list.isEmpty();
    }
}
