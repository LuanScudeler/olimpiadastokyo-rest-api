package olimpiadastokyo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competicao implements Comparable<Competicao> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long competicaoId;
    @NotNull
    private String modalidade;
    @NotNull
    private String local;
    @NotNull
    private String adversarioUm;
    @NotNull
    private String adversarioDois;
    @NotNull
    private String etapa;
    @NotNull
    private LocalDate dataInicio;
    @NotNull
    private LocalDate dataTermino;
    @NotNull
    private LocalTime horaInicio;
    @NotNull
    private LocalTime horaTermino;

    @Override
    public int compareTo(Competicao o) {
        /*
        * Condicionais para a ordenação tratar corretamente casos em que as
        * competições não iniciam e terminam no mesmo dia. E também considerar o horário de inicio
        * */
        int firstCompare = getDataInicio().compareTo(o.getDataInicio());
        if (firstCompare == 0) {
            int secondCompare = getDataTermino().compareTo(o.getDataTermino());

            if (secondCompare == 0) {
                return getHoraInicio().compareTo(o.getHoraInicio());
            } else {
                return secondCompare;
            }
        }
        return firstCompare;
    }
}
