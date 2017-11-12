package olimpiadastokyo.services;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.exceptions.EntityNotFoundException;
import olimpiadastokyo.exceptions.RuleBrokenException;
import olimpiadastokyo.exceptions.RuleErrorMessagesEnum;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Competicao create(Competicao c) throws RuleBrokenException {

        if(!checkForTimeOverlap(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_0);
        }
        else if (!checkForMinMatchDuration(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_1);
        }
        else if (!checkForMaxMatchesPerDay(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_2);
        }
        else if (!checkForDuplicatedMatches(c)) {
            throw new RuleBrokenException(Competicao.class, RuleErrorMessagesEnum.COD_3);
        }

        return competicaoRepository.save(c);
    }

    public boolean checkForMinMatchDuration(Competicao c) {
        long millisInicio = c.getDataInicio().toDateTime(c.getHoraInicio()).getMillis();
        long millisTermino = c.getDataTermino().toDateTime(c.getHoraTermino()).getMillis();
        long millisDiff = millisTermino - millisInicio;

        int duration = Integer.parseInt(String.format("%d", TimeUnit.MILLISECONDS.toMinutes(millisDiff)));
        if (duration < 30)
            return false;

        return true;
    }

    public boolean checkForMaxMatchesPerDay(Competicao c) {
        List<Competicao> list = competicaoRepository.findMatchesByDataInicioAndLocal(c.getDataInicio(), c.getLocal());
        if (list.size() >= 4)
            return false;

        return true;
    }

    /**
     * @param comp objeto Competicao a ser validado
     * @return Retorna 'false' caso a checagem encontre que a regra sendo validada será quebrada
     */
    public boolean checkForTimeOverlap(Competicao comp) {
        List<Competicao> list = competicaoRepository.findCompeticaoForTimeOverlapCheck(comp.getModalidade(), comp.getLocal(),
                comp.getDataInicio(), comp.getDataTermino());

        /*
        * Converte as datas para timestamp em milissegundos para tratar casos competições sendo comparadas
        * não iniciam e terminam no mesmo dia.
        * */
        long compInicioTimestamp = comp.getDataInicio().toDateTime(comp.getHoraInicio()).getMillis();
        long compTerminoTimestamp = comp.getDataTermino().toDateTime(comp.getHoraTermino()).getMillis();

        for (Competicao item : list) {
            long itemInicioTimestamp = item.getDataInicio().toDateTime(item.getHoraInicio()).getMillis();
            long itemTerminoTimestamp = item.getDataTermino().toDateTime(item.getHoraTermino()).getMillis();

            if (itemTerminoTimestamp > compInicioTimestamp && itemInicioTimestamp < compTerminoTimestamp)
                return false;
        }
        return true;
    }

    public boolean checkForDuplicatedMatches(Competicao c) {
        if (!c.getEtapa().equals(EtapasEnum.SEMIFINAL.getEtapaName())
                && !c.getEtapa().equals(EtapasEnum.FINAL.getEtapaName())) {
            List<Competicao> list = competicaoRepository.findDuplicatedMatches(c.getModalidade(), c.getAdversarioUm(), c.getAdversarioDois());
            return list.isEmpty();
        } else {
            return true;
        }
    }
}
