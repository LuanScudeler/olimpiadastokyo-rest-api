package olimpiadastokyo.repositories;

import olimpiadastokyo.entities.Competicao;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
    List<Competicao> findByModalidade(String modalidade);

    List<Competicao> findMatchesByDataInicioAndLocal(LocalDate date, String local);

    /**
     *
     * @return Lista das competições
     */
    @Query("SELECT c FROM Competicao c WHERE c.modalidade = :modalidade " +
            "AND c.local = :local " +
            "AND (c.dataInicio = :dataInicio OR c.dataTermino = :dataTermino)")
    List<Competicao> findCompeticaoForTimeOverlapCheck(@Param("modalidade") String modalidade,
                                                       @Param("local") String local,
                                                       @Param("dataInicio") LocalDate dataInicio,
                                                       @Param("dataTermino") LocalDate dataTermino);

    @Query("SELECT c FROM Competicao c WHERE c.modalidade = :modalidade " +
            "AND c.adversarioUm = :adversarioUm " +
            "AND c.adversarioDois = :adversarioDois")
    List<Competicao> findDuplicatedMatches(@Param("modalidade") String modalidade,
                                     @Param("adversarioUm") String adversarioUm,
                                     @Param("adversarioDois") String adversarioDois);
}
