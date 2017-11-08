package olimpiadastokyo.services;

import olimpiadastokyo.entities.Competicao;
import olimpiadastokyo.exceptions.EntityNotFoundException;
import olimpiadastokyo.repositories.CompeticaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lnsr on 11/8/2017.
 */

@Service
public class CompeticaoService {

    @Autowired
    CompeticaoRepository competicaoRepository;

    public List<Competicao> getCompeticoes(String modalidade) throws EntityNotFoundException {
        List<Competicao> list = new ArrayList<Competicao>();

        if(modalidade.isEmpty())
            list = competicaoRepository.findAll();
        else
            list = competicaoRepository.findByModalidade(modalidade);

        if(list.size() <= 0) {
            throw new EntityNotFoundException(Competicao.class, "modalidade", modalidade);
        } else {
            return list;
        }
    }

    public void create(Competicao competicao) {
        competicaoRepository.save(competicao);
    }

}