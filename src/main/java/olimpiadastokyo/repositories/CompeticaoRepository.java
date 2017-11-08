package olimpiadastokyo.repositories;

import olimpiadastokyo.entities.Competicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
    List<Competicao> findByModalidade(String modalidade);
}
