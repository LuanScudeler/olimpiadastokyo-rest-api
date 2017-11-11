package olimpiadastokyo.repositories;

import olimpiadastokyo.entities.Competicao;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
    List<Competicao> findByModalidade(String modalidade);

    List<Competicao> findMatchesByDate(LocalDate date);

    @Query("SELECT c FROM Competicao c WHERE c.modalidade = :modalidade AND c.local = :local AND c.termino > :inicio")
    List<Competicao> findTimeOverlap(@Param("modalidade") String modalidade,
                                     @Param("local") String local,
                                     @Param("inicio") DateTime inicio);
}
