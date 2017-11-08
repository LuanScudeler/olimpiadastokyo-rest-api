package olimpiadastokyo.repositories;

import olimpiadastokyo.entities.Competicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
    List<Competicao> findByModalidade(String modalidade);

    @Query("SELECT c FROM Competicao c WHERE c.modalidade = :modalidade AND c.local = :local AND c.termino > :inicio")
    List<Competicao> find(@Param("modalidade") String modalidade,
                          @Param("local") String local,
                          @Param("inicio") Date inicio);
}
