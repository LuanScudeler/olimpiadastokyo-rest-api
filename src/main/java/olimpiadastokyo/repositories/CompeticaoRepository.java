package olimpiadastokyo.repositories;

import olimpiadastokyo.entities.Competicao;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
    List<Competicao> findByModalidade(String modalidade);

    List<Competicao> findMatchesByDataInicio(LocalDate date);

    @Query("SELECT c FROM Competicao c WHERE c.modalidade = :modalidade " +
            "AND c.local = :local " +
            "AND c.dataInicio = :dataInicio AND c.dataTermino = :dataTermino " +
            "AND c.horaTermino > :horaInicio " +
            "AND c.horaInicio < :horaTermino")
    List<Competicao> findTimeOverlap(@Param("modalidade") String modalidade,
                                     @Param("local") String local,
                                     @Param("dataInicio") LocalDate dataInicio,
                                     @Param("dataTermino") LocalDate dataTermino,
                                     @Param("horaInicio") LocalTime horaInicio,
                                     @Param("horaTermino") LocalTime horaTermino);

    @Query("SELECT c FROM Competicao c WHERE c.modalidade = :modalidade " +
            "AND c.adversarioUm = :adversarioUm " +
            "AND c.adversarioDois = :adversarioDois")
    List<Competicao> findDuplicatedMatches(@Param("modalidade") String modalidade,
                                     @Param("adversarioUm") String adversarioUm,
                                     @Param("adversarioDois") String adversarioDois);
}
