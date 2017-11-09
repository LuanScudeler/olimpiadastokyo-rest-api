package olimpiadastokyo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competicao implements Comparable<Competicao> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long competicaoId;
    private String modalidade;
    private String local;
    private String adversarioUm;
    private String adversarioDois;
    private String etapa;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private DateTime inicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private DateTime termino;


    @Override
    public int compareTo(Competicao o) {
        return getInicio().compareTo(o.getInicio());
    }
}
